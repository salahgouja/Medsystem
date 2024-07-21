package com.salah.service;

import com.salah.dto.TaskDTO;
import com.salah.doctor.Doctor;
import com.salah.entity.Task;
import com.salah.exception.TaskNotFoundException;
import com.salah.repository.TaskRepository;

import com.salah.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public List<TaskDTO> getTasksByDoctor(Long doctorId) {
        Doctor doctor = userService.getDoctorById(doctorId);
        return taskRepository.findByDoctor(doctor).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO addTask(TaskDTO taskDTO) {
        Task task = new Task();
        mapToEntity(taskDTO, task);
        Doctor doctor = userService.getDoctorById(taskDTO.getDoctorId());
        task.setDoctor(doctor);
        Task savedTask = taskRepository.save(task);
        return mapToDTO(savedTask);
    }
    public TaskDTO updateTask(Long taskId, TaskDTO taskDTO) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        mapToEntity(taskDTO, task);
        Task updatedTask = taskRepository.save(task);
        return mapToDTO(updatedTask);
    }


    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
    private TaskDTO mapToDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getName(),
                task.getPrice(),
                task.getStatus(),
                task.getDoctor().getId()
        );
    }

    private void mapToEntity(TaskDTO taskDTO, Task task) {
        task.setName(taskDTO.getName());
        task.setPrice(taskDTO.getPrice());
        task.setStatus(taskDTO.getStatus());
    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

}
