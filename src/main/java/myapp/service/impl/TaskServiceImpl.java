package myapp.service.impl;


import myapp.exception.MyResourceNotFoundException;
import myapp.model.Task;
import myapp.repository.TaskRepository;
import myapp.service.TaskService;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private final static Logger log = LogManager.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskRepository repository;

    @Override
    public List<Task> findAllTasks() {
        log.info("Find all tasks");
        List<Task> tasks = (List<Task>) repository.findAll();
        if (tasks.isEmpty()) {
            throw new MyResourceNotFoundException();
        }
        return tasks;
    }

    @Override
    public void saveTask(Task task) {
        log.info("Save task - " + task.getDescription());
        myException(task);
        repository.save(task);
    }

    @Override
    public Task findTaskById(String taskId) {
        log.info("Find task by Id - " + taskId);
        Task task = repository.findOne(taskId);
        myException(task);
        return task;
    }

    @Override
    public void deleteTaskById(String taskId) {
        log.info("Delete Task by id - " + taskId);
        repository.delete(taskId);
    }

    private void myException(Task task) {
        if (task == null) {
            throw new MyResourceNotFoundException();
        }
    }
}
