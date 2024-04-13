package com.bigcompany.organization.service;

import java.util.List;
import java.util.Map;

import com.bigcompany.organization.application.Employee;

public interface Organization {
	public List<Employee> getEmployeeList();

	public Map<Employee, Integer> getEmployeesWithLongReportLine();

	public Map<Employee, Double> getUnderPaidManagers();

	public Map<Employee, Double> getOverPaidManagers();

	public void setUnderPaidManagers(Map<Employee, Double> underPaidManagers);

	public void setOverPaidManagers(Map<Employee, Double> overPaidManagers);

	public Map<Employee, List<Employee>> getMangerToEmployeesMap();

	public void setEmployeesWithLongReportLine(Map<Employee, Integer> employeesWithLongReportLine);
}
