package com.kh.myproject.member.manager.service;

import com.kh.myproject.community.plan.model.entity.PlanBoard;
import com.kh.myproject.member.manager.repository.PlanDetailRepositoryM;
import com.kh.myproject.member.manager.repository.PlanRepositoryM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PlanServiceM {

    @Autowired
    PlanRepositoryM planRepositoryM;

    @Autowired
    PlanDetailRepositoryM planDetailRepositoryM;


    public void deletePlan(Long pbNum){

        planRepositoryM.deleteById(pbNum);
    }

    public int selectPlanBoardCount(){

        int count = planRepositoryM.selectPlanBoardCount();
        return count;
    }

    public List<PlanBoard> findPlanBoardByPage(int pageNo){

        Pageable pageable = PageRequest.of(pageNo-1,10);
        Page<PlanBoard> pageList = planRepositoryM.findAll(pageable);
        List<PlanBoard> planBoardList = pageList.getContent();

        return planBoardList;
    }


}
