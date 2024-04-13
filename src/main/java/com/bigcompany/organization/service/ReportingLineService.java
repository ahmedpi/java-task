package com.bigcompany.organization.service;

import java.util.List;

import com.bigcompany.organization.employee.Employee;

public interface ReportingLineService {
	public List<String> getEmployeeReportingLine(List<Employee> employees);
}
