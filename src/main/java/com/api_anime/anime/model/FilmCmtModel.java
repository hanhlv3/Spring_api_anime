package com.api_anime.anime.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmCmtModel {

    private String commentContent;
    private long filmCmtParentId;
}
