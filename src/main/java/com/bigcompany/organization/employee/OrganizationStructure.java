package com.bigcompany.organization.employee;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.bigcompany.organization.exception.NoEmployeeRecordFoundException;
import com.bigcompany.organization.service.EmployeeRecordService;
import com.bigcompany.organization.service.Organization;

public class OrganizationStructure implements Organization {
	private List<Employee> employeeList;
	Map<Employee, List<Employee>> mangerToEmployeesMap;
	private Map<Employee, Integer> employeesWithLongReportLine;
	private Map<Employee, Double> underPaidManagers;
	private Map<Employee, Double> overPaidManagers;

	private final EmployeeRecordService employeeRecordService;

	public OrganizationStructure(EmployeeRecordService employeeRecordService)
			throws NoEmployeeRecordFoundException, URISyntaxException {
		this.employeeRecordService = employeeRecordService;

		initData();
	}

	@Override
	public void setEmployeesWithLongReportLine(Map<Employee, Integer> employeesWithLongReportLine) {
		this.employeesWithLongReportLine = employeesWithLongReportLine;
	}

	@Override
	public void setUnderPaidManagers(Map<Employee, Double> underPaidManagers) {
		this.underPaidManagers = underPaidManagers;
	}

	@Override
	public void setOverPaidManagers(Map<Employee, Double> overPaidManagers) {
		this.overPaidManagers = overPaidManagers;
	}

	private void initData() throws NoEmployeeRecordFoundException, URISyntaxException {
		employeeList = this.employeeRecordService.getEmployeeData();
		mangerToEmployeesMap = populateManagerToEmployeesMap(employeeList);
	}

	@Override
	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	@Override
	public Map<Employee, Integer> getEmployeesWithLongReportLine() {
		return employeesWithLongReportLine;
	}

	@Override
	public Map<Employee, Double> getUnderPaidManagers() {
		return underPaidManagers;
	}

	@Override
	public Map<Employee, Double> getOverPaidManagers() {
		return overPaidManagers;
	}

	@Override
	public Map<Employee, List<Employee>> getMangerToEmployeesMap() {
		return mangerToEmployeesMap;
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
