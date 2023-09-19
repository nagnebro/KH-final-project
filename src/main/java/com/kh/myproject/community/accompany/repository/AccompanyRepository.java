package com.kh.myproject.community.accompany.repository;

import com.kh.myproject.community.accompany.entity.Accompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface AccompanyRepository extends JpaRepository<Accompany, Long> {
    @Modifying
    @Transactional
    @Query(value = "update Accompany ac" +
            " set ac.ac_region = :#{#accompany.ac_region}," +
            " ac.ac_startdate = :#{#accompany.ac_startdate}," +
            " ac.ac_enddate = :#{#accompany.ac_enddate}," +
            " ac.ac_picture = :#{#accompany.ac_picture}," +
            " ac.ac_title = :#{#accompany.ac_title}," +
            " ac.ac_text = :#{#accompany.ac_text}," +
            " ac.ac_people = :#{#accompany.ac_people}" +
            " where ac.ac_num = :#{#accompany.ac_num}", nativeQuery = true)
    void updateAccompany(@Param("accompany") Accompany accompany);

    @Query("select a from Accompany a where a.ac_text LIKE :searchName OR a.ac_title LIKE :searchName ORDER BY a.ac_regdate DESC")
    List<Accompany> findByAc_textOrAc_titleOrderBOrderByAc_regdateDesc(@Param("searchName") String searchName);

    @Query("select max(a.ac_num) from Accompany a")
    int findTopByAc_num();

    @Transactional
    @Modifying
    @Query("update Accompany a set a.ac_viewcount = a.ac_viewcount+1 where a.ac_num = :ac_num")
    void increaseViewCount(@Param("ac_num") Long ac_num);

    @Query("select max(a.ac_viewcount) from Accompany a")
    int findTopByAc_viewcount();

    // 동행글을 최근 날짜별로 쿼리
    @Query("select a from Accompany a order by a.ac_viewcount desc")
    List<Accompany> findByAc_numOrderByAc_viewcountDesc();

    // 동행글을 최근 날짜별로 쿼리
    @Query("select a from Accompany a order by a.ac_regdate desc ")
    List<Accompany> findByAc_numOrderByAc_regdateDesc();

    // 조회기간 : 시작날짜와 마지막날짜 사이의 게시글을 찾는 쿼리
    @Query("SELECT a FROM Accompany a WHERE a.ac_startdate BETWEEN :startDate AND :endDate")
    List<Accompany> findByAc_startdateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // 모든지역 : 지역 선택하면 해당 지역 게시글만 나오게 쿼리
    @Query("SELECT a FROM Accompany a WHERE a.ac_region LIKE %:ac_region%")
    List<Accompany> findByAc_regionContains(String ac_region);

    //
    //    @Query("select Accompany a.*, count(Comment co.ac_num) count from accompany a join Comment co on a.ac_num = co.ac_num group by ac.ac_num order by count desc")
    @Query("SELECT a, COUNT(c.co_number) AS commentCount FROM Accompany a " +
            "LEFT JOIN Comment c ON a.ac_num = c.accompany.ac_num " +
            "GROUP BY a.ac_num " +
            "ORDER BY commentCount DESC")
    List<Accompany> findAccompanyWithCommentCount();


    /* 여기서부터 중복 쿼리 메서드 */

    /*1. 최신순, 조회기간, 모든지역*/
    @Query("select a from Accompany a  " +
            "WHERE a.ac_startdate BETWEEN :startDate AND :endDate order by a.ac_regdate")
    List<Accompany> findByAc_startdateWithinAndAc_enddateOrderByAc_regdateDesc(@Param("startDate") Date startDate, @Param("endDate") Date endDate);



    // 페이징 처리를ㄹ 위한 메서드
    @Query("select count(*) from Accompany a")
    int selectAccompanyCount();


    Page<Accompany> findAll(Pageable pageable);
}

