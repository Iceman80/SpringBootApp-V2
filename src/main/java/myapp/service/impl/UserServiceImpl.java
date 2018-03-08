package myapp.service.impl;

import myapp.model.Status;
import myapp.model.User;
import myapp.repository.UserRepository;
import myapp.service.Helper;
import myapp.service.UserService;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    private final static Logger log = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        Iterable<User> users = repository.findAll();
        log.info("Find all users - " + users);
        return (List<User>) users;
    }

    public User findOne(int id) {
        User user = repository.findOne(id);
        log.info("Find user - " + user + ", by id - " + id);
        return user;
    }

    public List<User> findByAge(int age) {
        Iterable<User> users = repository.findByAgeAfter(age);
        log.info("Find users - " + users + ", by age after - " + age);
        return (List<User>) users;
    }

    public void deleteUserById(int id) {
        repository.delete(id);
        log.info("Delete user by id - " + id);
    }

    public void addedUserAndDepartment() {
        repository.save(Helper.getUserList());
        log.info("Persist new users");
    }

    public String changeStatus(int id, Status status) {
        User user = repository.findOne(id);
        if (user != null) {
            user.setStatus(status);
            user.setData(Helper.getCurrentData());
            repository.save(user);
            log.info("User by id -" + id + "change status");
            return "Ok";
        } else {
            String message = "Error user by id - " + id + " not found";
            log.info(message);
            return message;
        }
    }

    public List<User> findByStatus(Status status){
        log.info("Found users by status - "+ status);
        return repository.findByStatus(status);
    }

    @Override
    public String addDepartment(int id, String department) {
        return null;
    }

    @Override
    public void addUser(User user) {

    }
}
