package com.bigcompany.organization.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bigcompany.organization.service.Organization;
import com.bigcompany.organization.service.SalaryService;

public class SalaryManager implements SalaryService {

	private static final int MINIMUM_MANAGER_SALARY_GAP_IN_PERCENT = 20;
	private static final int MAXIMUM_MANAGER_SALARY_GAP_IN_PERCENT = 50;

	private Map<Employee, Double> underPaidManagers;
	private Map<Employee, Double> overPaidManagers;

	@Override
	public void checkManagersSalary(Organization organization) {
		Map<Employee, List<Employee>> employeeManagerMap = populateManagerToEmployeesMap(
				organization.getEmployeeList());
		organization.setUnderPaidManagers(getUnderPaidManagers(employeeManagerMap));
		organization.setOverPaidManagers(getOverPaidManagers(employeeManagerMap));
	}

	private Map<Employee, Double> getUnderPaidManagers(Map<Employee, List<Employee>> employeeManagerMap) {
		underPaidManagers = employeeManagerMap.keySet().stream()
				.filter(manager -> manager.getSalary() < calculateManagerMinimumSalary(employeeManagerMap.get(manager)))
				.collect(Collectors.toMap(e -> e, man -> getUnderPaidAmount(man, employeeManagerMap.get(man))));

		return underPaidManagers;
	}

	private Map<Employee, Double> getOverPaidManagers(Map<Employee, List<Employee>> employeeManagerMap) {
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
		return subordinates.stream().mapToDouble(Employee::getSalary).average().getAsDouble();
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
