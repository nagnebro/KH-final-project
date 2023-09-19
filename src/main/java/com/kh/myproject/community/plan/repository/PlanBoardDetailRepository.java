package com.kh.myproject.community.plan.repository;

import com.kh.myproject.community.plan.model.entity.PlanBoardDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PlanBoardDetailRepository extends JpaRepository<PlanBoardDetail, Long> {
    // 필요한 경우 사용자 정의 쿼리 메서드를 여기에 정의할 수 있습니다.

    List<PlanBoardDetail> findByPlanBoard_pbNum(Long pbNum);
    @Query("select max(pbd.pbdDate) from PlanBoardDetail pbd where pbd.planBoard.pbNum = :pbNum")
    int getMaxByPbNum(@Param("pbNum") Long pbNum);

    @Modifying
    @Transactional
    void deleteAllByPlanBoard_pbNum(@Param("pbNum") Long pbNum);

}
