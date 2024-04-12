package com.epam.javatask.company.service;

import java.util.Map;

import com.epam.javatask.company.Employee;
import com.epam.javatask.company.Report;

public interface EmployeeService {
	public Map<Employee, Double> getUnderPaidManagers();

	public Map<Employee, Double> getOverPaidManagers();
	
	public Report analyzeOrganisationStructure();

}
