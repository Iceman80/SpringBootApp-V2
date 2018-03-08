package myapp.service.impl;

import myapp.exception.MyResourceNotFoundException;
import myapp.model.Department;
import myapp.repository.DepartmentRepository;
import myapp.service.DepartmentService;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final static Logger log = LogManager.getLogger(DepartmentServiceImpl.class);

    private Department department;

    @Autowired
    private DepartmentRepository repository;

    @Override
    public List<Department> findAllDepartments() {
        log.info("Find all departments.");
        List<Department> departments = (List<Department>) repository.findAll();
        if (departments.isEmpty()) {
            throw new MyResourceNotFoundException();
        }
        return departments;
    }

    @Override
    public Department findDepartmentById(int id) {
        log.info("Find department by id - " + id);
        department = repository.findOne(id);
        myException(department);
        return department;
    }

    @Override
    public void deleteDepartmentById(int id) {
        log.info("Delete department by id - " + id);
        repository.delete(id);
    }

    @Override
    public void createDepartment(Department department) {
        log.info("Add department");
        myException(department);
        repository.save(department);
    }

    public Department findByDepartmentName(String departmentName) {
        department = repository.findByDepartmentName(departmentName);
        log.info("Find by department name - " + departmentName + ", return - " + department);
        myException(department);
        return department;
    }

    private void myException(Department department) {
        if (department == null) {
            throw new MyResourceNotFoundException();
        }
    }
}
