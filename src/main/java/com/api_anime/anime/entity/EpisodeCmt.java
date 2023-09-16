package com.api_anime.anime.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "episode_cmt")
public class EpisodeCmt {
    @Id
    @NotBlank
    @NotEmpty
    @SequenceGenerator(
            name = "episode_cmt_sequence",
            sequenceName = "episode_cmt_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "episode_cmt_sequence"
    )
    private Long episodeCmtId;


    // user
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId"
    )
    private User user;


    // episode
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "episode_id",
            referencedColumnName = "episodeId"
    )
    private Episode episode;



    @Column(
            name = "comment_content",
            nullable = false
    )
    private String commentContent;

    @Column(name = "episode_cmt_parent_id")
    private Long episodeCmtParentId;

    @Column(
            name = "status",
            columnDefinition = "int default 0"

    ) // 0 - isDefault (not accept) : 1 - accept
    private int status;

    @Column(name = "film_cmt_parent_id")
    private Long filmCmtParentId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
