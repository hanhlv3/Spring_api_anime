package com.api_anime.anime.repository;

import com.api_anime.anime.entity.EvaluateFilm;
import com.api_anime.anime.entity.Film;
import com.api_anime.anime.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluateFilmRepository extends JpaRepository<EvaluateFilm, Long> {
    EvaluateFilm findByFilmAndUser (Film film, User user);

    List<EvaluateFilm> findAllByFilm(Film film);
}
