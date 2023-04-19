package com.ftence.ftwekey.repository;


import com.ftence.ftwekey.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByIntraId(String intraId);

}
