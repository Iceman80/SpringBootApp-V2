package myapp.controller;

import myapp.model.Task;
import myapp.model.User;
import myapp.service.TaskService;
import myapp.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@CrossOrigin(origins = ("*"))
@RestController
@RequestMapping(value = "/task")
public class TaskController {

    @Autowired
    private TaskService service;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String createTask(@RequestBody Task task) {
        service.saveTask(task);
        return "Task create Ok";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Task> findAll() {
        List<Task> tasks = service.findAllTasks();
        return tasks;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Task findOne(@PathVariable("id") String id) {
        Task task= service.findTaskById(id);
        Link selfLink = linkTo(TaskController.class).slash(task.getTaskId()).withSelfRel();
        task.add(selfLink);
        return task;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteByID(@PathVariable("id") String id) {
        service.deleteTaskById(id);
        return "Delete task by Id OK";
    }

    @RequestMapping(value = "/{taskId}/user/{userId}", method = RequestMethod.POST)
    public String updateTask(@PathVariable("taskId") String taskId, @PathVariable("userId") int userId) {
        Task task = service.findTaskById(taskId);
        User user = userService.findOne(userId);
        task.getUsers().add(user);
        service.saveTask(task);
        return "Task create Ok";
    }
}


