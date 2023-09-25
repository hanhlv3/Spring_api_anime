package com.api_anime.anime.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluateModel {

    private long evaluateValue;
    private long filmId;
}
