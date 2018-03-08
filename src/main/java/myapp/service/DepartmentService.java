package myapp.service;


import myapp.model.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> findAllDepartments();

    Department findDepartmentById(int id);

    void deleteDepartmentById(int id);

    void createDepartment(Department department);

    Department findByDepartmentName(String departmentName);

}
