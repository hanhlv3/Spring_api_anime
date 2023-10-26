package com.api_anime.anime.controller;


import com.api_anime.anime.entity.Category;
import com.api_anime.anime.entity.Episode;
import com.api_anime.anime.model.EpisodeModel;
import com.api_anime.anime.model.ObjectResponse;
import com.api_anime.anime.service.EpisodeService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class EpisodeController {

    @Autowired
    private EpisodeService episodeService;


    // get a episode
    @GetMapping("/api/v1/public/episode/{id}")
    public ResponseEntity<?> getEpisodeById(@PathVariable("id") long id) {
        Episode episode =  episodeService.getEpisodeById(id);
        return ResponseEntity.ok(episode);
    }

    // get all episode of film
    @GetMapping("/api/v1/public/episode/film/{id}")
    public ResponseEntity<?> getAllEpisodeByFilm(@PathVariable("id") long id) {
        List<Episode> episodeList =  episodeService.getAllEpisodeByFilm(id);
        for (Episode episode: episodeList) {

           episode.setFilm(null);
        }
        return ResponseEntity.ok(episodeList);
    }

    // get all episode
    @GetMapping("/api/v1/public/listEpisode")
    public ResponseEntity<?> getAllEpisode() {
        log.info("Hanh");
        List<Episode> episodeList =  episodeService.getAllEpisode();
        return ResponseEntity.ok(episodeList);
    }

    // insert a episode
    @PostMapping("/api/v1/private/episode")
    public ResponseEntity<?>  insertEpisode(@RequestBody EpisodeModel episodeModel) {
        Episode episode = episodeService.insertEpisode(episodeModel);
        for(Category category: episode.getFilm().getCategories()) {
            category.setFilms(null);
        }
        return ResponseEntity.ok(episode);
    }

    // update a episode
    @PutMapping("/api/v1/private/episode/{id}")
    public ResponseEntity<?>  updateEpisode(@PathVariable("id") long id, @RequestBody EpisodeModel episodeModel) {
        Episode episode = episodeService.updateEpisode(id, episodeModel);
        return ResponseEntity.ok(episode);
    }

    // delete a episode
    @DeleteMapping("/api/v1/private/episode/{id}")
    public ResponseEntity<?>  deleteEpisode(@PathVariable("id") long id) {
        log.info(id + "ddd");
        episodeService.deleteEpisode(id);


        return ResponseEntity.ok(
                new ObjectResponse(201, "Delete episode successfully")
        );
    }
}
