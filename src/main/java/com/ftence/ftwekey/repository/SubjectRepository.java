package com.ftence.ftwekey.repository;

import com.ftence.ftwekey.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Subject findByName(String name);

    List<Subject> findByCircle(int circle);

    @Query(value = "SELECT * FROM Subject ORDER BY rating DESC LIMIT 3", nativeQuery = true)
    List<Subject> getRatingRank();

    @Query(value = "SELECT * FROM Subject ORDER BY comment_cnt DESC LIMIT 3", nativeQuery = true)
    List<Subject> getReviewRank();
}
