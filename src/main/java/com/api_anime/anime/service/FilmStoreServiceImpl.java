package com.api_anime.anime.service;

import com.api_anime.anime.entity.Film;
import com.api_anime.anime.entity.FilmStore;
import com.api_anime.anime.entity.User;
import com.api_anime.anime.repository.FilmRepository;
import com.api_anime.anime.repository.FilmStoreRepository;
import com.api_anime.anime.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FilmStoreServiceImpl implements FilmStoreService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FilmStoreRepository filmStoreRepository;

    @Autowired
    private FilmRepository filmRepository;
    @Override
    public List<FilmStore> getAllFilmListStore(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("user not exist"));

        List<FilmStore> filmStores = filmStoreRepository.findAllByUser(user);


        return filmStores;
    }

    @Override
    public void insertFilmStore(String email, long id) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("user not exist"));
        Film film = filmRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("film not exits"));

        FilmStore filmStore = new FilmStore();
        filmStore.setFilm(film);
        filmStore.setUser(user);

        filmStoreRepository.save(filmStore);
    }

    @Override
    public void deleteFilmStore(long id) {
        FilmStore filmStore = filmStoreRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Film store not found"));

        filmStore.setUser(null);
        filmStore.setFilm(null);

        filmStoreRepository.delete(filmStore);
    }
}
