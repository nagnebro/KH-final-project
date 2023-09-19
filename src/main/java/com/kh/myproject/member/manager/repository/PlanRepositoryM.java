package com.kh.myproject.member.manager.repository;

import com.kh.myproject.community.plan.model.entity.PlanBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface PlanRepositoryM extends JpaRepository<PlanBoard, Long> {
    List<PlanBoard> findAllByOrderByPbNumAsc();

    List<PlanBoard> findByUserUserNumber(Long userNumber);

    @Modifying
    @Transactional
    void deleteByUserUserNumber(Long userNumber);


    @Query("select count(*) from PlanBoard p")
    int selectPlanBoardCount();


    Page<PlanBoard> findAll(Pageable pageable);
//    int countByUserNameLike(String user_name);
//    int countByUserIdLike(String user_id);
//
//
//    // 검색어에 해당하는 User정보 불러온다.
//    Page<User> findByUserNameLike(Pageable pageable,String user_name);
//    Page<User> findByUserIdLike(Pageable pageable,String user_id);
//

}
