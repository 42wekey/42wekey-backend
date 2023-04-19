package com.ftence.ftwekey.repository;


import com.ftence.ftwekey.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByIntraId(String intraId);

    User findByUniqueId(Long uniqueId);

}
