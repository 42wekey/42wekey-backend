package com.ftence.ftwekey.repository;

import com.ftence.ftwekey.entity.Comment;
import com.ftence.ftwekey.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findBySubject(Subject subject);

    @Query(value = "SELECT * FROM Comment order by create_time DESC limit 5", nativeQuery = true)
    List<Comment> getRecentComment();
}
