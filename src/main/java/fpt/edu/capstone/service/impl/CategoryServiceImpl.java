package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.repository.CategoryRepository;
import fpt.edu.capstone.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public boolean existById(long categoryId) {
        return categoryRepository.existsById(categoryId);
    }
}
