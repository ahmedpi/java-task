package com.bigcompany.organization.service;

import java.net.URISyntaxException;
import java.util.List;

import com.bigcompany.organization.employee.Employee;
import com.bigcompany.organization.exception.NoEmployeeRecordFoundException;

public interface CsvReaderService {
	public List<Employee> loadEmployeeData() throws URISyntaxException, NoEmployeeRecordFoundException;
}
