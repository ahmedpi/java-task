package com.bigcompany.organization;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.util.Map;

import org.junit.Test;

import com.bigcompany.organization.employee.Employee;
import com.bigcompany.organization.employee.EmployeeRecordReader;
import com.bigcompany.organization.employee.EmployeeStatistics;
import com.bigcompany.organization.employee.OrganizationStructure;
import com.bigcompany.organization.employee.ReportingLineManager;
import com.bigcompany.organization.exception.NoEmployeeRecordFoundException;
import com.bigcompany.organization.finance.FinanceManager;
import com.bigcompany.organization.service.EmployeeRecordService;
import com.bigcompany.organization.service.FinanceService;
import com.bigcompany.organization.service.Organization;
import com.bigcompany.organization.service.ReportingLineService;
import com.bigcompany.organization.service.StatisticsService;

public class OrganizationManagerTest {

	@Test
	public void GetUnderpaidManagers_ReturnsList_WhenGivenUnderpaidEmployees() throws URISyntaxException {
		OrganizationManager organizationManager = init("employees.csv");
		Organization organization = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organization.getUnderPaidManagers();

		assertFalse(actualResult.isEmpty());
		assertTrue(actualResult.keySet().stream().allMatch(emp -> actualResult.get(emp) > 0));
	}

	@Test
	public void GetUnderpaidManagers_ReturnsListWithUnderPaidAmount_WhenGivenUnderpaidEmployees()
			throws URISyntaxException {
		OrganizationManager organizationManager = init("employees.csv");
		Organization organization = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organization.getUnderPaidManagers();

		assertTrue(actualResult.keySet().stream().allMatch(emp -> actualResult.get(emp) > 0));
	}

	@Test
	public void CheckManagerSalary_ReturnsList_WhenGivenOverpaidEmployees() throws URISyntaxException {
		OrganizationManager organizationManager = init("employees.csv");
		Organization organization = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organization.getUnderPaidManagers();
		assertFalse(actualResult.isEmpty());
	}

	@Test
	public void GetOverpaidManagers_ReturnsEmptyList_WhenGivenRegularEmployees() throws URISyntaxException {
		OrganizationManager organizationManager = init("employees_normal.csv");
		Organization organization = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organization.getUnderPaidManagers();
		assertTrue(actualResult.isEmpty());
	}

	@Test
	public void GetEmployeeWithLongReportingLine_ReturnsEmptyList_WhenNoEmployeeExceedsLimit()
			throws URISyntaxException {
		OrganizationManager organizationManager = init("employees_normal.csv");
		Organization organization = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organization.getUnderPaidManagers();
		assertTrue(actualResult.isEmpty());
	}

	@Test
	public void GetEmployeeWithLongReportingLine_ReturnsList_WhenSomeEmployeeExceedsLimit() throws URISyntaxException {
		OrganizationManager organizationManager = init("employees.csv");
		Organization organization = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organization.getUnderPaidManagers();

		assertFalse(actualResult.isEmpty());
		assertTrue(actualResult.keySet().stream().allMatch(emp -> actualResult.get(emp) > 0));
	}

	private OrganizationManager init(String csvFile) throws NoEmployeeRecordFoundException, URISyntaxException {
		EmployeeRecordService employeeRecordService = new EmployeeRecordReader(csvFile);
		Organization organization = new OrganizationStructure(employeeRecordService);
		FinanceService financeService = new FinanceManager();
		ReportingLineService reportingLineService = new ReportingLineManager();
		StatisticsService statisticsService = new EmployeeStatistics();

		OrganizationManager organizationManager = new OrganizationManager(organization, financeService,
				reportingLineService, statisticsService);

		return organizationManager;
	}

}
