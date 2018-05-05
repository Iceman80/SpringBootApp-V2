package myapp.repository;


import myapp.model.Task;

import org.springframework.data.repository.CrudRepository;

public interface TaskRepository  extends CrudRepository<Task, Integer>{
}
