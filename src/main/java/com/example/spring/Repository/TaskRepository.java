package com.example.spring.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.spring.Entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
     
}
