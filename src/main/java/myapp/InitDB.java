package myapp;


import myapp.service.UserService;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class InitDB {

    @Autowired
    @Qualifier("employeeService")
    private UserService service;

    @PostConstruct
    public void initDB() {
        if (service.findAll().isEmpty()) {
            service.addedUserAndDepartment();
        }
    }
}
