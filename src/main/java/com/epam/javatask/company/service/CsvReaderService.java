package com.epam.javatask.company.service;

import java.util.List;

import com.epam.javatask.company.Employee;

public interface CsvReaderService {
	public List<Employee> loadEmployeeData();
}
