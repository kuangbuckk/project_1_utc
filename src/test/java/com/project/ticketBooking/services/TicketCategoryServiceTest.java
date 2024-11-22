package com.project.ticketBooking.services;

import static org.junit.jupiter.api.Assertions.*;
import com.project.ticketBooking.dtos.TicketCategoryDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Event;
import com.project.ticketBooking.models.TicketCategory;
import com.project.ticketBooking.repositories.EventRepository;
import com.project.ticketBooking.repositories.TicketCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class TicketCategoryServiceTest {

    @Mock
    private TicketCategoryRepository ticketCategoryRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private TicketCategoryService ticketCategoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTicketCategories_returnsAllCategories() {
        List<TicketCategory> categories = Arrays.asList(new TicketCategory(), new TicketCategory());
        when(ticketCategoryRepository.findAll()).thenReturn(categories);

        List<TicketCategory> result = ticketCategoryService.getAllTicketCategories();

        assertEquals(2, result.size());
        verify(ticketCategoryRepository, times(1)).findAll();
    }

    @Test
    void getTicketCategoryById_validId_returnsCategory() throws DataNotFoundException {
        TicketCategory category = new TicketCategory();
        when(ticketCategoryRepository.findById(1L)).thenReturn(Optional.of(category));

        TicketCategory result = ticketCategoryService.getTicketCategoryById(1L);

        assertNotNull(result);
        verify(ticketCategoryRepository, times(1)).findById(1L);
    }

    @Test
    void getTicketCategoryById_invalidId_throwsException() {
        when(ticketCategoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> ticketCategoryService.getTicketCategoryById(1L));
        verify(ticketCategoryRepository, times(1)).findById(1L);
    }

    @Test
    void createTicketCategory_validDTO_returnsCreatedCategory() throws DataNotFoundException {
        TicketCategoryDTO dto = new TicketCategoryDTO();
        dto.setEventId(1L);
        Event event = new Event();
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(ticketCategoryRepository.save(any(TicketCategory.class))).thenReturn(new TicketCategory());

        TicketCategory result = ticketCategoryService.createTicketCategory(dto);

        assertNotNull(result);
        verify(eventRepository, times(1)).findById(1L);
        verify(ticketCategoryRepository, times(1)).save(any(TicketCategory.class));
    }

    @Test
    void createTicketCategory_invalidEventId_throwsException() {
        TicketCategoryDTO dto = new TicketCategoryDTO();
        dto.setEventId(1L);
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> ticketCategoryService.createTicketCategory(dto));
        verify(eventRepository, times(1)).findById(1L);
        verify(ticketCategoryRepository, times(0)).save(any(TicketCategory.class));
    }

    @Test
    void updateTicketCategory_validIdAndDTO_returnsUpdatedCategory() throws DataNotFoundException {
        TicketCategoryDTO dto = new TicketCategoryDTO();
        dto.setEventId(1L);
        TicketCategory category = new TicketCategory();
        Event event = new Event();
        when(ticketCategoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(ticketCategoryRepository.save(any(TicketCategory.class))).thenReturn(category);

        TicketCategory result = ticketCategoryService.updateTicketCategory(1L, dto);

        assertNotNull(result);
        verify(ticketCategoryRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).findById(1L);
        verify(ticketCategoryRepository, times(1)).save(any(TicketCategory.class));
    }

    @Test
    void updateTicketCategory_invalidCategoryId_throwsException() {
        TicketCategoryDTO dto = new TicketCategoryDTO();
        dto.setEventId(1L);
        when(ticketCategoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> ticketCategoryService.updateTicketCategory(1L, dto));
        verify(ticketCategoryRepository, times(1)).findById(1L);
        verify(eventRepository, times(0)).findById(1L);
        verify(ticketCategoryRepository, times(0)).save(any(TicketCategory.class));
    }

    @Test
    void updateTicketCategory_invalidEventId_throwsException() {
        TicketCategoryDTO dto = new TicketCategoryDTO();
        dto.setEventId(1L);
        TicketCategory category = new TicketCategory();
        when(ticketCategoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> ticketCategoryService.updateTicketCategory(1L, dto));
        verify(ticketCategoryRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).findById(1L);
        verify(ticketCategoryRepository, times(0)).save(any(TicketCategory.class));
    }

    @Test
    void deleteTicketCategory_validId_deletesCategory() {
        doNothing().when(ticketCategoryRepository).deleteById(1L);

        ticketCategoryService.deleteTicketCategory(1L);

        verify(ticketCategoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void getTicketCategoriesByEventId_validEventId_returnsCategories() throws DataNotFoundException {
        Event event = new Event();
        List<TicketCategory> categories = Arrays.asList(new TicketCategory(), new TicketCategory());
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(ticketCategoryRepository.findByEventId(1L)).thenReturn(categories);

        List<TicketCategory> result = ticketCategoryService.getTicketCategoriesByEventId(1L);

        assertEquals(2, result.size());
        verify(eventRepository, times(1)).findById(1L);
        verify(ticketCategoryRepository, times(1)).findByEventId(1L);
    }

    @Test
    void getTicketCategoriesByEventId_invalidEventId_throwsException() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> ticketCategoryService.getTicketCategoriesByEventId(1L));
        verify(eventRepository, times(1)).findById(1L);
        verify(ticketCategoryRepository, times(0)).findByEventId(1L);
    }

    @Test
    void getTicketCategoriesByIds_validIds_returnsCategories() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<TicketCategory> categories = Arrays.asList(new TicketCategory(), new TicketCategory());
        when(ticketCategoryRepository.findTicketCategoriesByIds(ids)).thenReturn(categories);

        List<TicketCategory> result = ticketCategoryService.getTicketCategoriesByIds(ids);

        assertEquals(2, result.size());
        verify(ticketCategoryRepository, times(1)).findTicketCategoriesByIds(ids);
    }

    @Test
    void hasAvailableTickets_validCategoryId_returnsTrue() {
        when(ticketCategoryRepository.existsByIdAndRemainingCountGreaterThan(1L, 0)).thenReturn(true);

        boolean result = ticketCategoryService.hasAvailableTickets(1L);

        assertTrue(result);
        verify(ticketCategoryRepository, times(1)).existsByIdAndRemainingCountGreaterThan(1L, 0);
    }

    @Test
    void hasAvailableTickets_invalidCategoryId_returnsFalse() {
        when(ticketCategoryRepository.existsByIdAndRemainingCountGreaterThan(1L, 0)).thenReturn(false);

        boolean result = ticketCategoryService.hasAvailableTickets(1L);

        assertFalse(result);
        verify(ticketCategoryRepository, times(1)).existsByIdAndRemainingCountGreaterThan(1L, 0);
    }

}