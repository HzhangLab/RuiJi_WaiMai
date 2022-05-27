package org.example.ruiji_waimai.service;

import org.example.ruiji_waimai.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee queryEmployeeByUserName(String userName);

    List<Employee> queryAll();

    Integer createEmployee(Employee employee);

    Employee queryEmployeeById(Long employeeId);

    Integer update(Employee employee);
}
