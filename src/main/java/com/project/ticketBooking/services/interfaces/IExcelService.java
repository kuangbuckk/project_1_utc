package com.project.ticketBooking.services.interfaces;

import com.project.ticketBooking.models.Event;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface IExcelService {
    void exportEventByStatusToExcel(HttpServletResponse response);
    void exportEventToExcel(HttpServletResponse response);
    void exportOrganizationToExcel(HttpServletResponse response);
    void exportEventByCategoryToExcel(HttpServletResponse response);
}
