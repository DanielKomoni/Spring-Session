package com.example.demo.dbService;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
    public String findPasswordByUsername(String username);
}
