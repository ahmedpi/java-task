package com.bigcompany.organization;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import com.bigcompany.organization.application.EmployeeRecordReader;
import com.bigcompany.organization.application.ReportingLineManager;
import com.bigcompany.organization.application.SalaryManager;
import com.bigcompany.organization.application.StatisticsManager;
import com.bigcompany.organization.dto.Organization;
import com.bigcompany.organization.exception.NoEmployeeRecordFoundException;
import com.bigcompany.organization.model.Employee;
import com.bigcompany.organization.service.EmployeeRecordService;
import com.bigcompany.organization.service.ReportingLineService;
import com.bigcompany.organization.service.SalaryService;
import com.bigcompany.organization.service.StatisticsService;

public class OrganizationManager {

	private final SalaryService salaryService;
	private final ReportingLineService reportingLineService;
	private final StatisticsService statisticsService;
	private final EmployeeRecordService employeeRecordService;

	public OrganizationManager(EmployeeRecordService employeeRecordService, SalaryService salaryService,
			ReportingLineService reportingLineService, StatisticsService statisticsService) {
		this.employeeRecordService = employeeRecordService;
		this.salaryService = salaryService;
		this.reportingLineService = reportingLineService;
		this.statisticsService = statisticsService;
	}

	public Organization analyzeOrganizationStructure() throws Exception {
		List<Employee> employees = employeeRecordService.processEmployeeData();
		Map<Employee, Double> underPaidEmployees = salaryService.getUnderPaidManagers(employees);
		Map<Employee, Double> overPaidEmployees = salaryService.getOverPaidManagers(employees);
		Map<Employee, Integer> employeeWithReportingLine = reportingLineService
				.getEmployeesWithLongReportingLine(employees);

		Organization organization = new Organization(employees, employeeWithReportingLine, underPaidEmployees,
				overPaidEmployees);

		this.statisticsService.printEmployeeStatistics(organization);

		return organization;

	}

	public static void main(String[] args) throws NoEmployeeRecordFoundException, URISyntaxException, Exception {
		// Reading file using scanner
		if (args.length == 0) {
			System.out.println("Error: Missing file path");
			return;
		}

		EmployeeRecordService employeeRecordService = new EmployeeRecordReader(args[0]);
		SalaryService salaryService = new SalaryManager();
		ReportingLineService reportingLineService = new ReportingLineManager();
		StatisticsService statisticsService = new StatisticsManager();
		OrganizationManager organizationManager = new OrganizationManager(employeeRecordService, salaryService,
				reportingLineService, statisticsService);

		Organization organization = organizationManager.analyzeOrganizationStructure();
	}
}
