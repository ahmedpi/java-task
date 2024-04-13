package com.bigcompany.organization.service;

import java.net.URISyntaxException;

import com.bigcompany.organization.employee.OrganizationSummary;
import com.bigcompany.organization.exception.NoEmployeeRecordFoundException;

public interface EmployeeService {

	public OrganizationSummary analyzeOrganizationStructure() throws URISyntaxException, NoEmployeeRecordFoundException;

}
