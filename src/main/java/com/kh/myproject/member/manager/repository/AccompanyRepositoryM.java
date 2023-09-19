package com.kh.myproject.member.manager.repository;

import com.kh.myproject.community.accompany.entity.Accompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccompanyRepositoryM extends JpaRepository<Accompany,Long> {


//    @Modifying
//    @Transactional
//    void deleteAccompanyByUserUserNumber(Long userNumber);


    @Query("select count(*) from Accompany a")
    int selectAccompanyCount();

    @Query("select a from Accompany a order by a.ac_regdate desc")
    Page<Accompany> findAll(Pageable pageable);
//    int countByAc_titleLike(String user_name);
//    int countByAc(String user_id);

}
