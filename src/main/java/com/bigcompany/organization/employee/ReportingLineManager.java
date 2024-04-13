package com.bigcompany.organization.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bigcompany.organization.service.ReportingLineService;

public class ReportingLineManager implements ReportingLineService {
	private static final int MAXIMUM_REPORTING_LINE = 4;

	@Override
	public Map<Employee, Integer> getEmployeesWithLongReportingLine(List<Employee> employees) {
		Map<Employee, Integer> reportingLinesMap = getEmployeeReportingLines(employees);

		return getEmployeesWithExtraLine(reportingLinesMap);
	}

	private Map<Employee, Integer> getEmployeesWithExtraLine(Map<Employee, Integer> reportingLinesMap) {
		return reportingLinesMap.keySet().stream().filter(emp -> isReportingLineTooLong(reportingLinesMap.get(emp)))
				.collect(Collectors.toMap(emp -> emp, emp -> countExtraReportLine(reportingLinesMap.get(emp))));
	}

	private Map<Employee, Integer> getEmployeeReportingLines(List<Employee> employees) {
		Map<Employee, Integer> reportingLinesMap = new HashMap<Employee, Integer>();
		employees.stream().forEach(emp -> reportingLinesMap.put(emp, countNumberOfLevels(emp, employees)));
		return reportingLinesMap;
	}

	private int countExtraReportLine(Integer employeeReportingLevel) {
		return employeeReportingLevel - MAXIMUM_REPORTING_LINE;
	}

	private boolean isReportingLineTooLong(int levels) {
		return levels > MAXIMUM_REPORTING_LINE;
	}

	private int countNumberOfLevels(Employee employee, List<Employee> employeeList) {
		int managerId = employee.getManagerId();
		int countReportingLevel = 0;
		if (employee.isCeo()) {
			return 0;
		}

		countReportingLevel++;
		for (Employee e : employeeList) {
			if (isManagerSubordinate(managerId, e)) {
				countReportingLevel += countNumberOfLevels(e, employeeList);
			}
		}
		return countReportingLevel;
	}

	private boolean isManagerSubordinate(int managerId, Employee employee) {
		return employee.getId() == managerId && isNotCeo(employee);
	}

	private boolean isNotCeo(Employee employee) {
		return !employee.isCeo();
	}
}
