package com.ftence.ftwekey.repository;

import com.ftence.ftwekey.entity.Comment;
import com.ftence.ftwekey.entity.Heart;
import com.ftence.ftwekey.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    List<Heart> findByComment(Comment comment);

    @Query(value = "SELECT * FROM Heart WHERE Heart.comment_id=:comment AND Heart.user_id=:user", nativeQuery = true)
    List<Heart> getUserLikedThisComment (@Param("comment") Long comment, @Param("user") Long user);

    @Query(value = "SELECT COUNT(*) FROM Heart WHERE Heart.user_id=:user", nativeQuery = true)
    int getUserLikesCnt(@Param("user") Long user);

    @Query(value = "SELECT * FROM Heart WHERE Heart.user_id=:user", nativeQuery = true)
    List<Heart> getUserLikeComments(@Param("user") Long user);
}

