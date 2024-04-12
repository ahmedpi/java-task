package com.epam.javatask.company.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import com.epam.javatask.company.Employee;
import com.epam.javatask.company.service.CsvReaderService;

public class CsvReader implements CsvReaderService {
	private final String csvFile;

	private static final String SEPARATOR = ",";
	private static final String UNDERLINE = "======================================================================";

	FileResourcesUtils filResourcesUtils = new FileResourcesUtils();

	public CsvReader(String csvFile) {
		this.csvFile = csvFile;
	}

	@Override
	public List<Employee> loadEmployeeData() {
		File file = getFile(csvFile);
		try {
			file = filResourcesUtils.getFileFromResource(csvFile);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			List<Employee> employees = bufferedReader.lines().skip(1).map(line -> parseEmployee(line))
					.collect(Collectors.toList());
			System.out.println("Employees:");
			employees.stream().forEach(System.out::println);
			System.out.println(UNDERLINE);
			return employees;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}

	}

	private File getFile(String csvFile) {
		try {
			return filResourcesUtils.getFileFromResource(csvFile);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Employee parseEmployee(String data) {

		String[] empData = data.split(SEPARATOR, -1);

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
