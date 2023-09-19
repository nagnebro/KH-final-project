package com.kh.myproject.member.manager.repository;

import com.kh.myproject.member.user.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepositoryM extends JpaRepository<User, Long> {


    @Query("select count(*) from  User u where datediff(now(), u.userRegdate) = :count")

    int countByDate(@Param("count")int count);

//    @Query("")

    @Query(value = "SELECT CASE " +
            "WHEN EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM u.user_date) BETWEEN 10 AND 19 THEN 'ten' " +
            "WHEN EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM u.user_date) BETWEEN 20 AND 29 THEN 'two' " +
            "WHEN EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM u.user_date) BETWEEN 30 AND 39 THEN 'three' " +
            "WHEN EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM u.user_date) BETWEEN 40 AND 49 THEN 'four' " +
            "WHEN EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM u.user_date) BETWEEN 50 AND 59 THEN 'five' " +
            "WHEN EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM u.user_date) BETWEEN 60 AND 69 THEN 'six' " +
            "WHEN EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM u.user_date) BETWEEN 70 AND 79 THEN 'seven' " +
            "ELSE 'else' END AS age_group, COUNT(*) " +
            "FROM User u " +
            "GROUP BY age_group " +
            "ORDER BY age_group DESC", nativeQuery = true)
    List<Object[]> getUserAgeCount();


    // 페이징 처리 관련 쿼리
    @Query("select count(*) from User u")
    int selectUserCount();


    Page<User> findAll(Pageable pageable);
    int countByUserNameLike(String user_name);
    int countByUserIdLike(String user_id);


    // 검색어에 해당하는 User정보 불러온다.
    Page<User> findByUserNameLike(Pageable pageable,String user_name);
    Page<User> findByUserIdLike(Pageable pageable,String user_id);


    int countByUserGender(String gender);

    User findByUserIdAndUserPassword(String user_id, String user_password);

}