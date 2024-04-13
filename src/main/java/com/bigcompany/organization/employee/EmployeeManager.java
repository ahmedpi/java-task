package com.bigcompany.organization.employee;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.bigcompany.organization.exception.NoEmployeeRecordFoundException;
import com.bigcompany.organization.service.CsvReaderService;
import com.bigcompany.organization.service.EmployeeService;
import com.bigcompany.organization.service.FinanceService;
import com.bigcompany.organization.service.ReportingLineService;

public class EmployeeManager implements EmployeeService {

	private final CsvReaderService csvReaderService;
	private final FinanceService financeService;
	private final ReportingLineService reportingLineService;

	private List<Employee> employees;
	private Map<Employee, List<Employee>> mangerToEmployeesMap;

	public EmployeeManager(CsvReaderService csvReaderService, FinanceService financeService,
			ReportingLineService reportingLineService) {
		this.csvReaderService = csvReaderService;
		this.financeService = financeService;
		this.reportingLineService = reportingLineService;
	}

	@Override
	public OrganizationSummary analyzeOrganizationStructure() throws URISyntaxException {
		initData();
		Map<Employee, Double> underpaidManagers = getUnderPaidManagers();
		Map<Employee, Double> overpaidManagers = getOverPaidManagers();
		List<String> longReportLine = getEmployeeReportingLine();
		OrganizationSummary organizationSummary = new OrganizationSummary(employees, longReportLine, underpaidManagers,
				overpaidManagers);
		return organizationSummary;
	}

	private void initData() throws URISyntaxException, NoEmployeeRecordFoundException {
		employees = this.csvReaderService.loadEmployeeData();
		mangerToEmployeesMap = getManagerToEmployeesMap(employees);
	}

	private Map<Employee, Double> getUnderPaidManagers() {
		return financeService.getUnderPaidManagers(mangerToEmployeesMap);
	}

	private Map<Employee, Double> getOverPaidManagers() {
		return financeService.getOverPaidManagers(mangerToEmployeesMap);
	}

	private List<String> getEmployeeReportingLine() {
		return this.reportingLineService.getEmployeeReportingLine(employees);
	}

	private Map<Employee, List<Employee>> getManagerToEmployeesMap(List<Employee> employees) {
		Map<Employee, List<Employee>> managerEmployeeMap = new HashMap<Employee, List<Employee>>();

		for (Employee emp : employees) {
			Optional<Employee> optionalManager = getManager(emp, employees);

			if (!optionalManager.isPresent()) {
				continue;
			}

			Employee manager = optionalManager.get();
			if (managerEmployeeMap.containsKey(manager)) {
				managerEmployeeMap.get(manager).add(emp);
			} else {
				List<Employee> subordinates = new ArrayList<Employee>();
				subordinates.add(emp);
				managerEmployeeMap.put(manager, subordinates);
			}
		}
		return managerEmployeeMap;
	}

	private Optional<Employee> getManager(Employee employee, List<Employee> employees) {
		Optional<Employee> optionalManager = employees.stream().filter(emp -> emp.getId() == employee.getManagerId())
				.findAny();

		return optionalManager;
	}

}
