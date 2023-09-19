package com.kh.myproject.member.user.repository;

import com.kh.myproject.community.plan.model.dto.PlanBoardDetailDTO;
import com.kh.myproject.community.plan.model.entity.PlanBoardDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardDetailRepository extends JpaRepository<PlanBoardDetail, Long> {


}
