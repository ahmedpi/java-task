package com.epam.javatask.company.service;

import java.net.URISyntaxException;

import com.epam.javatask.company.employee.EmployeeDataStore;
import com.epam.javatask.company.exception.NoEmployeeRecordFoundException;

public interface EmployeeService {

	public EmployeeDataStore analyzeOrganisationStructure() throws URISyntaxException, NoEmployeeRecordFoundException;

}
