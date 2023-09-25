package com.api_anime.anime.service;


import com.api_anime.anime.entity.FilmCmt;
import com.api_anime.anime.model.FilmCmtModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FilmCmtService {
    List<FilmCmt> getAllCmtByFilm(long id);

    FilmCmt insertFilmCmt(String email, FilmCmtModel filmCmtModel, long filmId);

    List<FilmCmt> getAllCmtByFilmAndStatus(long id, boolean status);

    List<FilmCmt> getAllCmtByStatus(boolean status);

    void updateFilmCmt(long id);

    void deleteFilm(long id);

    List<FilmCmt> getAllCmt();
}
