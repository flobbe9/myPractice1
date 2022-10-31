package com.example.myPractice1.appUser;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.myPractice1.email.MailService;


@Service
public class AppUserService<T extends AppUser> {
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;
    

    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }


    public void sendMail(T user) {
        // getting path of html email
        InputStream inputStream = getClass().getResourceAsStream("../html/confirmationEmail.html");
        String emailText = mailService.parseHtmlToString(inputStream);

        // sending mail
        new Thread(() -> 
            mailService.send(user.getUsername(), emailText, "myPractice1 | Confirm your Email", null));
    }
}