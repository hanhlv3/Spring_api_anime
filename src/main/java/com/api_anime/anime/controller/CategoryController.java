package com.api_anime.anime.controller;


import com.api_anime.anime.entity.Category;
import com.api_anime.anime.execptions.BadRequestException;
import com.api_anime.anime.model.CategoryModel;
import com.api_anime.anime.model.ObjectResponse;
import com.api_anime.anime.service.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/api/v1/public/category/{id}")
    private ResponseEntity<?> getCategoryById(@PathVariable long id) {

        Category category = categoryService.getCategoryById(id);
        if(category == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ObjectResponse(400, "Category not found"));

        return ResponseEntity.ok(category);
    }

    @GetMapping("/api/v1/public/categories")
    private ResponseEntity<List<Category>> getAllCategory() {

        List<Category> listCategories = categoryService.getAllCategories();
        return ResponseEntity.ok(listCategories);
    }

    @PostMapping("/api/v1/private/category")
    private ResponseEntity<Category> insertCategory(@RequestBody @Valid CategoryModel category, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new BadRequestException();
        }
        Category result = categoryService.insertCategory(category);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/api/v1/private/category/{id}")
    private ResponseEntity<?> updateCategory(@PathVariable long id, @RequestBody CategoryModel categoryModel) {

        Category category = categoryService.updateCategory(id, categoryModel.getCategoryName());
        if (category == null)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ObjectResponse(400, "Category not exist"));

        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/api/v1/private/category/{id}")
    private ResponseEntity<?> deleteCategory(@PathVariable long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete oke");
    }
}
