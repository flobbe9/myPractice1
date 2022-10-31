package com.example.myPractice1.appUser.professor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// import org.springframework.batch.core.BatchStatus;
// import org.springframework.batch.core.Job;
// import org.springframework.batch.core.JobExecution;
// import org.springframework.batch.core.JobParameter;
// import org.springframework.batch.core.JobParameters;
// import org.springframework.batch.core.JobParametersInvalidException;
// import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
// import org.springframework.batch.core.launch.JobLauncher;
// import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
// import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
// import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;


@RestController
@RequestMapping("/test/appUser/professor")
// @EnableBatchProcessing
@Getter
@Setter
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;


//// user related

    @PostMapping("/addNew")
    public Professor addNew(@RequestBody Professor professor) {
        return professorService.addNew(professor);
    }


    @GetMapping("/getByUserName") 
    public Professor getByUserName(@RequestParam("userName") String userName) {
        return (Professor) professorService.loadUserByUsername(userName);
    }


    @GetMapping("/testResource")
    public String testReource() throws IOException {
        return professorService.test();
    }
}