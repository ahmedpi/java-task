package com.bigcompany.organization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.util.Map;

import org.junit.Test;

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

public class OrganizationManagerTest {

	@Test
	public void GetUnderpaidManagers_ReturnsList_WhenGivenUnderpaidEmployees()
			throws URISyntaxException, NoEmployeeRecordFoundException, Exception {
		OrganizationManager organizationManager = init("src/test/resources/employees.csv");
		Organization organization = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organization.getUnderPaidManagers();

		assertFalse(actualResult.isEmpty());
		assertTrue(actualResult.keySet().stream().allMatch(emp -> actualResult.get(emp) > 0));
	}

	@Test
	public void GetUnderpaidManagers_ReturnsListWithUnderPaidAmount_WhenGivenUnderpaidEmployees()
			throws URISyntaxException, NoEmployeeRecordFoundException, Exception {
		OrganizationManager organizationManager = init("src/test/resources/employees.csv");
		Organization organization = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organization.getUnderPaidManagers();

		assertTrue(actualResult.keySet().stream().allMatch(emp -> actualResult.get(emp) > 0));
	}

	@Test
	public void CheckManagerSalary_ReturnsList_WhenGivenOverpaidEmployees()
			throws URISyntaxException, NoEmployeeRecordFoundException, Exception {
		OrganizationManager organizationManager = init("src/test/resources/employees.csv");
		Organization organization = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organization.getUnderPaidManagers();
		assertFalse(actualResult.isEmpty());
	}

	@Test
	public void GetOverpaidManagers_ReturnsEmptyList_WhenGivenRegularEmployees()
			throws URISyntaxException, NoEmployeeRecordFoundException, Exception {
		OrganizationManager organizationManager = init("src/test/resources/employees_normal.csv");
		Organization organization = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organization.getUnderPaidManagers();
		assertTrue(actualResult.isEmpty());
	}

	@Test
	public void GetEmployeeWithLongReportingLine_ReturnsEmptyList_WhenNoEmployeeExceedsLimit()
			throws URISyntaxException, NoEmployeeRecordFoundException, Exception {
		OrganizationManager organizationManager = init("src/test/resources/employees_normal.csv");
		Organization organization = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organization.getUnderPaidManagers();
		assertTrue(actualResult.isEmpty());
	}

	@Test
	public void GetEmployeeWithLongReportingLine_ReturnsList_WhenSomeEmployeeExceedsLimit()
			throws URISyntaxException, NoEmployeeRecordFoundException, Exception {
		OrganizationManager organizationManager = init("src/test/resources/employees.csv");
		Organization organization = organizationManager.analyzeOrganizationStructure();
		Map<Employee, Double> actualResult = organization.getUnderPaidManagers();

		assertFalse(actualResult.isEmpty());
		assertTrue(actualResult.keySet().stream().allMatch(emp -> actualResult.get(emp) > 0));
	}

	@Test
	public void InitData_ThrowsException_WhenGivenDuplicateEmployeeRecord()
			throws URISyntaxException, NoEmployeeRecordFoundException, Exception {
		OrganizationManager organizationManager = init("src/test/resources/employees_duplicated.csv");

		Exception thrown = assertThrows(Exception.class, () -> organizationManager.analyzeOrganizationStructure());

		assertEquals("Error: Duplicate Employee Record Found", thrown.getMessage());
	}

	@Test
	public void InitData_ThrowsException_WhenGivenEmptyRecord()
			throws URISyntaxException, NoEmployeeRecordFoundException, Exception {
		OrganizationManager organizationManager = init("src/test/resources/employees_empty.csv");

		Exception thrown = assertThrows(Exception.class, () -> organizationManager.analyzeOrganizationStructure());

		assertEquals("Error: No Employee Record Found", thrown.getMessage());
	}

	private OrganizationManager init(String csvFile)
			throws NoEmployeeRecordFoundException, URISyntaxException, Exception {

		EmployeeRecordService employeeRecordService = new EmployeeRecordReader(csvFile);
		SalaryService salaryService = new SalaryManager();
		ReportingLineService reportingLineService = new ReportingLineManager();
		StatisticsService statisticsService = new StatisticsManager();
		OrganizationManager organizationManager = new OrganizationManager(employeeRecordService, salaryService,
				reportingLineService, statisticsService);
		return organizationManager;
	}

}
