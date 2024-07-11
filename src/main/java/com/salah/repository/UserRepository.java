package com.salah.repository;

import com.salah.entity.User;
import com.salah.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByFirstname(String firstname);
    List<User> findByLastname(String lastname);
    Optional<User> findByEmail(String email);
    Optional<User> findByRole(UserRole role);
}
