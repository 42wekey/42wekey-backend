package com.ftence.ftwekey.repository;

import com.ftence.ftwekey.entity.Wiki;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WikiRepository extends JpaRepository<Wiki, Long> {

    @Query(value = "SELECT MAX(version) FROM Wiki WHERE subject_id=:subject", nativeQuery = true)
    Long findLastVersionOfSubject(@Param("subject") Long subject);

    @Query(value = "SELECT id FROM Wiki WHERE subject_id=:subject AND version=:version", nativeQuery = true)
    Long findSubjectWikiId(@Param("subject") Long subject, @Param("version") Long version);


    @Query(value = "SELECT MAX(id) FROM Wiki", nativeQuery = true)
    Long getMaxId();


}
