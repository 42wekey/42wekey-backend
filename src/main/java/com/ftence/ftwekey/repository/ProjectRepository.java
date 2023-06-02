package com.ftence.ftwekey.repository;

import com.ftence.ftwekey.entity.Comment;
import com.ftence.ftwekey.entity.Project;
import com.ftence.ftwekey.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByUser(User user);

}
