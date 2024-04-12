package com.epam.javatask.company;

import java.net.URISyntaxException;

import com.epam.javatask.company.service.EmployeeService;
import com.epam.javatask.company.service.StatisticsService;

public class BigCompany {
	private final StatisticsService statisticsService;
	private final EmployeeService employeeService;

	public BigCompany(StatisticsService statisticsService, EmployeeService employeeService) {
		this.statisticsService = statisticsService;
		this.employeeService = employeeService;
	}

	public EmployeeDataStore analyzeOrganisationStructure() throws URISyntaxException {
		EmployeeDataStore employeeDataStore = employeeService.analyzeOrganisationStructure();
		printEmployeeStatistics(employeeDataStore);
		return employeeDataStore;
	}

	public void printEmployeeStatistics(EmployeeDataStore employeeDataStore) {
		this.statisticsService.printEmployeeStatistics(employeeDataStore);
	}
}
