package com.bigcompany.organization;

import java.net.URISyntaxException;

import com.bigcompany.organization.service.Organization;
import com.bigcompany.organization.service.ReportingLineService;
import com.bigcompany.organization.service.SalaryService;
import com.bigcompany.organization.service.StatisticsService;

public class OrganizationManager {

	private final SalaryService salaryService;
	private final ReportingLineService reportingLineService;
	private final StatisticsService statisticsService;

	private final Organization organization;

	public OrganizationManager(Organization organization, SalaryService salaryService,
			ReportingLineService reportingLineService, StatisticsService statisticsService) {
		this.organization = organization;
		this.salaryService = salaryService;
		this.reportingLineService = reportingLineService;
		this.statisticsService = statisticsService;
	}

	public Organization analyzeOrganizationStructure() throws URISyntaxException {
		checkManagersSalary();
		checkEmployeesReportingLine();
		printEmployeeStatistics();
		return organization;
	}

	private void checkEmployeesReportingLine() {
		reportingLineService.checkEmployeesWithLongReportingLine();
	}

	private void checkManagersSalary() {
		salaryService.checkManagersSalary(organization);
	}

	private void printEmployeeStatistics() {
		this.statisticsService.printEmployeeStatistics(organization);
	}
}
