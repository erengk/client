package com.example.client.employee;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.util.List;
import static java.time.Month.*;

@Configuration
public class EmployeeConfig {

    @Bean
    CommandLineRunner commandLineRunner(EmployeeRepository employeeRepository) {
        return args -> {
           Employee gokhaneren = new Employee(
                    "Gokhan Eren",
                    "gokhane@mail.com",
                    LocalDate.of(1982, APRIL, 15)
            );

            Employee mustafasevinc = new Employee(
                    "Mustafa Sevinç",
                    "mustafas@mail.com",
                    LocalDate.of(2003, JANUARY, 1)
            );

            Employee berkeozkir = new Employee(
                    "Berke Özkır",
                    "berkeo@mail.com",
                    LocalDate.of(2003, FEBRUARY, 1)
            );

            employeeRepository.saveAll(
                    List.of(gokhaneren, mustafasevinc, berkeozkir)
            );
        };
    }
}
