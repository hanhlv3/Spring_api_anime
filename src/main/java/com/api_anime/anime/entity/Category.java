package com.api_anime.anime.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "categories"
)
public class Category {

    @Id
    @NotBlank
    @NotEmpty
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_sequence"
    )
    private Long categoryId;

    @NotBlank(message = "Category name not valid")
    @NotEmpty()
    @Column(name = "category_name")
    private String categoryName;

    @ManyToMany(
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "film_category_map",
            joinColumns = @JoinColumn(
                    name = "category_id",
                    referencedColumnName = "categoryId"
            ),
            inverseJoinColumns = @JoinColumn(
                    table = "films",
                    name = "film_id",
                    referencedColumnName = "filmId"
            )
    )
    private List<Film> films;
    public void addFilms(Film film) {
        if(films.isEmpty()) films = new ArrayList<>();
        films.add(film);
    }
}
