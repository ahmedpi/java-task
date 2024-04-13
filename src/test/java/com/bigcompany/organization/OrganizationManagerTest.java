package com.bigcompany.organization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.util.Map;

import org.junit.Test;

import com.bigcompany.organization.application.Employee;
import com.bigcompany.organization.application.EmployeeRecordReader;
import com.bigcompany.organization.application.FinanceManager;
import com.bigcompany.organization.application.OrganizationStructure;
import com.bigcompany.organization.application.ReportingLineManager;
import com.bigcompany.organization.application.StatisticsManager;
import com.bigcompany.organization.exception.DuplicateEmployeeRecordFoundException;
import com.bigcompany.organization.exception.NoEmployeeRecordFoundException;
import com.bigcompany.organization.service.EmployeeRecordService;
import com.bigcompany.organization.service.FinanceService;
import com.bigcompany.organization.service.Organization;
import com.bigcompany.organization.service.ReportingLineService;
import com.bigcompany.organization.service.StatisticsService;

public class OrganizationManagerTest {

	@Test
	public void GetUnderpaidManagers_ReturnsList_WhenGivenUnderpaidEmployees()
			throws URISyntaxException, NoEmployeeRecordFoundException, Exception {
		OrganizationManager organizationManager = init("employees.csv");
		Organization organization = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organization.getUnderPaidManagers();

		assertFalse(actualResult.isEmpty());
		assertTrue(actualResult.keySet().stream().allMatch(emp -> actualResult.get(emp) > 0));
	}

	@Test
	public void GetUnderpaidManagers_ReturnsListWithUnderPaidAmount_WhenGivenUnderpaidEmployees()
			throws URISyntaxException, NoEmployeeRecordFoundException, Exception {
		OrganizationManager organizationManager = init("employees.csv");
		Organization organization = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organization.getUnderPaidManagers();

		assertTrue(actualResult.keySet().stream().allMatch(emp -> actualResult.get(emp) > 0));
	}

	@Test
	public void CheckManagerSalary_ReturnsList_WhenGivenOverpaidEmployees()
			throws URISyntaxException, NoEmployeeRecordFoundException, Exception {
		OrganizationManager organizationManager = init("employees.csv");
		Organization organization = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organization.getUnderPaidManagers();
		assertFalse(actualResult.isEmpty());
	}

	@Test
	public void GetOverpaidManagers_ReturnsEmptyList_WhenGivenRegularEmployees()
			throws URISyntaxException, NoEmployeeRecordFoundException, Exception {
		OrganizationManager organizationManager = init("employees_normal.csv");
		Organization organization = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organization.getUnderPaidManagers();
		assertTrue(actualResult.isEmpty());
	}

	@Test
	public void GetEmployeeWithLongReportingLine_ReturnsEmptyList_WhenNoEmployeeExceedsLimit()
			throws URISyntaxException, NoEmployeeRecordFoundException, Exception {
		OrganizationManager organizationManager = init("employees_normal.csv");
		Organization organization = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organization.getUnderPaidManagers();
		assertTrue(actualResult.isEmpty());
	}

	@Test
	public void GetEmployeeWithLongReportingLine_ReturnsList_WhenSomeEmployeeExceedsLimit()
			throws URISyntaxException, NoEmployeeRecordFoundException, Exception {
		OrganizationManager organizationManager = init("employees.csv");
		Organization organization = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organization.getUnderPaidManagers();

		assertFalse(actualResult.isEmpty());
		assertTrue(actualResult.keySet().stream().allMatch(emp -> actualResult.get(emp) > 0));
	}

	@Test
	public void InitData_ThrowsException_WhenGivenDuplicateEmployeeRecord()
			throws URISyntaxException, NoEmployeeRecordFoundException, Exception {

		Exception thrown = assertThrows(DuplicateEmployeeRecordFoundException.class,
				() -> init("employees_duplicated.csv"));

		assertEquals("Error: Duplicate Employee Record Found", thrown.getMessage());
	}

	@Test
	public void GetUnderpaidManagers_ReturnsList_WhenGivenUnderpaidEmployees2()
			throws URISyntaxException, NoEmployeeRecordFoundException, Exception {

		Exception thrown = assertThrows(NoEmployeeRecordFoundException.class, () -> init("employees_empty.csv"));

		assertEquals("Error: No Employee Record Found", thrown.getMessage());
	}

	private OrganizationManager init(String csvFile)
			throws NoEmployeeRecordFoundException, URISyntaxException, Exception {
		EmployeeRecordService employeeRecordService = new EmployeeRecordReader(csvFile);
		Organization organization = new OrganizationStructure(employeeRecordService);
		FinanceService financeService = new FinanceManager();
		ReportingLineService reportingLineService = new ReportingLineManager();
		StatisticsService statisticsService = new StatisticsManager();

		OrganizationManager organizationManager = new OrganizationManager(organization, financeService,
				reportingLineService, statisticsService);

		return organizationManager;
	}

}
