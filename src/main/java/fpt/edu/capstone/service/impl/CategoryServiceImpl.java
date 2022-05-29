package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.sprint1.Category;
import fpt.edu.capstone.repository.CategoryRepository;
import fpt.edu.capstone.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public boolean existById(long categoryId) {
        return categoryRepository.existsById(categoryId);
    }

    @Override
    public Optional<Category> findCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<Category> getListCategory(String name) {
        return categoryRepository.findAll();
    }
}
