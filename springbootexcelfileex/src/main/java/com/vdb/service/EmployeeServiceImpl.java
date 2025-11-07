package com.vdb.service;

import com.vdb.model.Employee;
import com.vdb.repository.EmployeeRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void saveExcelFileData(MultipartFile file) throws IOException {
        List<Employee> employeeList = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // skip header

                Employee employee = new Employee();
                employee.setEmpName(row.getCell(0).getStringCellValue());
                employee.setEmpSalary(row.getCell(1).getNumericCellValue());
                employee.setEmpAddress(row.getCell(2).getStringCellValue());
                employeeList.add(employee);
            }

            employeeRepository.saveAll(employeeList);

        } catch (Exception e) {
            throw new IOException("Invalid Excel file. Please upload a valid .xls or .xlsx file.", e);
        }
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findById(int empId) {
        return employeeRepository.findById(empId);
    }

    @Override
    public void deleteById(int empId) {
        employeeRepository.deleteById(empId);
    }

    @Override
    public void deleteAll() {
        employeeRepository.deleteAll();
    }
}
