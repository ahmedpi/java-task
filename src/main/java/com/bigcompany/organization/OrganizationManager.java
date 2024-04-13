package com.bigcompany.organization;

import java.net.URISyntaxException;

import com.bigcompany.organization.employee.OrganizationSummary;
import com.bigcompany.organization.service.EmployeeService;
import com.bigcompany.organization.service.StatisticsService;

public class OrganizationManager {
	private final StatisticsService statisticsService;
	private final EmployeeService employeeService;

	public OrganizationManager(StatisticsService statisticsService, EmployeeService employeeService) {
		this.statisticsService = statisticsService;
		this.employeeService = employeeService;
	}

	public OrganizationSummary analyzeOrganizationStructure() throws URISyntaxException {
		OrganizationSummary organizationSummary = employeeService.analyzeOrganizationStructure();
		printEmployeeStatistics(organizationSummary);
		return organizationSummary;
	}

	public void printEmployeeStatistics(OrganizationSummary organizationSummary) {
		this.statisticsService.printEmployeeStatistics(organizationSummary);
	}
}
