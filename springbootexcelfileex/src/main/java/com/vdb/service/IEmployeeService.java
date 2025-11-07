package com.vdb.service;

import com.vdb.model.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    void saveExcelFileData(MultipartFile file) throws IOException;
    List<Employee> findAll();
    Optional<Employee> findById(int empId);
    void deleteById(int empId);
    void deleteAll();
}
