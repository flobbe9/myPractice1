package com.example.myPractice1.config;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


public class CreatePdf {

    public void test() throws IOException {
        PDDocument pdDocument = new PDDocument();
        PDPage pdPage = new PDPage();
        pdDocument.addPage(pdPage);
        
        PDPageContentStream pageContentStream = new PDPageContentStream(pdDocument, pdPage);
        pageContentStream.setFont(PDType1Font.COURIER, 12);
        pageContentStream.beginText();
        pageContentStream.showText("Hiiiii");
        pageContentStream.endText();
        pageContentStream.close();
    

        pdDocument.save("./src/main/java/com/example/myPractice1/config/myPdf.pdf");
        pdDocument.close();
    }
}