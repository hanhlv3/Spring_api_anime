package com.api_anime.anime.service;

import com.api_anime.anime.entity.Episode;
import com.api_anime.anime.entity.Film;
import com.api_anime.anime.model.EpisodeModel;
import com.api_anime.anime.repository.EpisodeRepository;
import com.api_anime.anime.repository.FilmRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
@Transactional
public class EpisodeServiceImpl implements  EpisodeService{

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private EntityManager entityManager;
    @Override
    public Episode getEpisodeById(long id) {
        Episode episode = episodeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        return episode;
    }

    @Override
    public List<Episode> getAllEpisodeByFilm(long id) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found film"));
        List<Episode> episodeList = episodeRepository.findAllByFilm(film);
        return episodeList;
    }


    @Override
    public Episode insertEpisode(EpisodeModel episodeModel) {
        Episode episode = new Episode();

        Film film = filmRepository.findById(episodeModel.getFilmId()).orElseThrow(() -> new EntityNotFoundException("Not found film"));

        episode.setFilm(film);
        episode.setEpisodeLink(episodeModel.getEpisodeLink());
        episode.setEpisodeNumber((int) episodeModel.getEpisodeNumber());

        episodeRepository.save(episode);

        return episode;
    }

    @Override
    public Episode updateEpisode(long id, EpisodeModel episodeModel) {
        Episode episode = episodeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found episode"));

        Film film = filmRepository.findById(episodeModel.getFilmId()).orElseThrow(() -> new EntityNotFoundException("Not found"));

        episode.setFilm(film);
        episode.setEpisodeLink(episodeModel.getEpisodeLink());
        episode.setEpisodeNumber((int) episodeModel.getEpisodeNumber());
        episode.setUpdatedAt(Calendar.getInstance().getTime());

        episodeRepository.save(episode);

        return episode;
    }

    @Override
    public void deleteEpisode(long id) {
        //episodeRepository.deleteByEpisodeId(id);
        removeEntity(id);
    }


    public void removeEntity(Long entityId) {
        Episode episode = entityManager.find(Episode.class, entityId);
        if (episode != null) {

            episode.setFilm(null);
            entityManager.remove(episode);
        }
    }

    @Override
    public List<Episode> getAllEpisode() {
        return  episodeRepository.findAll();
    }
}
