package com.api_anime.anime.repository;

import com.api_anime.anime.entity.FilmStore;
import com.api_anime.anime.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmStoreRepository extends JpaRepository<FilmStore, Long> {

    List<FilmStore> findAllByUser(User user);
}
