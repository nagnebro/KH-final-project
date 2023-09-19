package com.kh.myproject.member.manager.repository;

import com.kh.myproject.member.manager.model.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ManagerRepository extends JpaRepository<Manager, Long> {



    Manager findByManagerIdAndManagerPassword(
            @Param("user_id") String manager_id,
            @Param("user_password") String manager_password
    );

}
