package com.api_anime.anime.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "films")
public class Film {

    @Id
    @NotBlank
    @NotEmpty
    @SequenceGenerator(
            name = "film_sequence",
            sequenceName = "film_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "film_sequence"
    )
    @Column(name = "film_id")
    private Long filmId;

    @Column(
            name = "film_name",
            unique = true
    )
    private String filmName;

    @Column(name = "description")
    private String description;

    @Column(name = "img")
    private String img;
    @Column(name = "episodes_quantity")
    private int episodesQuantity;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

}
