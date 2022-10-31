package com.example.myPractice1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.myPractice1.appUser.professor.Professor;
import com.example.myPractice1.appUser.professor.ProfessorService;


@SpringBootTest
@AutoConfigureMockMvc
class MyPractice1ApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ProfessorService professorService;


	@Test
	void contextLoads() throws Exception {
		mockMvc.perform(get("/test/appUser/professor/getByUserName?userName=florin.schikarski@gmail.com"))
			   .andDo(print())
			   .andExpect(status().isOk());
	}


	@Test
	void testService() throws Exception {
		Professor professor = (Professor) professorService.loadUserByUsername("florin.schikarski@gmail.com");
		int salary = 520;
		assertEquals(salary, professor.getSalary());
	}
}