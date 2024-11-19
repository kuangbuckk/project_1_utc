package com.project.ticketBooking.services;

import com.project.ticketBooking.dtos.CategoryDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Category;
import com.project.ticketBooking.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCategories_returnsListOfCategories() {
        List<Category> categories = List.of(new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(categories, result);
    }

    @Test
    void getCategoryById_withValidId_returnsCategory() throws DataNotFoundException {
        Long categoryId = 1L;
        Category category = new Category();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Category result = categoryService.getCategoryById(categoryId);

        assertEquals(category, result);
    }

    @Test
    void getCategoryById_withInvalidId_throwsDataNotFoundException() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> categoryService.getCategoryById(categoryId));
    }

    @Test
    void createCategory_withValidData_returnsNewCategory() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("New Category");
        Category newCategory = new Category();
        when(categoryRepository.save(any(Category.class))).thenReturn(newCategory);

        Category result = categoryService.createCategory(categoryDTO);

        assertEquals(newCategory, result);
    }

    @Test
    void updateCategory_withValidData_returnsUpdatedCategory() throws DataNotFoundException {
        Long categoryId = 1L;
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Updated Category");
        Category existingCategory = new Category();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);

        Category result = categoryService.updateCategory(categoryId, categoryDTO);

        assertEquals(existingCategory, result);
    }

    @Test
    void updateCategory_withInvalidId_throwsDataNotFoundException() {
        Long categoryId = 1L;
        CategoryDTO categoryDTO = new CategoryDTO();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> categoryService.updateCategory(categoryId, categoryDTO));
    }

    @Test
    void deleteCategory_withValidId_deletesCategory() {
        Long categoryId = 1L;
        doNothing().when(categoryRepository).deleteById(categoryId);

        categoryService.deleteCategory(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }
}