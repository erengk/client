package com.example.client.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "employee", produces = "application/json; charset=UTF-8")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping(path = "{employeeId}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Optional<Employee>> getEmployeeById(@PathVariable("employeeId") Long employeeId) {
        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
        if (employee.isPresent()) {
            return ResponseEntity.ok().body(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public void addNewEmployee(@RequestBody Employee employee) {
        employeeService.addNewEmployee(employee);
    }

    @DeleteMapping(path = "{employeeId}", produces = "application/json; charset=UTF-8")
    public void deleteEmployee(@PathVariable("employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }

    @PutMapping(path = "{employeeId}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> updateEmployee(
            @PathVariable("employeeId") Long employeeId,
            @RequestBody EmployeeUpdateRequest request) {
        try {
            employeeService.updateEmployee(employeeId, request.getName(), request.getEmail(), request.getDob());
            return ResponseEntity.ok("Employee updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while updating the employee: " + e.getMessage());
        }
    }
}

class EmployeeUpdateRequest {
    private String name;
    private String email;
    private String dob;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
