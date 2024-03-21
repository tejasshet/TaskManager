package com.example.spring.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.spring.Entity.Task;
import com.example.spring.Service.TaskService;
import java.util.*;

@RestController
@RequestMapping("/v1/task")
public class TaskController {
	
	private final TaskService taskService;
	
	@Autowired
	public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
	
	@PostMapping	
    public Task createTask(@RequestBody Task task) {
        try {
            return taskService.saveTask(task);
        } catch (Exception e) {
            throw new RuntimeException("Unable to create task: " + e.getMessage());
        }
	}
	
	@GetMapping("/getTasks")
    public List<Task> getAllTasks() {
        try {
            return taskService.getAllTasks();
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve tasks: " + e.getMessage());
        }
    }
	
	
	@DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        try {
            taskService.deleteTask(taskId);
            return ResponseEntity.ok("Task with ID " + taskId + " delete	d successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Failed to delete task with ID " + taskId + ": " + e.getMessage());
        }
    }
	
	@GetMapping("/getTasks/page/{pageNumber}")
    public Map<String, Object> getTasksByPage(@PathVariable int pageNumber,
                                              @RequestParam(defaultValue = "5") int pageSize) {
        try {
            List<Task> tasks = taskService.getTasksByPage(pageNumber, pageSize);
            long totalTasks = taskService.getTotalTasks();
            Map<String, Object> response = new HashMap<>();
            response.put("tasks", tasks);
            response.put("currentPage", pageNumber);
            response.put("totalTasks", totalTasks);
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve tasks: " + e.getMessage());
        }
    }
	
	
}
