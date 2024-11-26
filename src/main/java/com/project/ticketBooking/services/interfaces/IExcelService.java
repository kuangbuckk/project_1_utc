package com.project.ticketBooking.services.interfaces;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface IExcelService {
    void exportEventToExcel(HttpServletResponse response);
    void exportOrganizationToExcel(HttpServletResponse response);
}
