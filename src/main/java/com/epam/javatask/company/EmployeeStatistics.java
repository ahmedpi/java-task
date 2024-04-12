package com.epam.javatask.company;

import java.util.Map;

import com.epam.javatask.company.service.StatisticsService;

public class EmployeeStatistics implements StatisticsService {

	private static final String MESSAGE_UNDERPAID_MANAGER = "Name: %s %s, Salary: %s, Underpaid Amount: %s";
	private static final String MESSAGE_OVERPAID_MANAGER = "Name: %s %s, Salary: %s, Overpaid Amount: %s";
	private static final String UNDERLINE = "======================================================================";
	private static final String MESSAGE_TITLE_EMPLOYEES = "EMPLOYEES:";
	private static final String MESSAGE_TITLE_OVERPAID_MANAGERS = "\nOVERPAID MANAGERS:";
	private static final String MESSAGE_TITLE_UNDERPAID_MANAGERS = "\nUNDERPAID MANAGERS:";
	private static final String MESSAGE_TITLE_REPORT_LINE = "\nEMPLOYEES WITH TOO LONG REPORTING LINES: ";
	private static final String NO_OVERPAID_MANAGER_FOUND = "No Overpaid Manager Found.";
	private static final String NO_UNDERPAID_MANAGER_FOUND = "No Underpaid Manager Found.";
	private static final String MESSAGE_ANALYSIS_RESULT = "ANALYSIS RESULT\n";
	private static final String MESSAGE_NO_EMPLOYEE_FOUND = "No Employee List Found\n";

	@Override
	public void printEmployeeStatistics(EmployeeDataStore employeeDataStore) {
		System.out.println(MESSAGE_ANALYSIS_RESULT);
		printEmployeeList(employeeDataStore);
		printReportLineInfo(employeeDataStore);
		printSalaryInfo(employeeDataStore);
		System.out.println(UNDERLINE);
	}

	private void printEmployeeList(EmployeeDataStore employeeDataStore) {
		if (!employeeDataStore.getEmployeeList().isEmpty()) {
			System.out.println(MESSAGE_TITLE_EMPLOYEES);
			employeeDataStore.getEmployeeList().stream().forEach(System.out::println);
		} else {
			System.out.println(MESSAGE_NO_EMPLOYEE_FOUND);
		}

	}

	private void printReportLineInfo(EmployeeDataStore employeeDataStore) {
		if (!employeeDataStore.getEmployeeList().isEmpty() && !employeeDataStore.getReportLineInfo().isEmpty()) {
			System.out.println(MESSAGE_TITLE_REPORT_LINE);
			employeeDataStore.getReportLineInfo().stream().forEach(System.out::println);
		}
	}

	private void printSalaryInfo(EmployeeDataStore employeeDataStore) {
		if (!employeeDataStore.getEmployeeList().isEmpty()) {
			Map<Employee, Double> underPaidManagers = employeeDataStore.getUnderPaidManagers();
			printUnderpaidManagersInfo(underPaidManagers);

			Map<Employee, Double> overPaidManagers = employeeDataStore.getOverPaidManagers();
			printOverpaidManagersInfo(overPaidManagers);
		}
	}

	private void printOverpaidManagersInfo(Map<Employee, Double> overPaidManagers) {
		if (!overPaidManagers.isEmpty()) {
			System.out.println(MESSAGE_TITLE_OVERPAID_MANAGERS);
			for (Map.Entry<Employee, Double> entry : overPaidManagers.entrySet()) {
				Employee employee = entry.getKey();
				Double amount = entry.getValue();
				System.out.println(String.format(MESSAGE_OVERPAID_MANAGER, employee.getFirstName(),
						employee.getLastName(), employee.getSalary(), amount));
			}
		} else {
			System.out.println(NO_OVERPAID_MANAGER_FOUND);
		}
	}

	private void printUnderpaidManagersInfo(Map<Employee, Double> underPaidManagers) {
		if (!underPaidManagers.isEmpty()) {
			System.out.println(MESSAGE_TITLE_UNDERPAID_MANAGERS);
			for (Map.Entry<Employee, Double> entry : underPaidManagers.entrySet()) {
				Employee employee = entry.getKey();
				Double amount = entry.getValue();
				System.out.println(String.format(MESSAGE_UNDERPAID_MANAGER, employee.getFirstName(),
						employee.getLastName(), employee.getSalary(), amount));
			}
		} else {
			System.out.println(NO_UNDERPAID_MANAGER_FOUND);
		}
	}

}
