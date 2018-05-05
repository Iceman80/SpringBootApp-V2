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
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service("employeeService")
public class EmployeeServiceImpl implements UserService {
    private final static Logger log = LogManager.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<User> findAll() {
        List<User> users = (List<User>) repository.findAll();
        log.info("Find all employee -" + users);
        return Optional.of(users).orElseThrow(MyResourceNotFoundException::new);
    }

    public User findOne(int id) {
        return repository.findById(id).stream().
                findAny().
                orElseThrow(MyResourceNotFoundException::new);
    }

    public List<User> findByAge(int age) {
        List<User> users = repository.findByAgeAfter(age);
        log.info("Find employee - " + users + ", by age after - " + age);
        return Optional.of(users).orElseThrow(MyResourceNotFoundException::new);
    }

    public void deleteUserById(int id) {
        repository.deleteById(id);
        log.info("Delete employee by id - " + id);
    }

    public void addedUserAndDepartment() {
//        repository.saveAll(Helper.getUserList());
//        departmentRepository.saveAll(Helper.getDepartment());
//        taskRepository.saveAll(Helper.getTasks());
        log.info("Persist users, departments and tasks");
    }

    public String changeStatus(int id, Status status) {
        User user = Optional.of(repository.findById(id)).
                get().
                orElseThrow(MyResourceNotFoundException::new);
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
        User user = Optional.of(repository.findById(id)).
                get().
                orElseThrow(MyResourceNotFoundException::new);
        Department department = ofNullable(departmentRepository.findByDepartmentName(departmentName)).
                orElseThrow(MyResourceNotFoundException::new);
        user.setDepartment(department);
        user.setData(Helper.getCurrentData());
        repository.save(user);
        log.info("Employee by id -" + id + "added department");
        return "Ok";
    }

    @Override
    public void addUser(User user) {
        log.info("Save user - " + user);
        ofNullable(user).orElseThrow(MyResourceNotFoundException::new);
        repository.save(user);
    }
}
