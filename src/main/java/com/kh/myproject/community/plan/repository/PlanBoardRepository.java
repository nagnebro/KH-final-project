package com.kh.myproject.community.plan.repository;

import com.kh.myproject.community.plan.model.entity.PlanBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PlanBoardRepository extends JpaRepository<PlanBoard, Long> {


    // 필요한 경우 사용자 정의 쿼리 메서드를 여기에 정의할 수 있습니다.
    PlanBoard findByPbNum(Long pbNum);
    List<PlanBoard> findAllByOrderByPbNumDesc();


    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update plan_board pb " +
            "set pb.pb_end_date = :#{#planBoard.pbEndDate}," +
            "pb.pb_start_date = :#{#planBoard.pbStartDate}," +
            "pb.pb_region = :#{#planBoard.pbRegion}," +
            "pb.pb_title = :#{#planBoard.pbTitle} " +
            "where pb.pb_num = :#{#planBoard.pbNum}", nativeQuery = true)
    void updatePlanBoardByPbNum(@Param("planBoard") PlanBoard planBoard);

    Page<PlanBoard> findAll(Pageable pageable);

}

