package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {
    @Bean

    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
            Student Prisha =new Student(
                    1L,
            "Prisha",
            "prishachopra6@gmail.com",
                    LocalDate.of(2003, Month.SEPTEMBER,26),
                    90
            );
            Student Mannat =new Student(
                    "Mannat",
                    "Mannatjain6@gmail.com",
                    LocalDate.of(2003, Month.SEPTEMBER,26),
                    80
            );
            repository.saveAll(
                    List.of(Prisha,Mannat)
            );
        };
    }
}
