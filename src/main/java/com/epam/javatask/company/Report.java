package com.epam.javatask.company;

import java.util.List;
import java.util.Map;

public class Report {
	private final List<String> reportLineInfo;
	private final Map<Employee, Double> underPaidManagers;
	private final Map<Employee, Double> overPaidManagers;

	public Report(List<String> reportLineInfo, Map<Employee, Double> underPaidManagers,
			Map<Employee, Double> overPaidManagers) {
		this.reportLineInfo = reportLineInfo;
		this.underPaidManagers = underPaidManagers;
		this.overPaidManagers = overPaidManagers;
	}

	public List<String> getReportLineInfo() {
		return reportLineInfo;
	}

	public Map<Employee, Double> getUnderPaidManagers() {
		return underPaidManagers;
	}

	public Map<Employee, Double> getOverPaidManagers() {
		return overPaidManagers;
	}

}
