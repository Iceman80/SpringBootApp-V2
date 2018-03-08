package myapp.repository;


import myapp.model.Department;
import myapp.model.Status;
import myapp.model.User;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findByAgeAfter(int age);

    List<User> findByStatus(Status status);
}
