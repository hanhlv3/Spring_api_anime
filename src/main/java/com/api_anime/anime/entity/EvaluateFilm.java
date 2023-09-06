package com.api_anime.anime.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
        name = "evaluate_film"
)
public class EvaluateFilm {

   
    @Id
    @NotBlank
    @NotEmpty
    @SequenceGenerator(
            name = "evaluate_film_sequence",
            sequenceName = "evaluate_film_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "evaluate_film_sequence"
    )
    private Long evaluateFilmId;
 
    @Column(name = "user_id")
    private Long userId;

   
    @Column(name = "film_id")
    private Long filmId;

    @Column(name = "evaluate_value")
    private int evaluateValue;

}
