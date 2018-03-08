package myapp.repository;


import myapp.model.Department;

import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    Department findByDepartmentName(String departmentName);
}
