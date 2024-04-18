package com.bigcompany.organization.dto;

import java.util.List;
import java.util.Map;

import com.bigcompany.organization.model.Employee;

public final class Organization {
	private final List<Employee> employeeList;
	private final Map<Employee, Integer> employeesWithLongReportLine;
	private final Map<Employee, Double> underPaidManagers;
	private final Map<Employee, Double> overPaidManagers;

	public Organization(List<Employee> employeeList, Map<Employee, Integer> employeesWithLongReportLine,
			Map<Employee, Double> underPaidManagers, Map<Employee, Double> overPaidManagers) {
		this.employeeList = employeeList;
		this.employeesWithLongReportLine = employeesWithLongReportLine;
		this.underPaidManagers = underPaidManagers;
		this.overPaidManagers = overPaidManagers;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public Map<Employee, Integer> getEmployeesWithLongReportLine() {
		return employeesWithLongReportLine;
	}

	public Map<Employee, Double> getUnderPaidManagers() {
		return underPaidManagers;
	}

	public Map<Employee, Double> getOverPaidManagers() {
		return overPaidManagers;
	}

}
