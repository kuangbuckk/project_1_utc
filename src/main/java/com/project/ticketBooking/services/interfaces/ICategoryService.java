package com.project.ticketBooking.services.interfaces;

import com.project.ticketBooking.dtos.CategoryDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Long id) throws DataNotFoundException;
    Category createCategory(CategoryDTO categoryDTO);
    Category updateCategory(Long categoryId, CategoryDTO categoryDTO) throws DataNotFoundException;
    void deleteCategory(Long categoryId);
}
