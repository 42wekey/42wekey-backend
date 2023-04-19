package com.ftence.ftwekey.repository;

import com.ftence.ftwekey.entity.Comment;
import com.ftence.ftwekey.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findBySubject(Subject subject);
}
