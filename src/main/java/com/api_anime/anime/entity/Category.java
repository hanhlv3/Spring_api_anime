package com.api_anime.anime.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    @NotNull
    @Column(
            name = "category_name",
            nullable = false

    )
    private String categoryName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "film_category_map",
            joinColumns = @JoinColumn(
                    name = "category_id",
                    referencedColumnName = "categoryId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "film_id",
                    referencedColumnName = "filmId"
            )
    )
    private List<Film> films;

    @Column(name = "created_at")
    private Date createdAt = Calendar.getInstance().getTime();

    @Column(name = "updated_at")
    private Date updatedAt;
}
