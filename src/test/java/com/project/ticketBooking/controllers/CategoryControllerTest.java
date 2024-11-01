package com.project.ticketBooking.controllers;

import com.project.ticketBooking.dtos.CategoryDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Category;
import com.project.ticketBooking.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCategories_returnsListOfCategories() {
        List<Category> categories = List.of(new Category(), new Category());
        when(categoryService.getAllCategories()).thenReturn(categories);

        ResponseEntity<List<Category>> response = categoryController.getAllCategories();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(categories, response.getBody());
    }

    @Test
    void insertCategory_withValidData_returnsNewCategory() {
        CategoryDTO categoryDTO = new CategoryDTO();
        Category newCategory = new Category();
        when(categoryService.createCategory(categoryDTO)).thenReturn(newCategory);
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<?> response = categoryController.insertCategory(categoryDTO, bindingResult);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(newCategory, response.getBody());
    }

    @Test
    void insertCategory_withInvalidData_returnsErrorMessages() {
        CategoryDTO categoryDTO = new CategoryDTO();
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(new FieldError("categoryDTO", "name", "Name is required")));

        ResponseEntity<?> response = categoryController.insertCategory(categoryDTO, bindingResult);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof List);
        assertEquals("Name is required", ((List<?>) response.getBody()).get(0));
    }

    @Test
    void updateCategory_withValidData_returnsUpdatedCategoryName() throws DataNotFoundException {
        Long categoryId = 1L;
        CategoryDTO categoryDTO = new CategoryDTO();
        Category updatedCategory = new Category();
        updatedCategory.setName("Updated Name");
        when(categoryService.updateCategory(categoryId, categoryDTO)).thenReturn(updatedCategory);

        ResponseEntity<?> response = categoryController.updateCategory(categoryId, categoryDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Name", response.getBody());
    }

    @Test
    void deleteCategory_withValidId_returnsCategoryId() {
        Long categoryId = 1L;
        doNothing().when(categoryService).deleteCategory(categoryId);

        ResponseEntity<?> response = categoryController.deleteCategory(categoryId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(categoryId, response.getBody());
    }
}