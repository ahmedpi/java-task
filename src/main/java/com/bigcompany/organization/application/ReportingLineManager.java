package com.bigcompany.organization.application;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bigcompany.organization.service.Organization;
import com.bigcompany.organization.service.ReportingLineService;

public class ReportingLineManager implements ReportingLineService {
	private static final int MAXIMUM_REPORTING_LINE = 4;

	private final Organization organization;

	public ReportingLineManager(Organization organization) {
		this.organization = organization;
	}

	public void checkEmployeesWithLongReportingLine() {
		Map<Employee, Integer> employeesWithLongLine = getEmployeesWithExtraLine(organization.getEmployeeList());
		organization.setEmployeesWithLongReportLine(employeesWithLongLine);
	}

	private Map<Employee, Integer> getEmployeesWithExtraLine(List<Employee> employees) {
		return employees.stream().filter(emp -> isReportingLineTooLong(emp.getReportingLine(employees)))
				.collect(Collectors.toMap(emp -> emp, emp -> countExtraReportLine(emp.getReportingLine(employees))));
	}

	private int countExtraReportLine(Integer employeeReportingLevel) {
		return employeeReportingLevel - MAXIMUM_REPORTING_LINE;
	}

	private boolean isReportingLineTooLong(int levels) {
		return levels > MAXIMUM_REPORTING_LINE;
	}
}
