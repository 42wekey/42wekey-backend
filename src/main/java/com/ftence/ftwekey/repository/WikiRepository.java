package com.ftence.ftwekey.repository;

import com.ftence.ftwekey.entity.Comment;
import com.ftence.ftwekey.entity.Subject;
import com.ftence.ftwekey.entity.Wiki;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WikiRepository extends JpaRepository<Wiki, Long> {

    @Query(value = "SELECT MAX(id) FROM Wiki", nativeQuery = true)
    Long getMaxId();

    List<Wiki> findBySubject(Subject subject);
}
