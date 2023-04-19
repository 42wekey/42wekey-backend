package com.ftence.ftwekey.repository;

import com.ftence.ftwekey.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findByName(String name);
}
