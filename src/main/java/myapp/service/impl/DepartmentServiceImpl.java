package myapp.service.impl;

import myapp.exception.MyResourceNotFoundException;
import myapp.model.Department;
import myapp.repository.DepartmentRepository;
import myapp.service.DepartmentService;

import java.util.List;
import java.util.Optional;

import myapp.service.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final static Logger log = LogManager.getLogger(DepartmentServiceImpl.class);
    private final DepartmentRepository repository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Department> findAllDepartments() {
        log.info("Find all departments.");
        List<Department> departments = (List<Department>) repository.findAll();
        return Optional.of(departments).orElseThrow(MyResourceNotFoundException::new);
    }

    @Override
    public Department findDepartmentById(int id) {
        log.info("Find department by id - " + id);
        return repository.findById(id).stream().
                findAny().
                orElseThrow(MyResourceNotFoundException::new);
    }

    @Override
    public void deleteDepartmentById(int id) {
        log.info("Delete department by id - " + id);
        repository.deleteById(id);
    }

    @Override
    public void createDepartment(Department department) {
        ofNullable(department).orElseThrow(MyResourceNotFoundException::new);
        log.info("Add department");
        repository.save(department);
    }

    public Department findByDepartmentName(String departmentName) {
        Department department = repository.findByDepartmentName(departmentName);
        log.info("Find by department name - " + departmentName + ", return - " + department);
        return ofNullable(department).orElseThrow(MyResourceNotFoundException::new);
    }

}
