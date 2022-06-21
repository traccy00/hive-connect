package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.category.UpdateCategoryRequest;
import fpt.edu.capstone.entity.Category;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.CategoryRepository;
import fpt.edu.capstone.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

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
    public List<Category> searchCategoryList(String name) {
        return categoryRepository.searchCategoryList(name);
    }

    @Override
    public void updateCategory(UpdateCategoryRequest request) {
        Category category = categoryRepository.getById(request.getId());
        if(category == null){
            throw new HiveConnectException("Category does not exist");
        }
        Object CreateCategoryRequest = request;
        category = modelMapper.map(CreateCategoryRequest, Category.class);
        categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void deleteCategory(long categoryId) {
        Category category = categoryRepository.getById(categoryId);
        if(category == null){
            throw new HiveConnectException("Category does not exist");
        }
        categoryRepository.deleteCategory(categoryId);
    }
}
