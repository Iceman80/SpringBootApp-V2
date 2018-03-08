package myapp.service;


import myapp.model.Department;
import myapp.model.Task;
import myapp.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Helper {

    public static List<User> getUserList() {
        List<User> users = new ArrayList<>();
        users.add(new User("Serhii", "Petrov", 47, "066-100-82-92"));
        users.add(new User("Alex", "Ivanov", 27, "066-100-82-93"));
        users.add(new User("Petro", "Petrov", 22, "067-111-82-94"));
        return users;
    }

    public static String getCurrentData() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd - hh.mm.ss");
        return sdf.format(date);
    }

    public static List<Department> getDepartment() {
        List<Department> departments = new ArrayList<>();
        departments.add(new Department("It"));
        departments.add(new Department("Security"));
        departments.add(new Department("Financial"));
        return departments;
    }

    public static List<Task> getTasks(){
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Fix Bug"));
        tasks.add(new Task("Prepare report"));
        tasks.add(new Task("Hard work"));
        return tasks;
    }
}
