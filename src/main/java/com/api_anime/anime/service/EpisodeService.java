package com.api_anime.anime.service;


import com.api_anime.anime.entity.Episode;
import com.api_anime.anime.entity.Film;
import com.api_anime.anime.model.EpisodeModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface EpisodeService  {
    Episode getEpisodeById(long id);

    List<Episode> getAllEpisodeByFilm(Long film);

    Episode insertEpisode(EpisodeModel episodeModel);

    Episode updateEpisode(long i, EpisodeModel episodeModel);

    void deleteEpisode(long id);

    List<Episode> getAllEpisode();

    int getCurrentNumber(Long id);
}
