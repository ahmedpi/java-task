package com.epam.javatask.company.service;

import java.net.URISyntaxException;
import java.util.Map;

import com.epam.javatask.company.Employee;
import com.epam.javatask.company.EmployeeDataStore;
import com.epam.javatask.company.exception.NoEmployeeRecordFoundException;

public interface EmployeeService {
	public Map<Employee, Double> getUnderPaidManagers();

	public Map<Employee, Double> getOverPaidManagers();
	
	public EmployeeDataStore analyzeOrganisationStructure() throws URISyntaxException, NoEmployeeRecordFoundException;

}
