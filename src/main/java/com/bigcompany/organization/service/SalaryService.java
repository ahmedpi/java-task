package com.bigcompany.organization.service;

import java.util.List;
import java.util.Map;

import com.bigcompany.organization.model.Employee;

public interface SalaryService {
	// public Map<Employee, List<Employee>> checkManagersSalary(List<Employee>
	// employees);
	public Map<Employee, Double> getUnderPaidManagers(List<Employee> employees);

	public Map<Employee, Double> getOverPaidManagers(List<Employee> employees);
}
