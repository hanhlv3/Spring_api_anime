package com.api_anime.anime.repository;

import com.api_anime.anime.entity.Category;
import com.api_anime.anime.entity.Film;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    List<Film> findAllByCategories(Category categories);

}
