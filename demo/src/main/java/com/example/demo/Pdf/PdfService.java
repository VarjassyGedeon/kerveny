package com.example.demo.Pdf;

import com.example.demo.Kapcsolat.KapcsolatRepository;
import com.example.demo.Szak.SzakRepository;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import com.example.demo.Tantargy.TantargyRepository;
import com.example.demo.Tantargy.Tantargy;
import com.example.demo.Tantargy.TantargyRepository;


import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Rectangle;
import java.util.stream.Collectors;

@Service
public class PdfService {
    @Autowired
    private final TantargyRepository tantargyRepository;
    @Autowired
    private SzakRepository szakRepository;
    @Autowired
    private KapcsolatRepository kapcsolatRepository;

    public PdfService(TantargyRepository tantargyRepository,
                      SzakRepository szakRepository,
                      KapcsolatRepository kapcsolatRepository) {
        this.tantargyRepository = tantargyRepository;
        this.szakRepository = szakRepository;
        this.kapcsolatRepository = kapcsolatRepository;
    }

    public String extractText(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);

            return stripper.getText(document);
        }
    }

    public List<String> extractCodesBySzak(MultipartFile file, Long szakId) throws IOException {
        // Extract raw text from PDF
        String text = extractText(file);

        // Get all tantargyak belonging to the selected szak
        var szak = szakRepository.findById(szakId)
                .orElseThrow(() -> new RuntimeException("Szak not found"));

        var tantargyak = szak.getTantargyak();

        List<String> foundCodes = new ArrayList<>();
        for (var t : tantargyak) {
            if (text.contains(t.getKod())) {
                foundCodes.add(t.getKod());
            }
        }

        return foundCodes;
    }


    public List<Map<String, String>> extractSubjects(MultipartFile file) throws IOException {
        String text = extractText(file);
        List<Map<String, String>> subjects = new ArrayList<>();

        Pattern pattern = Pattern.compile(
                "^([A-Z0-9\\-]+)\\s+(.+?)\\s+(Excellent|Good|Satisfactory|Pass|Fail|Not completed|Passed)\\s*\\(?([0-9])?\\)?",
                Pattern.MULTILINE | Pattern.UNICODE_CHARACTER_CLASS
        );

        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            Map<String, String> subject = new HashMap<>();
            subject.put("code", matcher.group(1).trim());
            subject.put("name", matcher.group(2).trim());
            subject.put("grade", matcher.group(3).trim() + (matcher.group(4) != null ? " (" + matcher.group(4) + ")" : ""));
            subjects.add(subject);
        }

        return subjects;
    }

    public List<String> extractMatchingCodes(MultipartFile file) throws IOException {
        String text = extractText(file); // reuse your existing method

        // Get all known subject codes from DB
        List<String> allCodes = tantargyRepository.findAll()
                .stream()
                .map(Tantargy::getKod)
                .toList();

        // Filter codes that appear in the PDF text
        List<String> matchedCodes = new ArrayList<>();
        for (String code : allCodes) {
            if (text.contains(code)) {
                matchedCodes.add(code);
            }
        }

        return matchedCodes;
    }

    public List<Map<String, Object>> extractConnectionsForSzak(MultipartFile file, Long szakId) throws IOException {
        // 1️⃣ Extract all codes from PDF
        String text = extractText(file); // or your existing extractText()
        List<String> pdfCodes = extractCodesFromText(text);

        // 2️⃣ Get all tantargyak for the szak
        var szak = szakRepository.findById(szakId).orElseThrow();
        Set<Tantargy> szakTantargyak = szak.getTantargyak();

        List<Map<String, Object>> result = new ArrayList<>();

        // 3️⃣ For each tantargy in szak, find related PDF codes via Kapcsolat
        for (Tantargy t : szakTantargyak) {
            List<String> linkedPdfCodes = new ArrayList<>();

            for (String pdfCode : pdfCodes) {
                if (kapcsolatRepository.existsByTantargyPair(t.getKod(), pdfCode)) {
                    linkedPdfCodes.add(pdfCode);
                }
            }

            Map<String, Object> row = new HashMap<>();
            row.put("tantargyKod", t.getKod());
            row.put("linkedPdfCodes", linkedPdfCodes);
            result.add(row);
        }

        return result;
    }

    private List<String> extractCodesFromText(String text) {
        // simple regex to catch codes like INF123, BPM456-17 etc.
        List<String> codes = new ArrayList<>();
        var pattern = java.util.regex.Pattern.compile("\\b[A-Z]{2,5}[A-Z0-9-]{2,}\\b");
        var matcher = pattern.matcher(text);
        while (matcher.find()) {
            codes.add(matcher.group());
        }
        return codes;
    }

}

