package com.kh.myproject.community.home.repository;

import com.kh.myproject.community.accompany.entity.Accompany;
import com.kh.myproject.member.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomeRepository extends JpaRepository<User, Long> {

    @Query("SELECT a FROM Accompany a order by a.ac_regdate asc")
    List<Accompany> findTop8byOrderByRegdateAsc();
}
