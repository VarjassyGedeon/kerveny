package com.example.demo.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class pdf {

    public static void main(String[] args) {
        try {
            File file = new File("example.pdf"); // path to your PDF
            PDDocument document = PDDocument.load(file);

            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);

            System.out.println(text);

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}