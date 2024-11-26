package com.project.ticketBooking.controllers;

import com.project.ticketBooking.services.EventService;
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

    @GetMapping("/export/events")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportEventToExcel(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=events.xls");
        response.setContentType("application/octet-stream");
        excelService.exportEventToExcel(response);
    }
}
