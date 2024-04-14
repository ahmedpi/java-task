package com.bigcompany.organization.application;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import com.bigcompany.organization.exception.NoEmployeeRecordFoundException;
import com.bigcompany.organization.service.EmployeeRecordService;
import com.bigcompany.organization.service.Organization;

public class OrganizationStructure implements Organization {
	private List<Employee> employeeList;
	private Map<Employee, Integer> employeesWithLongReportLine;
	private Map<Employee, Double> underPaidManagers;
	private Map<Employee, Double> overPaidManagers;

	private final EmployeeRecordService employeeRecordService;

	public OrganizationStructure(EmployeeRecordService employeeRecordService)
			throws NoEmployeeRecordFoundException, URISyntaxException, Exception {
		this.employeeRecordService = employeeRecordService;

		initData();
	}

	@Override
	public void setEmployeesWithLongReportLine(Map<Employee, Integer> employeesWithLongReportLine) {
		this.employeesWithLongReportLine = employeesWithLongReportLine;
	}

	@Override
	public void setUnderPaidManagers(Map<Employee, Double> underPaidManagers) {
		this.underPaidManagers = underPaidManagers;
	}

	@Override
	public void setOverPaidManagers(Map<Employee, Double> overPaidManagers) {
		this.overPaidManagers = overPaidManagers;
	}

	private void initData() throws NoEmployeeRecordFoundException, URISyntaxException, Exception {
		employeeList = this.employeeRecordService.processEmployeeData();
	}

	@Override
	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	@Override
	public Map<Employee, Integer> getEmployeesWithLongReportLine() {
		return employeesWithLongReportLine;
	}

	@Override
	public Map<Employee, Double> getUnderPaidManagers() {
		return underPaidManagers;
	}

	@Override
	public Map<Employee, Double> getOverPaidManagers() {
		return overPaidManagers;
	}

}
