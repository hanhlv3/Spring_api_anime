package com.api_anime.anime.service;


import com.api_anime.anime.entity.Film;
import com.api_anime.anime.entity.FilmCmt;
import com.api_anime.anime.entity.User;
import com.api_anime.anime.model.FilmCmtModel;
import com.api_anime.anime.repository.FilmCmtRepository;
import com.api_anime.anime.repository.FilmRepository;
import com.api_anime.anime.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
public class FilmCmtServiceImpl implements  FilmCmtService{

    @Autowired
    private FilmCmtRepository filmCmtRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<FilmCmt> getAllCmtByFilm(long id) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found film"));
        List<FilmCmt> filmCmtList = filmCmtRepository.findAllByFilm(film);
        return filmCmtList;
    }

    @Override
    public List<FilmCmt> getAllCmt() {
        return filmCmtRepository.findAll();
    }

    @Override
    public FilmCmt insertFilmCmt(String email, FilmCmtModel filmCmtModel, long filmId) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("not found user"));

        Film film = filmRepository.findById(filmId).orElseThrow(() -> new EntityNotFoundException("not found film"));

        FilmCmt filmCmt = new FilmCmt();

        filmCmt.setCommentContent(filmCmtModel.getCommentContent());
        filmCmt.setUser(user);
        filmCmt.setFilmCmtParentId(filmCmtModel.getFilmCmtParentId());
        filmCmt.setFilm(film);
        filmCmtRepository.save(filmCmt);

        return filmCmt;
    }

    @Override
    public List<FilmCmt> getAllCmtByFilmAndStatus(long id, boolean status) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found film"));
        List<FilmCmt> filmCmtList = filmCmtRepository.findAllByFilmAndAndStatus(film, status);
        return filmCmtList;
    }

    @Override
    public List<FilmCmt> getAllCmtByStatus(boolean status) {
        return filmCmtRepository.findAllByStatus(status);
    }

    @Override
    public void updateFilmCmt(long id) {
        FilmCmt filmCmt = filmCmtRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());

        filmCmt.setUpdatedAt(Calendar.getInstance().getTime());
        filmCmt.setStatus(true);

        filmCmtRepository.save(filmCmt);
    }

    @Override
    public void deleteFilm(long id) {
        FilmCmt filmCmt = filmCmtRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());

        filmCmt.setUser(null);
        filmCmt.setFilm(null);

        filmCmtRepository.delete(filmCmt);
    }


}
