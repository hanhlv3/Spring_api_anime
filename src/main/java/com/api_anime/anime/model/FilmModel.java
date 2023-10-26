package com.api_anime.anime.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilmModel {

    private String filmName;

    private String description;

    private int episodesQuantity;

    //private byte[] image;
    //private MultipartFile image;

    private Date releaseDate;
    private long[] categories;

}
