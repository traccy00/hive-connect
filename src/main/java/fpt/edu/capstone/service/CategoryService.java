package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.category.CreateCategoryRequest;
import fpt.edu.capstone.dto.category.UpdateCategoryRequest;
import fpt.edu.capstone.entity.sprint1.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    boolean existById(long categoryId);

    Optional<Category> findCategoryByName(String name);

    void saveCategory(Category category);

    List<Category> searchCategoryList(String name);

    void updateCategory(UpdateCategoryRequest request);

    void deleteCategory(long categoryId);
}
