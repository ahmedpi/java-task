package com.bigcompany.organization.service;

import java.util.List;
import java.util.Map;

import com.bigcompany.organization.model.Employee;

public interface ReportingLineService {
	public Map<Employee, Integer>  getEmployeesWithLongReportingLine(List<Employee> employees);
}
