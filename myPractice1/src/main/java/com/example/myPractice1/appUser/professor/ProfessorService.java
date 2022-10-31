package com.example.myPractice1.appUser.professor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.myPractice1.appUser.AppUserService;


@Service
public class ProfessorService extends AppUserService<Professor> implements UserDetailsService, ItemWriter<Professor> {
    
    @Autowired
    private ProfessorRepository professorRepository;
    
    @Value("${testVar}")
    private String resourcePath;


    /**
     * Saving Professor entity to db. 
     * 
     * <p>Encoding password and sending confirmation email...
     * 
     * @param professorTemplate Professor instance to be added.
     * @return saved Professor.
     */
    public Professor addNew(Professor professorTemplate) {
        // calling constructor
        Professor professor = new Professor(professorTemplate);

        // encoding password
        professor.setPassword(encryptPassword(professor.getPassword()));

        // TODO: delete this line later
        professor.setEnabled(true);

        // sending mail
        sendMail(professor);

        return professorRepository.save(professor);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // return professorRepository.findByUserName(username).orElseThrow(() -> 
        //     new IllegalStateException("Could not find '" + username + "'."));
        return null;
    }


//// helper


    public String test() throws IOException {

        try (InputStream is = getClass().getResourceAsStream(resourcePath);
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr)) {

            return br.readLine();
        }
    }


    public String test2() {
        return "noooice";
    }


    @Override
    public void write(List<? extends Professor> items) throws Exception {
        // TODO Auto-generated method stub
        
    }
}