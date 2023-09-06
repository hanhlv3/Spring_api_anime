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

    @Column(
            name = "user_id",
            nullable = false
    )
    private Long userId;

    @Column(
            name = "episode_id",
            nullable = false
    )
    private Long episodeId;

    @Column(
            name = "comment_content",
            nullable = false
    )
    private String commentContent;

    @Column(name = "episode_cmt_parent_id")
    private Long episodeCmtParentId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
