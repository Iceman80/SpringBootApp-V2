package myapp.service;

import myapp.model.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAllTasks();

    void saveTask(Task task);

    Task findTaskById (String taskId);

    void deleteTaskById (String taskId);
}
