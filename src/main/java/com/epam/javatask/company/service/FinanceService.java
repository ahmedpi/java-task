package com.epam.javatask.company.service;

import java.util.List;
import java.util.Map;

import com.epam.javatask.company.employee.Employee;

public interface FinanceService {
	// public void checkManagersSalary(Map<Employee, List<Employee>>
	// managerTOEmployeesMap);

	public Map<Employee, Double> getUnderPaidManagers(Map<Employee, List<Employee>> employeeManagerMap);

	public Map<Employee, Double> getOverPaidManagers(Map<Employee, List<Employee>> employeeManagerMap);

	// public void printSalaryInfo();
}
