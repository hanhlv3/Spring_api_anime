package com.api_anime.anime.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategoryModel {

    @NotBlank(message = "Not found category name")
    @NotEmpty(message = "Category name not empty")
    private String categoryName;

}
