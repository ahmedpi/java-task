package com.bigcompany.organization.service;

import java.util.List;
import java.util.Map;

import com.bigcompany.organization.employee.Employee;

public interface FinanceService {
	public Map<Employee, Double> getUnderPaidManagers(Map<Employee, List<Employee>> employeeManagerMap);

	public Map<Employee, Double> getOverPaidManagers(Map<Employee, List<Employee>> employeeManagerMap);
}
