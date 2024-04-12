package com.epam.javatask.company;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

//	@Before
//	public void setUp() {
//
//		CsvReaderService csvReaderService = new CsvReader(csvFile);
//		FinanceService financeService = new FinanceManager();
//		ReportLineService reportingLineService = new ReportLineManager();
//		employeeService = new EmployeeManager(csvReaderService, financeService, reportingLineService);
//	}

	@Test
	public void CheckManagerSalary_ReturnsList_WhenGivenUnderpaidEmployees() {
		BigCompany bigCompany = init("employees.csv");
		Report report = bigCompany.analyzeOrganisationStructure();
		Map<Employee, Double> actualResult = report.getUnderPaidManagers();
		assertFalse(actualResult.isEmpty());
	}

	@Test
	public void CheckManagerSalary_ReturnsList_WhenGivenOverpaidEmployees() {
		BigCompany bigCompany = init("employees.csv");
		Report report = bigCompany.analyzeOrganisationStructure();
		Map<Employee, Double> actualResult = report.getOverPaidManagers();
		assertFalse(actualResult.isEmpty());
	}

	@Test
	public void CheckEmployeeReportingLineManager_ReturnsEmptyList_WhenNoEmployeeExceedsLimit() {
		BigCompany bigCompany = init("employees_no_long_lines.csv");
		Report report = bigCompany.analyzeOrganisationStructure();
		List<String> actualResult = report.getReportLineInfo();
		assertTrue(actualResult.isEmpty());
	}

	@Test
	public void CheckEmployeeReportingLineManager_ReturnsMessageList_WhenSomeEmployeeExceedsLimit() {
		BigCompany bigCompany = init("employees.csv");
		Report report = bigCompany.analyzeOrganisationStructure();
		List<String> actualResult = report.getReportLineInfo();
		assertFalse(actualResult.isEmpty());
	}

	private BigCompany init(String csvFile) {
		CsvReaderService csvReaderService = new CsvReader(csvFile);
		FinanceService financeService = new FinanceManager();
		ReportLineService reportLineService = new ReportLineManager();
		StatisticsService statisticsService = new EmployeeStatistics();
		EmployeeService employeeService = new EmployeeManager(csvReaderService, financeService, reportLineService,
				statisticsService);

		BigCompany bigCompany = new BigCompany(statisticsService, employeeService);

		return bigCompany;
	}

}
