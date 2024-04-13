package com.bigcompany.organization.finance;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bigcompany.organization.employee.Employee;
import com.bigcompany.organization.service.FinanceService;

public class FinanceManager implements FinanceService {

	private static final int MINIMUM_MANAGER_SALARY_GAP_IN_PERCENT = 20;
	private static final int MAXIMUM_MANAGER_SALARY_GAP_IN_PERCENT = 50;

	private Map<Employee, Double> underPaidManagers;
	private Map<Employee, Double> overPaidManagers;

	@Override
	public Map<Employee, Double> getUnderPaidManagers(Map<Employee, List<Employee>> employeeManagerMap) {
		underPaidManagers = employeeManagerMap.keySet().stream()
				.filter(manager -> manager.getSalary() < calculateMinimumSalary(employeeManagerMap.get(manager)))
				.collect(Collectors.toMap(e -> e, man -> getUnderPaidAmount(man, employeeManagerMap.get(man))));

		return underPaidManagers;
	}

	@Override
	public Map<Employee, Double> getOverPaidManagers(Map<Employee, List<Employee>> employeeManagerMap) {
		overPaidManagers = employeeManagerMap.keySet().stream()
				.filter(manager -> manager.getSalary() > calculateMaximumSalary(employeeManagerMap.get(manager)))
				.collect(Collectors.toMap(e -> e, emp -> getOverPaidAmount(emp, employeeManagerMap.get(emp))));

		return overPaidManagers;
	}

	private Double getUnderPaidAmount(Employee manager, List<Employee> subordinates) {
		return calculateMinimumSalary(subordinates) - manager.getSalary();
	}

	private Double getOverPaidAmount(Employee manager, List<Employee> subordinates) {
		return manager.getSalary() - calculateMaximumSalary(subordinates);
	}

	private double calculateMinimumSalary(List<Employee> subordinates) {
		Double averageSubordinatesSalary = getAverageSalary(subordinates);
		return averageSubordinatesSalary + ((averageSubordinatesSalary * MINIMUM_MANAGER_SALARY_GAP_IN_PERCENT) / 100);
	}

	private double calculateMaximumSalary(List<Employee> subordinates) {
		Double averageSubordinatesSalary = getAverageSalary(subordinates);
		return averageSubordinatesSalary + ((averageSubordinatesSalary * MAXIMUM_MANAGER_SALARY_GAP_IN_PERCENT) / 100);
	}

	private Double getAverageSalary(List<Employee> subordinates) {
		return subordinates.stream().mapToDouble(Employee::getSalary).average().getAsDouble();
	}
}