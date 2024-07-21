package com.salah.controller;

import com.salah.dto.TaskDTO;
import com.salah.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/tasks")
@PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin:read', 'doctor:read')")
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'doctor:read')")

    public List<TaskDTO> getTasksByDoctor(@PathVariable Long doctorId) {
        return taskService.getTasksByDoctor(doctorId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin:create', 'doctor:create')")
    public TaskDTO addTask(@RequestBody @Valid TaskDTO taskDTO) {
        return taskService.addTask(taskDTO);
    }

    @PutMapping("/{taskId}")
    @PreAuthorize("hasAnyAuthority('admin:update', 'doctor:update')")

    public TaskDTO updateTask(@PathVariable Long taskId, @RequestBody @Valid TaskDTO taskDTO) {
        return taskService.updateTask(taskId, taskDTO);
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasAnyAuthority('admin:delete', 'doctor:delete')")

    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }
}