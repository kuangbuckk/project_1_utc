package com.project.ticketBooking.controllers;

import com.project.ticketBooking.dtos.EventDTO;
import com.project.ticketBooking.dtos.EventImageDTO;
import com.project.ticketBooking.models.Event;
import com.project.ticketBooking.models.EventImage;
import com.project.ticketBooking.services.EventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/events")
@AllArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping("")
    public ResponseEntity<?> getAllEvents() {
        List<Event> Events = eventService.getAllEvents();
        return ResponseEntity.ok(Events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(
            @Valid @PathVariable("id") Long eventId
    ) {
        try {
            Event exisitingEvent = eventService.getEventById(eventId);
            return ResponseEntity.ok(exisitingEvent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> insertEvent(
            @Valid @RequestBody EventDTO eventDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Event newEvent = eventService.createEvent(eventDTO);
            return ResponseEntity.ok(newEvent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @PathVariable("id") Long eventId,
            @RequestParam("files") List<MultipartFile> files
    ) {
        try {
            Event existingEvent = eventService.getEventById(eventId);
            if (files == null) {
                files = new ArrayList<>();
            }
            if (files.size() > EventImage.MAXIMUM_IMAGES_PER_EVENT) {
                return ResponseEntity.badRequest().body("You can only upload maximum 5 images");
            }
            List<EventImage> eventImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue;
                }
                // Kiểm tra kích thước file và định dạng
                if (file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
                // Lưu file và cập nhật thumbnail trong DTO
                String filename = storeFile(file); // Thay thế hàm này với code của bạn để lưu file
                //lưu vào đối tượng product trong DB
                EventImage eventImage = eventService.createEventImage(
                        existingEvent.getId(),
                        EventImageDTO.builder()
                                .imageUrl(filename)
                                .build()
                );
                eventImages.add(eventImage);
            }
            return ResponseEntity.ok().body(eventImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // Đường dẫn đến thư mục mà bạn muốn lưu file
        java.nio.file.Path uploadDir = Paths.get("uploads");
        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(
            @Valid @PathVariable("id") Long eventId,
            @Valid @RequestBody EventDTO eventDTO
    ) {
        try {
            Event newEvent = eventService.updateEvent(eventId, eventDTO);
            return ResponseEntity.ok(newEvent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(
            @Valid @PathVariable("id") Long eventId
    ) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok("Event deleted with ID: " + eventId);
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<?> getEventsByOrganizationId(@PathVariable Long organizationId) {
        try {
            List<Event> events = eventService.getAllEventsByOrganizationId(organizationId);
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
