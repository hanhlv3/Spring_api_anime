package com.api_anime.anime.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

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

    // user
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId"
    )
    private User user;

    // film
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "film_id",
            referencedColumnName = "filmId"
    )
    private Film film;

    @Column(name = "evaluate_value")
    private int evaluateValue;

    @Column(name = "created_at")
    private Date createdAt = Calendar.getInstance().getTime();

}
