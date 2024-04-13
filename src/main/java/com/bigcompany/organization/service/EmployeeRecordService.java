package com.bigcompany.organization.service;

import java.net.URISyntaxException;
import java.util.List;

import com.bigcompany.organization.application.Employee;

public interface EmployeeRecordService {
	public List<Employee> getEmployeeData() throws URISyntaxException;
}
