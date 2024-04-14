package com.bigcompany.organization.application;

import java.util.Map;

import com.bigcompany.organization.service.Organization;
import com.bigcompany.organization.service.StatisticsService;

public class StatisticsManager implements StatisticsService {

	private static final String UNDERLINE = "======================================================================";
	private static final String MESSAGE_TITLE_EMPLOYEES = "EMPLOYEES:";
	private static final String MESSAGE_TITLE_REPORT_LINE = "\nEMPLOYEES WITH TOO LONG REPORTING LINE: ";
	private static final String NO_OVERPAID_MANAGER_FOUND = "No Overpaid Manager Found.";
	private static final String NO_UNDERPAID_MANAGER_FOUND = "No Underpaid Manager Found.";
	private static final String MESSAGE_ANALYSIS_RESULT = "ANALYSIS RESULT\n";
	private static final String MESSAGE_NO_EMPLOYEE_FOUND = "No Employee List Found\n";

	private static final String MESSAGE_HEADER_REPORT_LINE = "NAME\t\t EXCEEDS REPORTING LIMIT BY";
	private static final String MESSAGE_LONG_REPORTING_LINE = "%s %s \t %s level(s)";

	private static final String MESSAGE_TITLE_OVERPAID_MANAGERS = "\nOVERPAID MANAGERS:";
	private static final String MESSAGE_HEADER_OVERPAID_MANAGERS = "NAME\t\t SALARY \t OVERPAID AMOUNT";
	private static final String MESSAGE_OVERPAID_MANAGER = "%5s %s\t %s\t %s";

	private static final String MESSAGE_TITLE_UNDERPAID_MANAGERS = "\nUNDERPAID MANAGERS:";
	private static final String MESSAGE_HEADER_UNDERPAID_MANAGERS = "NAME\t\t SALARY \t UNDERPAID AMOUNT";
	private static final String MESSAGE_UNDERPAID_MANAGER = "%5s %s\t %s\t %s";

	@Override
	public void printEmployeeStatistics(Organization organization) {
		System.out.println(MESSAGE_ANALYSIS_RESULT);
		printEmployeeList(organization);
		printReportLineInfo(organization);
		printSalaryInfo(organization);
		System.out.println(UNDERLINE);
	}

	private void printEmployeeList(Organization organization) {
		if (!organization.getEmployeeList().isEmpty()) {
			System.out.println(MESSAGE_TITLE_EMPLOYEES);
			organization.getEmployeeList().stream().forEach(System.out::println);
		} else {
			System.out.println(MESSAGE_NO_EMPLOYEE_FOUND);
		}

	}

	private void printReportLineInfo(Organization organization) {
		if (!organization.getEmployeeList().isEmpty() && !organization.getEmployeesWithLongReportLine().isEmpty()) {
			System.out.println(MESSAGE_TITLE_REPORT_LINE);
			System.out.println(MESSAGE_HEADER_REPORT_LINE);
			Map<Employee, Integer> reportingLine = organization.getEmployeesWithLongReportLine();
			reportingLine.keySet().stream().forEach(emp -> System.out.println(String.format(MESSAGE_LONG_REPORTING_LINE,
					emp.getFirstName(), emp.getLastName(), reportingLine.get(emp))));
		}
	}

	private void printSalaryInfo(Organization organization) {
		if (!organization.getEmployeeList().isEmpty()) {
			Map<Employee, Double> underPaidManagers = organization.getUnderPaidManagers();
			printUnderpaidManagersInfo(underPaidManagers);

			Map<Employee, Double> overPaidManagers = organization.getOverPaidManagers();
			printOverpaidManagersInfo(overPaidManagers);
		}
	}

	private void printOverpaidManagersInfo(Map<Employee, Double> overPaidManagers) {
		if (!overPaidManagers.isEmpty()) {
			System.out.println(MESSAGE_TITLE_OVERPAID_MANAGERS);
			System.out.println(MESSAGE_HEADER_OVERPAID_MANAGERS);
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
			System.out.println(MESSAGE_HEADER_UNDERPAID_MANAGERS);
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
