package com.epam.javatask.company;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.epam.javatask.company.exception.NoEmployeeRecordFoundException;
import com.epam.javatask.company.service.CsvReaderService;
import com.epam.javatask.company.service.EmployeeService;
import com.epam.javatask.company.service.FinanceService;
import com.epam.javatask.company.service.ReportLineService;

public class EmployeeManager implements EmployeeService {

	private final CsvReaderService csvReaderService;
	private final FinanceService financeService;
	private final ReportLineService reportLineService;

	private List<Employee> employees;
	private Map<Employee, List<Employee>> mangerToEmployeesMap;

	public EmployeeManager(CsvReaderService csvReaderService, FinanceService financeService,
			ReportLineService reportLineService) {
		this.csvReaderService = csvReaderService;
		this.financeService = financeService;
		this.reportLineService = reportLineService;
	}

	@Override
	public EmployeeDataStore analyzeOrganisationStructure() throws URISyntaxException {
		initData();
		Map<Employee, Double> underpaidManagers = getUnderPaidManagers();
		Map<Employee, Double> overpaidManagers = getOverPaidManagers();
		List<String> longReportLine = getReportLineReport();
		EmployeeDataStore employeeDataStore = new EmployeeDataStore(employees, longReportLine, underpaidManagers,
				overpaidManagers);
		return employeeDataStore;
	}

	private void initData() throws URISyntaxException, NoEmployeeRecordFoundException {
		employees = this.csvReaderService.loadEmployeeData();
		mangerToEmployeesMap = getManagerToEmployeesMap(employees);
	}

	@Override
	public Map<Employee, Double> getUnderPaidManagers() {
		return financeService.getUnderPaidManagers(mangerToEmployeesMap);
	}

	@Override
	public Map<Employee, Double> getOverPaidManagers() {
		return financeService.getOverPaidManagers(mangerToEmployeesMap);
	}

	private List<String> getReportLineReport() {
		return this.reportLineService.getReportLineReport(employees);
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
