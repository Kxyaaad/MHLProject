package org.mhl.service;

import org.mhl.dao.EmployeeDao;
import org.mhl.domain.Employee;

/**
 * @author k
 * 2024/4/26 下午3:18
 * @version 1.0
 */
public class EmployeeService {

    private static final EmployeeDao employeeDao = new EmployeeDao();
    public static Employee getEmployeeByIdAndPwd(String empId, String pwd) {
        return employeeDao.querySingle("SELECT * FROM employee WHERE empId = ? AND pwd = MD5(?)", Employee.class, empId, pwd);
    }

}
