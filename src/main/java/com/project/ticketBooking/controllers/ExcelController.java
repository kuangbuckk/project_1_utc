package com.project.ticketBooking.controllers;

import com.project.ticketBooking.services.ExcelService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/excel")
@RequiredArgsConstructor
public class ExcelController {
    private final ExcelService excelService;

    @GetMapping("/export/events/status")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportEventStatusToExcel(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=eventsByStatus.xlsx");
        response.setContentType("application/octet-stream");
        excelService.exportEventByStatusToExcel(response);
    }

    @GetMapping("/export/events/category")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportEventCategoryToExcel(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=eventsByCategory.xlsx");
        response.setContentType("application/octet-stream");
        excelService.exportEventByCategoryToExcel(response);
    }

    @GetMapping("/export/events")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER')")
    public void exportEventToExcel(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=events.xls");
        response.setContentType("application/octet-stream");
        excelService.exportEventToExcel(response);
    }

    @GetMapping("/export/organizations")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportOrganizationToExcel(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=organizations.xls");
        response.setContentType("application/octet-stream");
        excelService.exportOrganizationToExcel(response);
    }
}
