package com.api_anime.anime.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeModel {

    private String episodeLink;
    private long episodeNumber;
    private long filmId;

}
