package com.project.ticketBooking.services;

import com.project.ticketBooking.dtos.CategoryDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Category;
import com.project.ticketBooking.repositories.CategoryRepository;
import com.project.ticketBooking.services.interfaces.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService {
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long categoryId) throws DataNotFoundException {
        return categoryRepository.findById(categoryId)
                .orElseThrow(()-> new DataNotFoundException("Cant find category with id: " + categoryId));
    }

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category newcategory = Category.builder()
                .name(categoryDTO.getName())
                .build();
        return categoryRepository.save(newcategory);
    }

    @Override
    public Category updateCategory(Long categoryId, CategoryDTO categoryDTO) throws DataNotFoundException {
        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setName(categoryDTO.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
