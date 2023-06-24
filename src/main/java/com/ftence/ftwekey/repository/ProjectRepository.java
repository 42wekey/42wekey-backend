package com.ftence.ftwekey.repository;

import com.ftence.ftwekey.entity.Project;
import com.ftence.ftwekey.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByUser(User user);
}
