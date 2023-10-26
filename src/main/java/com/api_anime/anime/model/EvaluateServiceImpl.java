package com.api_anime.anime.model;

import com.api_anime.anime.entity.EvaluateFilm;
import com.api_anime.anime.entity.Film;
import com.api_anime.anime.repository.EvaluateFilmRepository;
import com.api_anime.anime.service.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluateServiceImpl implements EvaluateService {

    @Autowired
    private EvaluateFilmRepository evaluateFilmRepository;
    @Override
    public double getScoreByFilmId(Film film) {
        List<EvaluateFilm> listEvaluate = evaluateFilmRepository.findAllByFilm(film);
        double score = -1.0;
        if (!listEvaluate.isEmpty()) {

            int sumValue = 0;
            for (EvaluateFilm item: listEvaluate) {
                sumValue += item.getEvaluateValue();
            }


            score = sumValue/listEvaluate.size();
        }

        return score;
    }
}
