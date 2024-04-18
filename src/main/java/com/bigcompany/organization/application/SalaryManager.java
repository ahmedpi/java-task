package com.bigcompany.organization.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import com.bigcompany.organization.model.Employee;
import com.bigcompany.organization.service.SalaryService;

public class SalaryManager implements SalaryService {

	private static final int MINIMUM_MANAGER_SALARY_GAP_IN_PERCENT = 20;
	private static final int MAXIMUM_MANAGER_SALARY_GAP_IN_PERCENT = 50;

	private Map<Employee, Double> underPaidManagers;
	private Map<Employee, Double> overPaidManagers;

	@Override
	public Map<Employee, Double> getUnderPaidManagers(List<Employee> employees) {
		// TODO - remove redundancy
		Map<Employee, List<Employee>> employeeManagerMap = populateManagerToEmployeesMap(employees);

		underPaidManagers = employeeManagerMap.keySet().stream()
				.filter(manager -> manager.getSalary() < calculateManagerMinimumSalary(employeeManagerMap.get(manager)))
				.collect(Collectors.toMap(e -> e, man -> getUnderPaidAmount(man, employeeManagerMap.get(man))));

		return underPaidManagers;
	}

	@Override
	public Map<Employee, Double> getOverPaidManagers(List<Employee> employees) {
		// TODO - remove redundancy
		Map<Employee, List<Employee>> employeeManagerMap = populateManagerToEmployeesMap(employees);

		overPaidManagers = employeeManagerMap.keySet().stream()
				.filter(manager -> manager.getSalary() > calculateManagerMaximumSalary(employeeManagerMap.get(manager)))
				.collect(Collectors.toMap(e -> e, emp -> getOverPaidAmount(emp, employeeManagerMap.get(emp))));

		return overPaidManagers;
	}

	private Double getUnderPaidAmount(Employee manager, List<Employee> subordinates) {
		return calculateManagerMinimumSalary(subordinates) - manager.getSalary();
	}

	private Double getOverPaidAmount(Employee manager, List<Employee> subordinates) {
		return manager.getSalary() - calculateManagerMaximumSalary(subordinates);
	}

	private double calculateManagerMinimumSalary(List<Employee> subordinates) {
		Double averageSubordinatesSalary = getSubordinatesAverageSalary(subordinates);
		return averageSubordinatesSalary + ((averageSubordinatesSalary * MINIMUM_MANAGER_SALARY_GAP_IN_PERCENT) / 100);
	}

	private double calculateManagerMaximumSalary(List<Employee> subordinates) {
		Double averageSubordinatesSalary = getSubordinatesAverageSalary(subordinates);
		return averageSubordinatesSalary + ((averageSubordinatesSalary * MAXIMUM_MANAGER_SALARY_GAP_IN_PERCENT) / 100);
	}

	private Double getSubordinatesAverageSalary(List<Employee> subordinates) {
		OptionalDouble optionalDouble = subordinates.stream().mapToDouble(Employee::getSalary).average();
		if (optionalDouble.isPresent()) {
			return optionalDouble.getAsDouble();
		}
		return 0.0;
	}

	private Map<Employee, List<Employee>> populateManagerToEmployeesMap(List<Employee> employees) {
		Map<Employee, List<Employee>> managerEmployeeMap = new HashMap<Employee, List<Employee>>();

		for (Employee emp : employees) {
			Optional<Employee> optionalManager = getManager(emp, employees);

			if (!optionalManager.isPresent()) {
				continue;
			}

			Employee manager = optionalManager.get();
			if (managerEmployeeMap.containsKey(manager)) {
				managerEmployeeMap.get(manager).add(emp);
			} else {
				List<Employee> subordinates = new ArrayList<Employee>();
				subordinates.add(emp);
				managerEmployeeMap.put(manager, subordinates);
			}
		}
		return managerEmployeeMap;
	}

	private Optional<Employee> getManager(Employee employee, List<Employee> employees) {

		Optional<Employee> optionalManager = employees.stream().filter(emp -> emp.getId() == employee.getManagerId())
				.findAny();

		return optionalManager;
	}
}
