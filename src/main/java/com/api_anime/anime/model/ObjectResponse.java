package com.api_anime.anime.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectResponse {
    private int status;
    private String message;
}
