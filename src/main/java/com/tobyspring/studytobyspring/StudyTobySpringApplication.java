package com.tobyspring.studytobyspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class StudyTobySpringApplication {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        SpringApplication.run(StudyTobySpringApplication.class, args);
    }

}
