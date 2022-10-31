package com.example.myPractice1.appUser.professor;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.myPractice1.appUser.AppUser;
import com.example.myPractice1.appUser.AppUserRole;
import com.example.myPractice1.appUser.Employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Professor extends AppUser implements Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer salary;

    
    public Professor(String userName,
                     String password, 
                     String firstName, 
                     String lastName, 
                     LocalDate birthday,
                     AppUserRole role, 
                     Integer salary) {
        super(userName, password, firstName, lastName, birthday, role);
        this.salary = salary;
    }


    /**
     * Serves as a wrapper to make sure the right fields are added to the Professor instance.
     * 
     * @param professor to instantiate.
     */
    public Professor(Professor professor) {
        super(professor.getUsername(), 
              professor.getPassword(), 
              professor.getFirstName(), 
              professor.getLastName(), 
              professor.getBirthday(), 
              professor.getRole());
        this.salary = professor.getSalary();
    }
}