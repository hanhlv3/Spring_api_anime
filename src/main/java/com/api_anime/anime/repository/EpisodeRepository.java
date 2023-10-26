package com.api_anime.anime.repository;

import com.api_anime.anime.entity.Episode;
import com.api_anime.anime.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    List<Episode> findAllByFilm(Film film);

    void deleteAllByFilm(Film film);
    void deleteByEpisodeId(long id);
}
