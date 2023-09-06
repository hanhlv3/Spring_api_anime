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
        name = "film_cmt"
)
public class FilmCmt {
    @Id
    @NotBlank
    @NotEmpty
    @SequenceGenerator(
            name = "film_cmt_sequence",
            sequenceName = "film_cmt_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "film_cmt_sequence"
    )
    private Long filmCmtId;

    @Column(
            name = "user_id",
            nullable = false
    )
    private Long userId;

    @Column(
            name = "film_id",
            nullable = false
    )
    private Long filmId;

    @Column(
            name = "comment_content",
            nullable = false
    )
    private String commentContent;

    @Column(name = "film_cmt_parent_id")
    private Long filmCmtParentId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
