package com.kh.myproject.member.user.repository;

import com.kh.myproject.community.accompany.entity.Accompany;
import com.kh.myproject.community.plan.model.entity.PlanBoard;
import com.kh.myproject.member.user.model.entity.User;
import com.kh.myproject.store.flight.model.entity.FlightTicketInfo;
import com.kh.myproject.store.rentcar.model.entity.RentReservationInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {



        @Query("SELECT u FROM User u WHERE u.userId = :user_id")
    // camel case -> snake case auto change
    User findByUserId(@Param("user_id") String user_id);

    User findByUserIdAndUserPassword(@Param("user_id") String user_id,
                                     @Param("user_password") String user_password);

    @Modifying
    @Transactional
    @Query("update User u " +
            "set u.userPassword = :new_pw1 " +
            "where u.userNumber = :user_number")
    int updatePw(@Param("user_number")Long user_number, @Param("new_pw1")String new_pw1);


    @Modifying
    @Transactional
    @Query("update User u" +
            " set u.userName = :#{#user.userName} ," +
            " u.userDate = :#{#user.userDate}," +
            " u.userGender = :#{#user.userGender}," +
            " u.userPassword = :#{#user.userPassword}" +
            " where u.userNumber = :#{#user.userNumber}")
    void updateUser(@Param("user")User user);


    User findByUserNameAndUserPhone(@Param("user_name")String user_name,
                                    @Param("user_phone1")String user_phone1);


    User findByUserIdAndUserPhone(@Param("user_id")String user_id,
                                  @Param("user_phone2")String user_phone2);

    List<User> findTop5ByOrderByUserDateDesc();


//    @Query("SELECT " +
//            "   (SELECT COUNT(u.userId) FROM User u WHERE DATEDIFF(NOW(), u.userRegdate) = 0) AS day0, " +
//            "   (SELECT COUNT(u.userId) FROM User u WHERE DATEDIFF(NOW(), u.userRegdate) = 1) AS day1, " +
//            "   (SELECT COUNT(u.userId) FROM User u WHERE DATEDIFF(NOW(), u.userRegdate) = 2) AS day2, " +
//            "   (SELECT COUNT(u.userId) FROM User u WHERE DATEDIFF(NOW(), u.userRegdate) = 3) AS day3, " +
//            "   (SELECT COUNT(u.userId) FROM User u WHERE DATEDIFF(NOW(), u.userRegdate) = 4) AS day4 ")
//    List<Long> countUsersByDateDiff();

    //select count(*) from user where datediff( now(), user_regdate) < 4 group by datediff(now(), user_regdate);

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

    @Query("select count(*) from User u")
    int selectUserCount();

    @Query("select u from User u order by u.userRegdate desc")
    int findUserByPage(int startNo, int endNo);

    Page<User> findAll(Pageable pageable);


//    @Query("select u from User u where u.userId = :user_id order by u.userRegdate desc")
//    List<User> selectByUserId(@Param("user_id")String user_id);
//
//    @Query("select u from User u where u.userName = :user_name order by u.userRegdate desc")
//    List<User> selectByUserName(@Param("user_name")String user_name);


    Page<User> findByUserNameLike(Pageable pageable,String user_name);
    Page<User> findByUserIdLike(Pageable pageable,String user_id);

    int countByUserNameLike(String user_name);
    int countByUserIdLike(String user_id);

    @Query("SELECT f FROM FlightTicketInfo f WHERE f.user.userNumber = :user_number")
    List<FlightTicketInfo> getFticketByNum(@Param("user_number") Long user_number);

    @Query("SELECT r FROM RentReservationInfo r WHERE r.user.userNumber = :user_number")
    List<RentReservationInfo> getRticketByNum(@Param("user_number") Long user_number);

    @Query("SELECT a FROM Accompany a WHERE a.user.userNumber = :user_number")
    List<Accompany> getAccompanyByNum(@Param("user_number") Long user_number);
    int countByUserGender(String gender);

    @Query("SELECT p FROM PlanBoard p WHERE p.user.userNumber = :user_number")
    List<PlanBoard> getPlanByNum(@Param("user_number") Long user_number);


}