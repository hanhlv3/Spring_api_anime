package com.api_anime.anime.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "film_category_map",
            joinColumns = @JoinColumn(
                    name = "film_id",
                    referencedColumnName = "filmId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id",
                    referencedColumnName = "categoryId"
            )
    )
    private List<Category> categories;

    @Column(name = "created_at")
    private Date createdAt = Calendar.getInstance().getTime();

    @Column(name = "updated_at")
    private Date updatedAt;

    public void addCategory(Category category) {
        if(categories.isEmpty()) this.categories = new ArrayList<>();
        categories.add(category);
    }

    @Transient
    private double score;

    @Transient
    private int currentEpisode;


}
