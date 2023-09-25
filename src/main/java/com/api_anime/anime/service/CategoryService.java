package com.api_anime.anime.service;


import com.api_anime.anime.entity.Category;
import com.api_anime.anime.model.CategoryModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Category insertCategory(CategoryModel category);

    Category getCategoryById(long id);

    List<Category> getAllCategories();

    Category updateCategory(long id, String categoryName);

    void deleteCategory(long id);
}
