package com.bigcompany.organization;

import java.net.URISyntaxException;

import com.bigcompany.organization.service.FinanceService;
import com.bigcompany.organization.service.Organization;
import com.bigcompany.organization.service.ReportingLineService;
import com.bigcompany.organization.service.StatisticsService;

public class OrganizationManager {

	private final FinanceService financeService;
	private final ReportingLineService reportingLineService;
	private final StatisticsService statisticsService;

	private final Organization organization;

	public OrganizationManager(Organization organization, FinanceService financeService,
			ReportingLineService reportingLineService, StatisticsService statisticsService) {
		this.organization = organization;
		this.financeService = financeService;
		this.reportingLineService = reportingLineService;
		this.statisticsService = statisticsService;
	}

	public Organization analyzeOrganizationStructure() throws URISyntaxException {
		
		financeService.checkManagersSalary(organization);
		
		reportingLineService.checkEmployeesWithLongReportingLine(organization);
		
		printEmployeeStatistics(organization);

		return organization;
	}

	private void printEmployeeStatistics(Organization organization) {
		this.statisticsService.printEmployeeStatistics(organization);
	}
}
