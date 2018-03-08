package myapp.service.impl;

import myapp.exception.MyResourceNotFoundException;
import myapp.model.Department;
import myapp.model.Status;
import myapp.model.User;
import myapp.repository.DepartmentRepository;
import myapp.repository.TaskRepository;
import myapp.repository.UserRepository;
import myapp.service.Helper;
import myapp.service.UserService;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("employeeService")
public class EmployeeServiceImpl implements UserService {
    private final static Logger log = LogManager.getLogger(EmployeeServiceImpl.class);
    private User user;

    @Autowired
    private UserRepository repository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private TaskRepository taskRepository;

    public List<User> findAll() {
        List<User> users = (List<User>) repository.findAll();
        log.info("Find all employee - " + users);
        return users;
    }

    public User findOne(int id) {
        user = repository.findOne(id);
        myException(user);
        log.info("Find employee - " + user + ", by id - " + id);
        return user;
    }

    public List<User> findByAge(int age) {
        List<User> users = repository.findByAgeAfter(age);
        if (users.isEmpty()) {
            throw new MyResourceNotFoundException();
        }
        log.info("Find employee - " + users + ", by age after - " + age);
        return users;
    }

    public void deleteUserById(int id) {
        repository.delete(id);
        log.info("Delete employee by id - " + id);
    }

    public void addedUserAndDepartment() {
        repository.save(Helper.getUserList());
        departmentRepository.save(Helper.getDepartment());
        taskRepository.save(Helper.getTasks());
        log.info("Persist users, departments and tasks");
    }

    public String changeStatus(int id, Status status) {
        user = repository.findOne(id);
        myException(user);
        user.setStatus(status);
        user.setData(Helper.getCurrentData());
        repository.save(user);
        log.info("employee by id -" + id + "change status");
        return "Ok";
    }

    public List<User> findByStatus(Status status) {
        log.info("Found employees by status - " + status);
        return repository.findByStatus(status);
    }

    public String addDepartment(int id, String departmentName) {
        user = repository.findOne(id);
        myException(user);
        Department department = departmentRepository.findByDepartmentName(departmentName);
        user.setDepartment(department);
        user.setData(Helper.getCurrentData());
        repository.save(user);
        log.info("employee by id -" + id + "added department");
        return "Ok";
    }

    @Override
    public void addUser(User user) {
        log.info("Save user - " + user);
        myException(user);
        repository.save(user);
    }

    private void myException(User user) {
        if (user == null) {
            throw new MyResourceNotFoundException();
        }
    }
}
