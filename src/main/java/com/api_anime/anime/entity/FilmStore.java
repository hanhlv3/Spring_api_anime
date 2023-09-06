package com.api_anime.anime.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "film_stores"
)
public class FilmStore {

    @Id
    @Column(
            name = "user_id",
            nullable = false
    )
    private Long userId;

    @Id
    @Column(
            name = "film_id",
            nullable = false
    )
    private Long filmId;
}
