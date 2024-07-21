package com.salah.repository;

import com.salah.doctor.Doctor;
import com.salah.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByDoctor(Doctor doctor);

}
