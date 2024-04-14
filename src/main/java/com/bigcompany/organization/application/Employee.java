package com.bigcompany.organization.application;

import java.util.List;
import java.util.Objects;

public class Employee {
	private int id;
	private String firstName;
	private String lastName;
	private double salary;
	private int managerId;

	public Employee() {
	}

	public Employee(int id, String firstName, String lastName, double salary, int managerId) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.managerId = managerId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, id, lastName, managerId, salary);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(firstName, other.firstName) && id == other.id && Objects.equals(lastName, other.lastName)
				&& managerId == other.managerId
				&& Double.doubleToLongBits(salary) == Double.doubleToLongBits(other.salary);
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", salary=" + salary
				+ ", managerId=" + managerId + "]";
	}

	public boolean isCeo() {
		return managerId == 0;
	}

	public int getReportingLine(List<Employee> employeeList) {
		int countReportingLevel = 0;

		if (isCeo()) {
			return 0;
		}

		countReportingLevel++;
		for (Employee e : employeeList) {
			if (isManagerSubordinate(managerId, e)) {
				countReportingLevel += e.getReportingLine(employeeList);
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
