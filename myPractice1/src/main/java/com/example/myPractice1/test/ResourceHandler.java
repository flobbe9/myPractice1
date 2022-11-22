package com.example.myPractice1.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

import java.io.PrintWriter;
import java.io.Writer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.fit.pdfdom.PDFDomTree;


public class ResourceHandler {

    /** Html files with the email message. */
    public static final String HTML_TEMPLATES_PATH = "./mail/templates/";

    /** Html files with the email attachment content. */
    public static final String HTML_ATTACHMENTS_PATH = "./mail/attachments/";
    
    /** Pdf files created as email attachment. */
    public static final String PDF_ATTACHMENTS_PATH = "./outputResources/attachments/";

    /** Any outputresource. */
    public static final String OUTPUT_RESOURCE = "./outputResources/";
    
    
    /**
     * Converts any html file into a String. 
     * 
     * @param htmlPath path to the html file relative to ResourceHandler.class.
     * @param fillers Objects to replace format specifiers.
     * @return String with the html content.
     * @throws IOException if the html file is not found.
     * @throws MissingArgumentException if 'fillers' has not enough arguments or the wrong argument types.
     */
    public static String htmlToString(String htmlPath, Object... fillers) {

        try (InputStreamReader isr = new InputStreamReader(getInputStream(htmlPath));
             BufferedReader br = new BufferedReader(isr)) {
            
            // html file as String
            String htmlString = br.lines().collect(Collectors.joining());

            // formating string
            htmlString = String.format(htmlString, fillers);

            return htmlString;

        } catch (NullPointerException e) {
            throw new IllegalStateException("Could not find html file at path " + htmlPath + ".");

        } catch (IOException e) {
            throw new IllegalStateException("Could not find html file at path " + htmlPath + ".");
        }
    }


    /**
     * Converts any String to pdf and saves it inside the specified directory. If it does not exist, it will be created.
     * 
     * @param str String to convert.
     * @param pdfTargetDir target directory to store the pdf file in.
     * @param pdfName file name that the pdf should have including the prefix.
     * @throws IOException if some path was not found.
     * @throws IllegalStateException if target directory could not be created.
     * @throws DocumentException
     */
    public static void stringToPdf(String str, String pdfTargetDir, String pdfName) throws IOException, DocumentException {

        // creating targetDir 
        createOutputDir(pdfTargetDir);

        try (OutputStream outputStream = new FileOutputStream(pdfTargetDir + pdfName)) {
            
            Document document = Jsoup.parse(str);
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(document.html());
            renderer.layout();
            renderer.createPDF(outputStream);

            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);

        } catch (NullPointerException e) {
            throw new IllegalStateException("File not found.");
        }
    }


    public static void pdfToHtml(String path) throws Exception {

        try (PDDocument pdf = PDDocument.load(getInputStream(path));
             Writer output = new PrintWriter(OUTPUT_RESOURCE + "test.html", "utf-8")) {
            
            new PDFDomTree().writeText(pdf, output);
        }
    }


/////// helper methods:


    /**
     * Takes any file path relative to this class and returns an InputStream of it.
     * 
     * @param relativePath path relative to ResourceHandler.class.
     * @return InputStream with data of the file.
     * @throws IllegalException if the file was not found.
     */
    public static InputStream getInputStream(String relativePath) {

        try {
            return ResourceHandler.class.getResourceAsStream(relativePath);

        } catch (NullPointerException e) {
            throw new IllegalStateException("Could not find the resource at path " + relativePath + ".");
        }
    }


    /**
     * Creates any directory or directories at the specified path if they don't already exist.
     * 
     * @param dir path of the directory to create relative to the execution folder (where some command like
     *            'gradle bootRun' is run).
     * @return true if dir was created successfully or already existed.
     * @throws IllegalStateException if target directory could not be created.
     */
    private static boolean createOutputDir(String dir) {

        File targetDir = new File(dir);

        if (!targetDir.exists() && !targetDir.mkdirs())
            throw new IllegalStateException("Failed to create output directory at path " + targetDir + ".");

        return true;
    }
}