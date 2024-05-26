package com.example.client.employee;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static java.time.Month.*;

@Configuration
public class EmployeeConfig {

    @Bean
    CommandLineRunner commandLineRunner(EmployeeRepository employeeRepository) {
        return args -> {
            addEmployeeIfNotPresent(employeeRepository,
                    new Employee("Gokhan Eren", "gokhane@mail.com", LocalDate.of(1982, APRIL, 15)));
            addEmployeeIfNotPresent(employeeRepository,
                    new Employee("Mustafa Sevinç", "mustafas@mail.com", LocalDate.of(2003, JANUARY, 1)));
            addEmployeeIfNotPresent(employeeRepository,
                    new Employee("Berke Özkır", "berkeo@mail.com", LocalDate.of(2003, FEBRUARY, 1)));
        };
    }

    private void addEmployeeIfNotPresent(EmployeeRepository repository, Employee employee) {
        Optional<Employee> existingEmployee = repository.findEmployeeByEmail(employee.getEmail());
        if (!existingEmployee.isPresent()) {
            repository.save(employee);
        }
    }
}
