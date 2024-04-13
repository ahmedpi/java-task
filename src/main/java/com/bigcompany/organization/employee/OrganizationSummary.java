package com.bigcompany.organization.employee;

import java.util.List;
import java.util.Map;

public class OrganizationSummary {
	private final List<Employee> employeeList;
	private final List<String> reportLineInfo;
	private final Map<Employee, Double> underPaidManagers;
	private final Map<Employee, Double> overPaidManagers;

	public OrganizationSummary(List<Employee> employeeList, List<String> reportLineInfo, Map<Employee, Double> underPaidManagers,
			Map<Employee, Double> overPaidManagers) {
		this.employeeList = employeeList;
		this.reportLineInfo = reportLineInfo;
		this.underPaidManagers = underPaidManagers;
		this.overPaidManagers = overPaidManagers;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public List<String> getReportingLineInfo() {
		return reportLineInfo;
	}

	public Map<Employee, Double> getUnderPaidManagers() {
		return underPaidManagers;
	}

	public Map<Employee, Double> getOverPaidManagers() {
		return overPaidManagers;
	}

}