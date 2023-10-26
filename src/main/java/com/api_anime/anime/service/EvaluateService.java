package com.api_anime.anime.service;

import com.api_anime.anime.entity.Film;
import org.springframework.stereotype.Service;

@Service
public interface EvaluateService {
    double getScoreByFilmId(Film film);
}
