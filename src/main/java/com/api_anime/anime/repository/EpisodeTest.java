package com.api_anime.anime.repository;

import com.api_anime.anime.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodeTest extends JpaRepository<Episode, Long> {

    @Query(value = "SELECT * FROM episodes WHERE filmId = :filmId", nativeQuery = true)
    List<Episode> findAllByFilmId(@Param("filmId") Long filmId);
}
