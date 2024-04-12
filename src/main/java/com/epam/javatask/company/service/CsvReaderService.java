package com.epam.javatask.company.service;

import java.net.URISyntaxException;
import java.util.List;

import com.epam.javatask.company.employee.Employee;
import com.epam.javatask.company.exception.NoEmployeeRecordFoundException;

public interface CsvReaderService {
	public List<Employee> loadEmployeeData() throws URISyntaxException, NoEmployeeRecordFoundException;
}
