package myapp.service.impl;


import myapp.exception.MyResourceNotFoundException;
import myapp.model.Task;
import myapp.repository.TaskRepository;
import myapp.service.Helper;
import myapp.service.TaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
public class TaskServiceImpl implements TaskService {
    private final static Logger log = LogManager.getLogger(TaskServiceImpl.class);
    private final TaskRepository repository;

    @Autowired
    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Task> findAllTasks() {
        log.info("Find all tasks");
        List<Task> tasks = (List<Task>) repository.findAll();
        return Optional.of(tasks).orElseThrow(MyResourceNotFoundException::new);
    }

    @Override
    public void saveTask(Task task) {
        ofNullable(task).orElseThrow(MyResourceNotFoundException::new);
        log.info("Save task - " + task.getDescription());
        repository.save(task);
    }

    @Override
    public Task findTaskById(int id) {
        log.info("Find task by Id - " + id);
        return repository.findById(id).stream().
                findAny().
                orElseThrow(MyResourceNotFoundException::new);
    }

    @Override
    public void deleteTaskById(int taskId) {
        log.info("Delete Task by id - " + taskId);
        repository.deleteById(taskId);
    }
}
