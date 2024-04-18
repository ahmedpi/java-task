package com.bigcompany.organization;

import java.net.URISyntaxException;

import com.bigcompany.organization.application.EmployeeRecordReader;
import com.bigcompany.organization.application.OrganizationStructure;
import com.bigcompany.organization.application.ReportingLineManager;
import com.bigcompany.organization.application.SalaryManager;
import com.bigcompany.organization.application.StatisticsManager;
import com.bigcompany.organization.exception.NoEmployeeRecordFoundException;
import com.bigcompany.organization.service.EmployeeRecordService;
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
		reportingLineService.checkEmployeesWithLongReportingLine(organization);
	}

	private void checkManagersSalary() {
		salaryService.checkManagersSalary(organization);
	}

	private void printEmployeeStatistics() {
		this.statisticsService.printEmployeeStatistics(organization);
	}

	public static void main(String[] args) throws NoEmployeeRecordFoundException, URISyntaxException, Exception {
		EmployeeRecordService employeeRecordService = new EmployeeRecordReader("employees.csv");
		SalaryService salaryService = new SalaryManager();
		ReportingLineService reportingLineService = new ReportingLineManager();
		StatisticsService statisticsService = new StatisticsManager();
		Organization organization = new OrganizationStructure(employeeRecordService);
		OrganizationManager organizationManager = new OrganizationManager(organization, salaryService,
				reportingLineService, statisticsService);

		organization = organizationManager.analyzeOrganizationStructure();
	}
}
