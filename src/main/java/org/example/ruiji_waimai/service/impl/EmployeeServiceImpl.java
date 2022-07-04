package org.example.ruiji_waimai.service.impl;

import org.example.ruiji_waimai.entity.Employee;
import org.example.ruiji_waimai.repository.EmployeeRepository;
import org.example.ruiji_waimai.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee queryEmployeeByUserName(String userName) {
        Example example = new Example(Employee.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", userName);
        List<Employee> employees = employeeRepository.selectByExample(example);
        return employees.isEmpty() ? null : employees.get(0);
    }

    @Override
    public List<Employee> queryAllOrderByCreateTimeDesc() {
        Example example = new Example(Employee.class);
        example.setOrderByClause("create_time desc");
        return employeeRepository.selectByExample(example);
    }

    @Override
    public Integer createEmployee(Employee employee) {
        return employeeRepository.insert(employee);
    }

    @Override
    public Employee queryEmployeeById(Long employeeId) {
        Example example = new Example(Employee.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", employeeId);
        example.setOrderByClause("create_time desc");
        return employeeRepository.selectOneByExample(example);
    }

    @Override
    public Integer update(Employee employee) {
        return employeeRepository.updateByPrimaryKeySelective(employee);
    }

    @Override
    public List<Employee> queryEmployeeByLikeName(String name) {
        Example example = new Example(Employee.class);
        Example.Criteria criteria = example.createCriteria();
        String likeName = "%" + name +"%";
        criteria.andLike("name", likeName);
        return employeeRepository.selectByExample(example);
    }
}
