package com.epam.javatask.company;

import com.epam.javatask.company.service.EmployeeService;
import com.epam.javatask.company.service.StatisticsService;

public class BigCompany {
	private final StatisticsService statisticsService;
	private final EmployeeService employeeService;

	public BigCompany(StatisticsService statisticsService, EmployeeService employeeService) {
		super();
		this.statisticsService = statisticsService;
		this.employeeService = employeeService;
	}

	public Report analyzeOrganisationStructure() {
		Report report = employeeService.analyzeOrganisationStructure();
		printEmployeeStatistics(report);
		return report;
	}

	public void printEmployeeStatistics(Report report) {
//		this.statisticsService.printSalaryInfo();
//		this.statisticsService.printReportLineInfo();
		this.statisticsService.printEmployeeStatistics(report);
	}
}
