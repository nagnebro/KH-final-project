package com.kh.myproject.member.manager.repository;

import com.kh.myproject.community.plan.model.entity.PlanBoardDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanDetailRepositoryM extends JpaRepository<PlanBoardDetail, Long> {

    void deleteByPlanBoardPbNum(Long pb_num);
}
