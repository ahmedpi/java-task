package com.epam.javatask.company;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.epam.javatask.company.service.CsvReaderService;
import com.epam.javatask.company.service.EmployeeService;
import com.epam.javatask.company.service.FinanceService;
import com.epam.javatask.company.service.ReportLineService;
import com.epam.javatask.company.service.StatisticsService;
import com.epam.javatask.company.utility.CsvReader;

public class BigCompanyTest {

	@Test
	public void GetUnderpaidManagers_ReturnsList_WhenGivenUnderpaidEmployees() throws URISyntaxException {
		BigCompany bigCompany = init("employees.csv");
		EmployeeDataStore employeeDataStore = bigCompany.analyzeOrganisationStructure();
		Map<Employee, Double> actualResult = employeeDataStore.getUnderPaidManagers();
		assertFalse(actualResult.isEmpty());
	}

	@Test
	public void CheckManagerSalary_ReturnsList_WhenGivenOverpaidEmployees() throws URISyntaxException {
		BigCompany bigCompany = init("employees.csv");
		EmployeeDataStore employeeDataStore = bigCompany.analyzeOrganisationStructure();
		Map<Employee, Double> actualResult = employeeDataStore.getOverPaidManagers();
		assertFalse(actualResult.isEmpty());
	}

	@Test
	public void GetOverpaidManagers_ReturnsEmptyList_WhenGivenRegularEmployees() throws URISyntaxException {
		BigCompany bigCompany = init("employees_normal.csv");
		EmployeeDataStore employeeDataStore = bigCompany.analyzeOrganisationStructure();
		Map<Employee, Double> actualResult = employeeDataStore.getOverPaidManagers();
		assertTrue(actualResult.isEmpty());
	}

	@Test
	public void GetEmployeeReportingLine_ReturnsEmptyList_WhenNoEmployeeExceedsLimit() throws URISyntaxException {
		BigCompany bigCompany = init("employees_normal.csv");
		EmployeeDataStore employeeDataStore = bigCompany.analyzeOrganisationStructure();
		List<String> actualResult = employeeDataStore.getReportLineInfo();
		assertTrue(actualResult.isEmpty());
	}

	@Test
	public void GetEmployeeReportingLine_ReturnsMessageList_WhenSomeEmployeeExceedsLimit() throws URISyntaxException {
		BigCompany bigCompany = init("employees.csv");
		EmployeeDataStore employeeDataStore = bigCompany.analyzeOrganisationStructure();
		List<String> actualResult = employeeDataStore.getReportLineInfo();
		assertFalse(actualResult.isEmpty());
	}

	@Test
	public void ShouldPrintNothing_WhenCsvFileIsEmpty() throws URISyntaxException {
		BigCompany bigCompany = init("employees_empty.csv");
		EmployeeDataStore employeeDataStore = bigCompany.analyzeOrganisationStructure();
		List<String> actualResult = employeeDataStore.getReportLineInfo();
		assertTrue(actualResult.isEmpty());
	}

	private BigCompany init(String csvFile) {
		CsvReaderService csvReaderService = new CsvReader(csvFile);
		FinanceService financeService = new FinanceManager();
		ReportLineService reportLineService = new ReportLineManager();
		StatisticsService statisticsService = new EmployeeStatistics();
		EmployeeService employeeService = new EmployeeManager(csvReaderService, financeService, reportLineService);

		BigCompany bigCompany = new BigCompany(statisticsService, employeeService);

		return bigCompany;
	}

}
