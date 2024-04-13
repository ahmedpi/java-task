package com.bigcompany.organization.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import com.bigcompany.organization.employee.Employee;
import com.bigcompany.organization.exception.NoEmployeeRecordFoundException;
import com.bigcompany.organization.service.EmployeeRecordService;

public class CsvReader implements EmployeeRecordService {
	private final String csvFile;

	private static final String FIELD_SEPARATOR = ",";

	FileResourcesUtils filResourcesUtils = new FileResourcesUtils();

	public CsvReader(String csvFile) {
		this.csvFile = csvFile;
	}

	@Override
	public List<Employee> loadEmployeeData() throws URISyntaxException {

		File file = filResourcesUtils.getFileFromResource(csvFile);

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			List<Employee> employees = bufferedReader.lines().skip(1).map(line -> parseEmployee(line))
					.collect(Collectors.toList());
			return employees;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private Employee parseEmployee(String data) throws NoEmployeeRecordFoundException {

		String[] empData = data.split(FIELD_SEPARATOR, -1);

		Employee employee = new Employee();
		employee.setId(Integer.parseInt(empData[0]));
		employee.setFirstName(empData[1].trim());
		employee.setLastName(empData[2].trim());
		employee.setSalary(Double.parseDouble(empData[3].trim()));
		if (empData[4] != null && !empData[4].isEmpty()) {
			employee.setManagerId(Integer.parseInt(empData[4].trim()));
		}

		return employee;
	}
}
