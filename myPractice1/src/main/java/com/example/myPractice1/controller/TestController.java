package com.example.myPractice1.controller;

import java.io.InputStream;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myPractice1.test.ResourceHandler;
import com.google.common.io.ByteStreams;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/test")
public class TestController {
    
    @GetMapping("/download")
    public ResponseEntity<byte[]> download() {

        try {
            
            InputStream is = ResourceHandler.getInputStream(ResourceHandler.HTML_TEMPLATES_PATH + "test.docx");
            byte[] contents = ByteStreams.toByteArray(is);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.attachment().filename("yourfile.docx").build());
     
            return new ResponseEntity<>(contents, headers, HttpStatus.OK);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/convert")
    public void convert() throws Exception {

        ResourceHandler.pdfToHtml(ResourceHandler.HTML_TEMPLATES_PATH + "test.pdf");

        String html = ResourceHandler.htmlToString(ResourceHandler.OUTPUT_RESOURCE + "test.html", "Deutsch", "-");

        ResourceHandler.stringToPdf(html, ResourceHandler.OUTPUT_RESOURCE, "test.pdf");
    }
}