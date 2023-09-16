package com.api_anime.anime.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "episodes"
)
public class Episode {

    @Id
    @NotBlank
    @NotEmpty
    @SequenceGenerator(
            name = "episode_sequence",
            sequenceName = "episode_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "episode_sequence"
    )
    private Long episodeId;


    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "film_id",
            referencedColumnName = "filmId"
    )
    private Film film;


    @Column(name = "episode_link")
    private String episodeLink;


    @Column(name = "episode_number")
    private int episodeNumber;



    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
