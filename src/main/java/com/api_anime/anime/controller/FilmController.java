package com.api_anime.anime.controller;


import com.api_anime.anime.db.ConnectDB;
import com.api_anime.anime.db.EpisodeDB;
import com.api_anime.anime.entity.Category;
import com.api_anime.anime.entity.Film;
import com.api_anime.anime.model.EvaluateModel;
import com.api_anime.anime.model.ObjectResponse;
import com.api_anime.anime.model.FilmModel;
import com.api_anime.anime.service.EpisodeService;
import com.api_anime.anime.service.EvaluateService;
import com.api_anime.anime.service.FilmService;
import com.api_anime.anime.utils.ApplicationUrl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class FilmController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private EpisodeService episodeService;

    @Autowired
    private EvaluateService evaluateService;

    private  static Connection conn;

    static {
        try {
            conn = ConnectDB.connectDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    // get film by id
    @GetMapping("/api/v1/public/film/{id}")
    private ResponseEntity<?> getFilmById(@PathVariable("id") long id) {
        Optional<Film> film = filmService.getFilmById(id);
        if(film.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ObjectResponse(0, "The film is not exits"));

        return ResponseEntity.ok(film.get());
    }


    // get film by category
    @GetMapping("/api/v1/public/film/category/{id}")
    private ResponseEntity<?> getFilmByCategoryId(@PathVariable("id") long id) {
    List<Film> film = filmService.getFilmByCategories(id);
        if(film.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ObjectResponse(400, "The film is not exits"));

        return ResponseEntity.ok(film);
    }


    // get all film
    @GetMapping("/api/v1/public/film")
    private ResponseEntity<List<Film>> getAllFilms(HttpServletRequest request) throws SQLException {

        List<Film> filmList =  filmService.getAllFimls();
        for (Film film: filmList) {

            film.setImg(ApplicationUrl.getUrlImage(film.getImg(), request));
            for (Category category: film.getCategories()) {
                category.setFilms(null);
            }

            double score = evaluateService.getScoreByFilmId(film);
            int currentEpisode = EpisodeDB.getEpisodeCurrent(conn, film.getFilmId());
            film.setScore(score);
            film.setCurrentEpisode(currentEpisode);
        }
        return ResponseEntity.ok(filmList);
    }

    // get image film
    @GetMapping("/api/v1/public/image/{fileName}")
    public ResponseEntity<?> getImageFilm(@PathVariable("fileName") String fileName) {
        try {

            byte[] bytes = filmService.getImage(fileName);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        }catch (Exception exception) {
            return ResponseEntity.noContent().build();
        }
    }


    // insert film
    @PostMapping(path = "/api/v1/private/film", consumes = {"multipart/form-data"})
    private ResponseEntity<?> insertFilm(@RequestPart("film")
                                             String dataString, @RequestPart("image") MultipartFile image,
                                         HttpServletRequest request) {
        String urlDir = "";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FilmModel filmModel = objectMapper.readValue(dataString, FilmModel.class);

            Film film = filmService.insertFilm(image, filmModel);
            film.setImg(ApplicationUrl.getUrlImage(film.getImg(), request));

            //Thread.sleep(1000*50);
            for (Category cat: film.getCategories() ) {
                cat.setFilms(null);
            }
            return ResponseEntity.ok(film);
        } catch (Exception e) {
            log.info("error: " + e.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

     //update film

    @PutMapping("/api/v1/private/film/{id}")
    private ResponseEntity<?> updateFilm(@PathVariable("id") long id,
                                         @RequestParam(value="image", required = false) MultipartFile image,
                                         @RequestParam("film") String dataString,
                                         HttpServletRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            FilmModel filmModel = objectMapper.readValue(dataString, FilmModel.class);
            Film film = filmService.updateFilm(id, image, filmModel);
            film.setImg(ApplicationUrl.getUrlImage(film.getImg(), request));

            for (Category category: film.getCategories() ) {
                category.setFilms(null);
            }

            return ResponseEntity.ok(film);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // delete film
    @DeleteMapping("/api/v1/private/film/{id}")
    private ResponseEntity<?> deleteFilm(@PathVariable("id") long id) {

        Optional<Film> film = filmService.getFilmById(id);
        if(film.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ObjectResponse(400, "The film is not exits"));

        filmService.deleteFilm(film.get());

        return ResponseEntity.ok(new ObjectResponse(200, "Delete film successfully"));

    }

    // evaluate film
    @PostMapping("/api/v1/user/film/evaluate")
    private ResponseEntity<?> evaluateFilm(@RequestBody EvaluateModel evaluateModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        filmService.evaluateFilm(email, evaluateModel);

        return ResponseEntity.ok(new ObjectResponse(200, "Evaluate successfully"));
    }


}
