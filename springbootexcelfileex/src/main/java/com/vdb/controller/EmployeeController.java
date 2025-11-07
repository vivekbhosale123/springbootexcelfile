package com.vdb.controller;

import com.vdb.model.Employee;
import com.vdb.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> saveExcelFile(@RequestParam("file") MultipartFile file) throws IOException {
        employeeService.saveExcelFileData(file);
        return ResponseEntity.ok("File Added Successfully");
    }

    @GetMapping("/findbyid/{empId}")
    public ResponseEntity<Optional<Employee>> findById(@PathVariable int empId) {
        return ResponseEntity.ok(employeeService.findById(empId));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/sortbyname")
    public ResponseEntity<List<Employee>> sortByName() {
        return ResponseEntity.ok(employeeService.findAll().stream().sorted(Comparator.comparing(Employee::getEmpName)).toList());
    }

    @GetMapping("/searchbyname")
    public ResponseEntity<List<Employee>> findByName(@RequestParam(defaultValue = "vivek") String empName) {
        return ResponseEntity.ok(employeeService.findAll().stream().filter(emp->emp.getEmpName().equals(empName)).toList());
    }

    @DeleteMapping("/deletebyid")
    public ResponseEntity<String> deleteById(@RequestParam int empId) {
        employeeService.deleteById(empId);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<String> deleteAll() {
        employeeService.deleteAll();
        return ResponseEntity.ok("All Employee Deleted Successfully");
    }
}
