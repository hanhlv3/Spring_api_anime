package com.api_anime.anime.controller;


import com.api_anime.anime.entity.Film;
import com.api_anime.anime.entity.FilmStore;
import com.api_anime.anime.model.ObjectResponse;
import com.api_anime.anime.service.FilmStoreService;
import com.api_anime.anime.utils.ApplicationUrl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FilmStoreController {

    @Autowired(required = false)
    private FilmStoreService filmStoreService;

    // get film
    @GetMapping("/api/v1/user/film_store")
    private ResponseEntity<List<FilmStore>> getListFilmStore(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        List<FilmStore> filmStoreList = filmStoreService.getAllFilmListStore(email);

        for (FilmStore filmStore : filmStoreList) {
            filmStore.getFilm().setCategories(null);
            String image = filmStore.getFilm().getImg();
            filmStore.getFilm().setImg(ApplicationUrl.getUrlImage(image,request));
            filmStore.setUser(null);
        }

        return ResponseEntity.ok(filmStoreList);
    }

    // insert film into store
    @PostMapping("/api/v1/user/film_store/{id}")
    private ResponseEntity<?> insertFilmStore(@PathVariable("id") long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        filmStoreService.insertFilmStore(email, id);

        return ResponseEntity.ok(
                new ObjectResponse(201, "Insert successfully")
        );
    }

    // delete film
    @DeleteMapping("/api/v1/user/film_store/{id}")
    private ResponseEntity<?> deleteFilmStore(@PathVariable("id") long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        filmStoreService.deleteFilmStore(id);

        return ResponseEntity.ok(
                new ObjectResponse(201, "Delete successfully")
        );
    }
}
