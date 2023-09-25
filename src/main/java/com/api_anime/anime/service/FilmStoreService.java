package com.api_anime.anime.service;


import com.api_anime.anime.entity.FilmStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FilmStoreService {
    List<FilmStore> getAllFilmListStore(String email);

    void insertFilmStore(String email, long id);

    void deleteFilmStore(long id);
}
