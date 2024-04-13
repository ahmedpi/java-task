package com.bigcompany.organization;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.util.Map;

import org.junit.Test;

import com.bigcompany.organization.employee.Employee;
import com.bigcompany.organization.employee.EmployeeManager;
import com.bigcompany.organization.employee.EmployeeStatistics;
import com.bigcompany.organization.employee.OrganizationSummary;
import com.bigcompany.organization.employee.ReportingLineManager;
import com.bigcompany.organization.finance.FinanceManager;
import com.bigcompany.organization.service.EmployeeRecordService;
import com.bigcompany.organization.service.EmployeeService;
import com.bigcompany.organization.service.FinanceService;
import com.bigcompany.organization.service.ReportingLineService;
import com.bigcompany.organization.service.StatisticsService;
import com.bigcompany.organization.utility.CsvReader;

public class OrganizationManagerTest {

	@Test
	public void GetUnderpaidManagers_ReturnsList_WhenGivenUnderpaidEmployees() throws URISyntaxException {
		OrganizationManager organizationManager = init("employees.csv");
		OrganizationSummary organizationSummary = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organizationSummary.getUnderPaidManagers();
		assertFalse(actualResult.isEmpty());
	}

	@Test
	public void CheckManagerSalary_ReturnsList_WhenGivenOverpaidEmployees() throws URISyntaxException {
		OrganizationManager organizationManager = init("employees.csv");
		OrganizationSummary organizationSummary = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organizationSummary.getOverPaidManagers();
		assertFalse(actualResult.isEmpty());
	}

	@Test
	public void GetOverpaidManagers_ReturnsEmptyList_WhenGivenRegularEmployees() throws URISyntaxException {
		OrganizationManager organizationManager = init("employees_normal.csv");
		OrganizationSummary organizationSummary = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organizationSummary.getOverPaidManagers();
		assertTrue(actualResult.isEmpty());
	}

	@Test
	public void GetEmployeeWithLongReportingLine_ReturnsEmptyList_WhenNoEmployeeExceedsLimit() throws URISyntaxException {
		OrganizationManager organizationManager = init("employees_normal.csv");
		OrganizationSummary organizationSummary = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Integer> actualResult = organizationSummary.getEmployeesWithLongReportLine();
		assertTrue(actualResult.isEmpty());
	}

	@Test
	public void GetEmployeeWithLongReportingLine_ReturnsList_WhenSomeEmployeeExceedsLimit() throws URISyntaxException {
		OrganizationManager organizationManager = init("employees.csv");
		OrganizationSummary organizationSummary = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Integer> actualResult = organizationSummary.getEmployeesWithLongReportLine();
		assertFalse(actualResult.isEmpty());
		assertTrue(actualResult.keySet().stream().allMatch(emp -> actualResult.get(emp) > 0));
	}

	private OrganizationManager init(String csvFile) {
		EmployeeRecordService csvReaderService = new CsvReader(csvFile);
		FinanceService financeService = new FinanceManager();
		ReportingLineService reportingLineService = new ReportingLineManager();
		StatisticsService statisticsService = new EmployeeStatistics();
		EmployeeService employeeService = new EmployeeManager(csvReaderService, financeService, reportingLineService);

		OrganizationManager organizationManager = new OrganizationManager(statisticsService, employeeService);

		return organizationManager;
	}

}
