package com.project.ticketBooking.services;

import com.project.ticketBooking.models.Event;
import com.project.ticketBooking.models.Organization;
import com.project.ticketBooking.services.interfaces.IExcelService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelService implements IExcelService {
    private final EventService eventService;
    private final OrganizationService organizationService;

    @Override
    public void exportEventToExcel(HttpServletResponse response) {
        try {
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Events");

            Row headerRow = sheet.createRow(0);
            String[] headers  = {"ID", "Name", "Description", "Start Date", "End Date", "Location", "Status"};
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }
            List<Event> events = eventService.getAllEvents();
            int rowNum = 1;
            for (Event event : events) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(event.getId());
                row.createCell(1).setCellValue(event.getName());
                row.createCell(2).setCellValue(event.getDescription());
                row.createCell(3).setCellValue(event.getStartDate().toString());
                row.createCell(4).setCellValue(event.getEndDate().toString());
                row.createCell(5).setCellValue(event.getLocation());
                row.createCell(6).setCellValue(event.getStatus());
            }
            ServletOutputStream ops = response.getOutputStream();
            workbook.write(ops);
            workbook.close();
            ops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportOrganizationToExcel(HttpServletResponse response) {
        try{
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Organizations");
            Row headerRow = sheet.createRow(0);
            String[] headers  = {"ID", "Name"};
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }
            List<Organization> organizations = organizationService.getAllOrganizations();
            int rowNum = 1;
            for (Organization organization : organizations) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(organization.getId());
                row.createCell(1).setCellValue(organization.getName());
            }
            ServletOutputStream ops = response.getOutputStream();
            workbook.write(ops);
            workbook.close();
            ops.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
