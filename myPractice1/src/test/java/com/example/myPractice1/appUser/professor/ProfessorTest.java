package com.example.myPractice1.appUser.professor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


@WebMvcTest(ProfessorService.class)
public class ProfessorTest {
    
    @Autowired
    private ProfessorService professorService;
    

    // @Test
    void testTest() {
        assertEquals("noooice", professorService.test2());
    }
}