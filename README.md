**Organizational Structure Analyzer**

This project allows a company to analyze its organizational
structure and identify potential improvements using the following business rules:
  - every manager should earn at least 20% more than the average salary of its direct subordinates, but no more than 50% more
than that average.
  - a reporting lines of more than 4 managers between an employee and the CEO is considered too long.

This application reads a CSV file which contains information about all the employees, with structure:

  |   Id   | firstName | lastName | salary | managerId|
  --- | --- | --- | --- |--- 
  |  123     | Joe       |  Doe     |  60000 |            |
  | 124      | Martin    | Chekov   |  45000 |  123       |
  | 125      | Bob       | Ronstad  | 47000  |  123       |
  | 300      | Alice     | Hasacat  | 50000  |  124       |
  | 305      | Brett     | Hardleaf | 34000  |  300       |
  
After reading the CSV file, the application reports:
- which managers earn less than they should, and by how much
- which managers earn more than they should, and by how much
- which employees have a reporting line which is too long, and by how much
  
**Build the Java code**
Issue the following at the command line to compile your Java code, run any tests, and package the code up in a JAR file within the target directory
  mvn package
To execute the JAR file run:
  java -jar target/java-task-0.0.1-SNAPSHOT.jar [FILE PATH]

**Useful Resources**
Java Docs: https://github.com/ahmedpi/java-task/tree/main/doc

