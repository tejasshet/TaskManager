package com.example.spring.Service;
import java.util.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.example.spring.Entity.Task;
import com.example.spring.Repository.TaskRepository;

@Service
public class TaskService {
	
	private final TaskRepository taskRepository;
	
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	public Task saveTask(Task task) {
        try {
            return taskRepository.save(task);
        } catch (Exception e) {
            throw new RuntimeException("Unable to save task: " + e.getMessage());
        }
    }	

	public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
	
	
	public void deleteTask(Long taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            taskRepository.deleteById(taskId);
        } else {
            throw new RuntimeException("Task with ID " + taskId + " not found.");
        }
    }
	
	public List<Task> getTasksByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Task> page = taskRepository.findAll(pageable);
        return page.getContent();
    }
	
	public long getTotalTasks() {
        return taskRepository.count();
    }
	
}
