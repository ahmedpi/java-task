package com.bigcompany.organization.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import com.bigcompany.organization.service.EmployeeRecordService;
import com.bigcompany.organization.utility.FileResourcesUtils;

public class EmployeeRecordReader implements EmployeeRecordService {
	private final String sourceFilePath;

	private static final String FIELD_SEPARATOR = ",";

	FileResourcesUtils filResourcesUtils = new FileResourcesUtils();

	public EmployeeRecordReader(String sourceFilePath) {
		this.sourceFilePath = sourceFilePath;
	}

	@Override
	public List<Employee> getEmployeeData() throws URISyntaxException {

		File file = filResourcesUtils.getFileFromResource(sourceFilePath);

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			List<Employee> employees = bufferedReader.lines().skip(1).map(line -> parseEmployee(line))
					.collect(Collectors.toList());
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
		if (empData[4] != null && !empData[4].isEmpty()) {
			employee.setManagerId(Integer.parseInt(empData[4].trim()));
		}

		return employee;
	}

}
