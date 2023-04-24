package com.ftence.ftwekey.repository;

import com.ftence.ftwekey.entity.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {


    @Query(value = "SELECT * FROM Heart WHERE Heart.comment_id=:comment AND Heart.user_id=:user", nativeQuery = true)
    List<Heart> getUserLikedThisComment (@Param("comment") Long comment, @Param("user") Long user);

}

