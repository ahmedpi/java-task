package com.bigcompany.organization.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bigcompany.organization.exception.DuplicateEmployeeRecordFoundException;
import com.bigcompany.organization.exception.NoEmployeeRecordFoundException;
import com.bigcompany.organization.model.Employee;
import com.bigcompany.organization.service.EmployeeRecordService;
import com.bigcompany.organization.utility.FileResourcesUtils;

public class EmployeeRecordReader implements EmployeeRecordService {
	private final String sourceFilePath;

	private static final String FIELD_SEPARATOR = ",";

	private static final String ERROR_DUPLICATE_EMPLOYEE_RECORD = "Error: Duplicate Employee Record Found";
	private static final String ERROR_NO_EMPLOYEE_RECORD_FOUND = "Error: No Employee Record Found";

	FileResourcesUtils filResourcesUtils = new FileResourcesUtils();

	public EmployeeRecordReader(String sourceFilePath) throws Exception {
		this.sourceFilePath = sourceFilePath;

	}

	/**
	 * Assumptions: An employee can not be a manager to him/herself. An employee can
	 * not report to it's subordinates (We can not have loop)
	 * 
	 * @throws DuplicateEmployeeRecordFoundException
	 * @throws NoEmployeeRecordFoundException
	 * @throws URISyntaxException
	 */
	@Override
	public List<Employee> processEmployeeData()
			throws NoEmployeeRecordFoundException, DuplicateEmployeeRecordFoundException, URISyntaxException {
		File file = filResourcesUtils.getFileFromResource(sourceFilePath);
		List<Employee> employeeList = mapCsvToEmployee(file);
		validateEmployeeData(employeeList);
		return employeeList;

	}

	private List<Employee> mapCsvToEmployee(File file) {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			List<Employee> employees = bufferedReader.lines().skip(1).filter(line -> !line.isEmpty())
					.map(line -> parseEmployee(line)).collect(Collectors.toList());
			return employees;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private Employee parseEmployee(String data) {
		String[] empData = data.split(FIELD_SEPARATOR, -1);

		Employee employee = new Employee();
		employee.setId(Integer.parseInt(empData[0]));
		employee.setFirstName(empData[1].trim());
		employee.setLastName(empData[2].trim());
		employee.setSalary(Double.parseDouble(empData[3].trim()));
		if (hasManager(empData[4])) {
			employee.setManagerId(Integer.parseInt(empData[4].trim()));
		}

		return employee;
	}

	private boolean hasManager(String managerId) {
		return !managerId.isEmpty();
	}

	private void validateEmployeeData(List<Employee> employeeList)
			throws NoEmployeeRecordFoundException, DuplicateEmployeeRecordFoundException {
		checkEmptyRecord(employeeList);
		checkDuplicatedEmployees(employeeList);
	}

	private void checkEmptyRecord(List<Employee> employeeList) throws NoEmployeeRecordFoundException {
		if (employeeList.isEmpty()) {
			throw new NoEmployeeRecordFoundException(ERROR_NO_EMPLOYEE_RECORD_FOUND);
		}
	}

	private void checkDuplicatedEmployees(List<Employee> employeeList) throws DuplicateEmployeeRecordFoundException {
		Set<Employee> items = employeeList.stream()
				.filter(employee -> Collections.frequency(employeeList, employee) > 1).collect(Collectors.toSet());

		if (!items.isEmpty()) {
			throw new DuplicateEmployeeRecordFoundException(ERROR_DUPLICATE_EMPLOYEE_RECORD);
		}
	}

}
