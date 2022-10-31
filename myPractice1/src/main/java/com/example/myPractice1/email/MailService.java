package com.example.myPractice1.email;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;


    /**
     * Sending an email via google smtp with sender adress 'schikarski98@gmail.com'. Email content should be written in html.
     * 
     * @param targetAdress email adress of reciever.
     * @param email actual content of the email.
     * @param subject of the email.
     * @param attachments of the email. May be null if no attachments are sent.
     * @throws IlleagalStateException if MimeMessageHelper throws a MessagingException.
     */
    @Async
    public void send(String targetAdress, String email, String subject, Set<File> attachments) {
        // creating mimeMessage for javaMailSender
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            // targetAdress
            mimeMessageHelper.setTo(targetAdress);

            // senderAdress
            mimeMessageHelper.setFrom("schikarski98@gmail.com");

            // subject
            mimeMessageHelper.setSubject(subject);

            // attachments
            if (attachments != null) {
                attachments.stream().forEach(attachment -> {
                    try {
                        mimeMessageHelper.addAttachment(attachment.getName(), attachment);
                    } catch (MessagingException e) {
                        throw new IllegalStateException(e.getMessage());
                    }
                });
            }

            // email content
            mimeMessageHelper.setText(email, true);

            // sending mail
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }


    /**
     * Parses any html file into a string. 
     * 
     * @param inputStream InputStream object with path of the html file in the parameter.
     * @throws IllegalStateException if a file is not found
     * @return a string with html text.
     */
    public String parseHtmlToString(InputStream inputStream) {
        // using input streams to read html file
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            // collecting stream to string
            return bufferedReader.lines().collect(Collectors.joining());

        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        } catch (NullPointerException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}