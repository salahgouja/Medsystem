package com.salah.controller;

import com.salah.dto.TaskDTO;
import com.salah.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/doctor/{doctorId}")
    public List<TaskDTO> getTasksByDoctor(@PathVariable Long doctorId) {
        return taskService.getTasksByDoctor(doctorId);
    }

    @PostMapping
    public TaskDTO addTask(@RequestBody @Valid TaskDTO taskDTO) {
        return taskService.addTask(taskDTO);
    }

    @PutMapping("/{taskId}")
    public TaskDTO updateTask(@PathVariable Long taskId, @RequestBody @Valid TaskDTO taskDTO) {
        return taskService.updateTask(taskId, taskDTO);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }
}