package com.epam.javatask.company;

import java.util.Map;

import com.epam.javatask.company.service.StatisticsService;

public class EmployeeStatistics implements StatisticsService {

	private static final String MESSAGE_UNDERPAID_MANAGER = "Name: %s %s, Salary: %s, Underpaid Amount: %s";
	private static final String MESSAGE_OVERPAID_MANAGER = "Name: %s %s, Salary: %s, Overpaid Amount: %s";
	private static final String UNDERLINE = "======================================================================";

//	@Override
//	public void printSalaryInfo() {
//		this.financeService.printSalaryInfo();
//	}
//
//	@Override
//	public void printReportLineInfo() {
//		this.reportLineService.printReportLineInfo();
//	}
//

	@Override
	public void printEmployeeStatistics(Report report) {
		printReportLineInfo(report);

		printSalaryInfo(report);
	}

	private void printReportLineInfo(Report report) {
		if (!report.getReportLineInfo().isEmpty()) {
			System.out.println("NUMBER OF EMPLOYEES WITH TOO LONG REPORTING LINES: ");
			report.getReportLineInfo().stream().forEach(System.out::println);
			System.out.println(UNDERLINE);
		}
	}

	private void printSalaryInfo(Report report) {
		Map<Employee, Double> underPaidManagers = report.getUnderPaidManagers();
		printUnderpaidManagersInfo(underPaidManagers);

		Map<Employee, Double> overPaidManagers = report.getOverPaidManagers();
		printOverpaidManagersInfo(overPaidManagers);
	}

	private void printOverpaidManagersInfo(Map<Employee, Double> overPaidManagers) {
		if (!overPaidManagers.isEmpty()) {
			System.out.println("\nOVERPAID MANAGERS: ");
			for (Map.Entry<Employee, Double> entry : overPaidManagers.entrySet()) {
				Employee employee = entry.getKey();
				Double amount = entry.getValue();
				System.out.println(String.format(MESSAGE_OVERPAID_MANAGER, employee.getFirstName(),
						employee.getLastName(), employee.getSalary(), amount));
			}
			System.out.println(UNDERLINE);
		}
	}

	private void printUnderpaidManagersInfo(Map<Employee, Double> underPaidManagers) {
		if (!underPaidManagers.isEmpty()) {
			System.out.println("UNDERPAID MANAGERS: ");
			for (Map.Entry<Employee, Double> entry : underPaidManagers.entrySet()) {
				Employee employee = entry.getKey();
				Double amount = entry.getValue();
				System.out.println(String.format(MESSAGE_UNDERPAID_MANAGER, employee.getFirstName(),
						employee.getLastName(), employee.getSalary(), amount));
			}
		}
	}

}
