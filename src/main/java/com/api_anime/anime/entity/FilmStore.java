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
        name = "film_stores"
)
public class FilmStore {


    @Id
    @NotBlank
    @NotEmpty
    @SequenceGenerator(
            name = "film_store_sequence",
            sequenceName = "film_store_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "film_store_sequence"
    )
    private Long filmStoreId;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId"
    )
    private User user;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "film_id",
            referencedColumnName = "filmId"
    )
    private Film film;


    @Column(name = "created_at")
    private Date createdAt = Calendar.getInstance().getTime();
}
