package com.springboot.rest.controller;

import com.springboot.rest.exceptions.ResourceNotFoundException;
import com.springboot.rest.model.Employee;
import com.springboot.rest.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(value = "/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping(value = "/employees/{id}", produces = "application/json")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") String employeeId)
            throws ResourceNotFoundException {
        try {
            Employee employee = employeeRepository.findEmployeeBy_id(employeeId);
            return ResponseEntity.ok().body(employee);
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/employees", produces = "application/json")
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @PutMapping(value = "/employees/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") String id,
                                                   @Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        try {
            Employee employee = employeeRepository.findEmployeeBy_id(id);
            employeeRepository.delete(employee);
            employee.setEmail(employeeDetails.getEmail());
            employee.setLastname(employeeDetails.getLastname());
            employee.setFirstname(employeeDetails.getFirstname());
            final Employee updatedEmployee = employeeRepository.save(employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        return null;
    }

    @DeleteMapping(value = "/employees/{id}", produces = "application/json")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") String employeeId)
            throws ResourceNotFoundException {
        try {
            Employee employee = employeeRepository.findEmployeeBy_id(employeeId);
            employeeRepository.delete(employee);
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
