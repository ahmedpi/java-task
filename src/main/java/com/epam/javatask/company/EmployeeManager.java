package com.epam.javatask.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.epam.javatask.company.service.CsvReaderService;
import com.epam.javatask.company.service.EmployeeService;
import com.epam.javatask.company.service.FinanceService;
import com.epam.javatask.company.service.ReportLineService;
import com.epam.javatask.company.service.StatisticsService;

public class EmployeeManager implements EmployeeService {

	private final CsvReaderService csvReaderService;
	private final FinanceService financeService;
	private final ReportLineService reportLineService;
	private final StatisticsService statisticsService;

	private List<Employee> employees;
	private Map<Employee, List<Employee>> mangerToEmployeesMap;

	public EmployeeManager(CsvReaderService csvReaderService, FinanceService financeService,
			ReportLineService reportLineService, StatisticsService statisticsService) {
		this.csvReaderService = csvReaderService;
		this.financeService = financeService;
		this.reportLineService = reportLineService;
		this.statisticsService = statisticsService;

		initData();
	}

	@Override
	public Report analyzeOrganisationStructure() {
		Map<Employee, Double> underpaidManagers = getUnderPaidManagers();
		Map<Employee, Double> overpaidManagers = getOverPaidManagers();
		List<String> longReportLine = getReportLineReport();
		Report report = new Report(longReportLine, underpaidManagers, overpaidManagers);
		return report;
	}

	private void initData() {
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

//statistics

	private Map<Employee, List<Employee>> getManagerToEmployeesMap(List<Employee> employees) {
		Map<Employee, List<Employee>> managerEmployeeMap = new HashMap<Employee, List<Employee>>();

		for (Employee emp : employees) {
			Employee manager = getManager(emp, employees);

			if (manager == null) {
				continue;
			}

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

	private Employee getManager(Employee employee, List<Employee> employees) {
		Optional<Employee> optionalManager = employees.stream().filter(emp -> emp.getId() == employee.getManagerId())
				.findAny();
		if (optionalManager.isPresent()) {
			return optionalManager.get();
		}
		return null;
	}

}
