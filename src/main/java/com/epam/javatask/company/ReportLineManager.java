package com.epam.javatask.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.epam.javatask.company.service.ReportLineService;

public class ReportLineManager implements ReportLineService {
	private static final int MAXIMUM_REPORTING_LINE = 4;
	private static final String MESSAGE_LONG_REPORTING_LINE = "Name: %s %s, Reporting line: %s, Exceeds reporting limit by: %s level(s)";
	private static final String UNDERLINE = "======================================================================";

	private List<String> reportLineInfo;

	@Override
	public List<String> getReportLineReport(List<Employee> employees) {
		Map<Employee, Integer> longReportLinesMap = new HashMap<Employee, Integer>();
		for (Employee employee : employees) {
			int numberOflevels = countNumberOfLevels(employee, employees);
			if (isReportingLineTooLong(numberOflevels)) {
				longReportLinesMap.put(employee, numberOflevels);
			}
		}
		reportLineInfo = getReportingLineInfo(longReportLinesMap);
		return reportLineInfo;
	}

//	@Override
//	public void printReportLineInfo() {
//		if (!reportLineInfo.isEmpty()) {
//			System.out.println("NUMBER OF EMPLOYEES WITH TOO LONG REPORTING LINES: ");
//			reportLineInfo.stream().forEach(System.out::println);
//			System.out.println(UNDERLINE);
//		}
//	}

	private boolean isReportingLineTooLong(int levels) {
		return levels > MAXIMUM_REPORTING_LINE;
	}

	private List<String> getReportingLineInfo(Map<Employee, Integer> longReportLinesMap) {
		List<String> info = longReportLinesMap.keySet().stream()
				.map(emp -> String.format(MESSAGE_LONG_REPORTING_LINE, emp.getFirstName(), emp.getLastName(),
						longReportLinesMap.get(emp), longReportLinesMap.get(emp) - MAXIMUM_REPORTING_LINE))
				.collect(Collectors.toList());

		return info;
	}

	private int countNumberOfLevels(Employee employee, List<Employee> employeeList) {
		int managerId = employee.getManagerId();
		int countLevel = 0;
		if (managerId == 0) {
			return 0; // CEO
		}

		countLevel++;

		// count manager above current manager
		for (Employee e : employeeList) {
			if (e.getId() == managerId && e.getManagerId() > 0) {
				countLevel += countNumberOfLevels(e, employeeList);
			}
		}
		return countLevel;
	}
}
