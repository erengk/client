package com.example.client.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();

    }

    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }


    @Transactional
    public void addNewEmployee(Employee employee) {
        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmail(employee.getEmail());
        if(employeeOptional.isPresent()){
            throw new IllegalStateException("Employee already exists");
        }
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long employeeId) {
        boolean exists = employeeRepository.existsById(employeeId);
        if (!exists){
            throw new IllegalStateException("Employee does not exist: " + employeeId);
        }
        employeeRepository.deleteById(employeeId);
    }

    @Transactional
    public void updateEmployee(Long employeeId, String name, String email, String dob) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalStateException("Employee not found: " + employeeId));

        // İsmi güncelle
        if (name != null && !name.trim().isEmpty() && !Objects.equals(employee.getName(), name)) {
            employee.setName(name);
        }

        // Email'i güncelle
        if (email != null && !email.trim().isEmpty() && !Objects.equals(employee.getEmail(), email)) {
            Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmail(email);
            if (employeeOptional.isPresent()) {
                throw new IllegalStateException("Employee already exists with email: " + email);
            }
            employee.setEmail(email);
        }

        // Doğum tarihini güncelle
        if (dob != null && !dob.trim().isEmpty()) {
            try {
                LocalDate parsedDob = LocalDate.parse(dob);
                if (!Objects.equals(employee.getDob(), parsedDob)) {
                    employee.setDob(parsedDob);
                }
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date format for dob: " + dob);
            }
        }
        // Güncellenmiş çalışanı kaydet
        employeeRepository.save(employee);
    }
}
