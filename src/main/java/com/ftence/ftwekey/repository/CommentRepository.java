package com.ftence.ftwekey.repository;

import com.ftence.ftwekey.entity.Comment;
import com.ftence.ftwekey.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findBySubject(Subject subject);

    @Query(value = "SELECT * FROM Comment order by create_time DESC limit 5", nativeQuery = true)
    List<Comment> getRecentComment();

    @Query(value = "SELECT COUNT(*) FROM Comment WHERE Comment.user_id=:user", nativeQuery = true)
    int getUserCommentCnt(@Param("user") Long user);
}
