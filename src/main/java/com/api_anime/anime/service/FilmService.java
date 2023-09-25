package com.api_anime.anime.service;

import com.api_anime.anime.entity.Film;
import com.api_anime.anime.model.EvaluateModel;
import com.api_anime.anime.model.FilmModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public interface FilmService {
    Optional<Film> getFilmById(long id);

    List<Film> getAllFimls();

    void deleteFilm(Film film);

    Film insertFilm(MultipartFile images, FilmModel filmModel) throws IOException, ExecutionException, InterruptedException;

    byte[] getImage(String fileName);

    Film updateFilm(long id, MultipartFile images, FilmModel filmModel) throws IOException;

    List<Film> getFilmByCategories(long id);

    void evaluateFilm(String email, EvaluateModel evaluateModel);
}
