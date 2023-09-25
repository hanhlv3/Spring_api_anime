package com.api_anime.anime.repository;

import com.api_anime.anime.entity.Film;
import com.api_anime.anime.entity.FilmCmt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmCmtRepository  extends JpaRepository<FilmCmt, Long> {

    List<FilmCmt> findAllByFilm(Film film);

    List<FilmCmt> findAllByFilmAndAndStatus(Film film, boolean status);

    List<FilmCmt> findAllByStatus(boolean status);
}
