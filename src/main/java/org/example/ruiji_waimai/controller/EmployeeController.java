package org.example.ruiji_waimai.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.example.ruiji_waimai.common.Result;
import org.example.ruiji_waimai.controller.VO.PageResult;
import org.example.ruiji_waimai.entity.Employee;
import org.example.ruiji_waimai.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebMethod;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employ) {
        Employee employee = employeeService.queryEmployeeByUserName(employ.getUsername());
        if (null == employee) {
            return Result.error("登陆失败！");
        }
        // 对密码进行加密处理
        String encodePassword = DigestUtils.md5DigestAsHex(employ.getPassword().getBytes());
        if (!employee.getPassword().equals(encodePassword)) {
            return Result.error("登陆失败！");
        }
        // 对employee状态进行判断 0:为禁用状态
        if (employee.getStatus() == 0) {
            return Result.error("账号已被禁用！");
        }

        // 登录成功！
        request.getSession().setAttribute("employee", employee.getId());
        return Result.success(employee);
    }

    @GetMapping("page")
    public Result<PageResult<Employee>> queryEmployees(@RequestParam("page") int pageNum, @RequestParam("pageSize") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Employee> employees = employeeService.queryAll();
        PageInfo<Employee> pageInfo = new PageInfo<>(employees);
        PageResult<Employee> pageResult = new PageResult<>();
        pageResult.setRecords(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return Result.success(pageResult);
    }

    @PostMapping("logout")
    public Result<String> logout(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().removeAttribute("employee");
        return Result.success("成功登出");
    }

    @PostMapping
    public Result<String> createEmployee(HttpServletRequest httpRequest, @RequestBody Employee employee) {
        log.info("create employee info: " + employee);
        // 验证username 是否唯一 通过username进行查询
        Employee tempEmployee = employeeService.queryEmployeeByUserName(employee.getUsername());
        if (null != tempEmployee){
            return Result.error("username 已存在");
        }
        // 密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setStatus(1); // 1:为默认值，表示用户未被禁用
        // createTime 为当前时间
        LocalDateTime createTime = LocalDateTime.now();
        employee.setCreateTime(createTime);
        employee.setUpdateTime(createTime);
        // createUser
        Long createUser = (long) httpRequest.getSession().getAttribute("employee");
        employee.setCreateUser(createUser);
        employee.setUpdateUser(createUser);
        int size = employeeService.createEmployee(employee);
        if (size == 0) {
            return Result.error("create user Error!");
        }
        return Result.success("create employee success");
    }

    @GetMapping("/{employeeId}")
    public Result<Employee> geiEmployeeById(@PathParam("employeeId") Long employeeId ) {
        log.info("getEmployeeById: id:" + employeeId);
        Employee employee = employeeService.queryEmployeeById(employeeId);
        return employee == null ? Result.error("error") : Result.success(employee);
    }

    @PutMapping
    public Result<String> updateEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("updateEmployeeStatus: requestParams:" + employee);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser( (Long) request.getSession().getAttribute("employee"));
        int size = employeeService.update(employee);
        return size > 0 ? Result.success("sucess") : Result.error("error");
    }
}
