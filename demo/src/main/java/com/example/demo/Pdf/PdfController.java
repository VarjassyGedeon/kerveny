package com.example.demo.Pdf;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/pdf")
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping("/extract")
    public String extractText(@RequestParam("file") MultipartFile file) {
        try {
            return pdfService.extractText(file);
        } catch (Exception e) {
            return "Error extracting text: " + e.getMessage();
        }
    }

    @PostMapping("/extract-subjects")
    public ResponseEntity<List<Map<String, String>>> extractSubjects(@RequestParam("file") MultipartFile file) {
        try {
            List<Map<String, String>> subjects = pdfService.extractSubjects(file);
            return ResponseEntity.ok(subjects);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(List.of(Map.of("error", "Error extracting subjects: " + e.getMessage())));
        }
    }

    @PostMapping("/extract-codes")
    public List<String> extractCodes(@RequestParam("file") MultipartFile file) {
        try {
            return pdfService.extractMatchingCodes(file);
        } catch (Exception e) {
            return List.of("Error extracting codes: " + e.getMessage());
        }
    }

    @PostMapping("/extract-codes/{szakId}")
    public List<String> extractCodesBySzak(
            @PathVariable Long szakId,
            @RequestParam("file") MultipartFile file) {
        try {
            return pdfService.extractCodesBySzak(file, szakId);
        } catch (Exception e) {
            throw new RuntimeException("Error extracting filtered codes: " + e.getMessage());
        }
    }

    @PostMapping("/extract-links/{szakId}")
    public List<Map<String, Object>> extractLinkedCodes(
            @PathVariable Long szakId,
            @RequestParam("file") MultipartFile file) {
        try {
            return pdfService.extractConnectionsForSzak(file, szakId);
        } catch (Exception e) {
            throw new RuntimeException("Error extracting connections: " + e.getMessage(), e);
        }
    }

}
