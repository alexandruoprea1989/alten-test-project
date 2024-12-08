package com.alexandru.alten.controller;

import com.alexandru.alten.model.api.ApiCategory;
import com.alexandru.alten.model.db.Category;
import com.alexandru.alten.model.form.FormCategory;
import com.alexandru.alten.store.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    public record GetCategoriesResult(List<ApiCategory> categories) {}

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/category")
    public GetCategoriesResult getCategories(@RequestParam(value = "name", defaultValue = "") String name) {
        List<Category> storedCategories = name.isBlank() ?
                categoryRepository.findAll() : categoryRepository.findByNameContainingIgnoreCase(name);
        List<ApiCategory> categoriesToReturn = storedCategories.stream().map(ApiCategory::fromDb).toList();

        return new GetCategoriesResult(categoriesToReturn);
    }

    @PostMapping("/category")
    public ApiCategory createCategory(@RequestBody FormCategory form) {
        // TODO Add form validation

        Category categoryToSave = Category.withForm(form);
        Category savedCategory = categoryRepository.save(categoryToSave);

        return ApiCategory.fromDb(savedCategory);
    }
}
