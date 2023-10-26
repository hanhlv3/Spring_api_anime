package com.api_anime.anime.service;


import com.api_anime.anime.entity.Category;
import com.api_anime.anime.execptions.BadRequestException;
import com.api_anime.anime.model.CategoryModel;
import com.api_anime.anime.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Category insertCategory(CategoryModel categoryModel) {
        Category category = new Category();
        category.setCategoryName(categoryModel.getCategoryName());
        categoryRepository.save(category);
        return category;
    }

    @Override
    public Category getCategoryById(long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if(category.isEmpty()) return null;
        return category.get();
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        for (Category cat : categoryList) {
            cat.setFilms(null);
        }
        return categoryList;
    }

    @Override
    public Category updateCategory(long id, String categoryName) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            throw new BadRequestException();
        }
        category.get().setCategoryName(categoryName);
        category.get().setUpdatedAt(Calendar.getInstance().getTime());
        categoryRepository.save(category.get());

        return category.get();
    }

    @Override
    public void deleteCategory(long id) {
        Category  category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not exits"));
        category.getFilms().clear();
        categoryRepository.delete(category);
    }
}
