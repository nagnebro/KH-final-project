package com.kh.myproject.community.home.controller;

import com.kh.myproject.community.accompany.entity.Accompany;
import com.kh.myproject.community.home.service.HomeService;
import com.kh.myproject.community.plan.model.dto.PlanBoardDetailDTO;
import com.kh.myproject.community.plan.model.entity.PlanBoard;
import com.kh.myproject.community.plan.service.PlanBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;
@Controller
@SessionAttributes("user")
public class HomeController {

    @Autowired
    HomeService homeService;

    @Autowired
    PlanBoardService planBoardService;



    @GetMapping("/community/home")
    public String communityHome(Model model){


        List<PlanBoard> planList = planBoardService.getPlanBoardList(8); // 일정개수만 가지고 온다.

        System.out.println("planBoardList다" + planList);

        List<PlanBoardDetailDTO> planDetailList = planBoardService.getAllPlanBoardDetailList();
        List<Integer> maxDays = new ArrayList<>();
        for(int i = 0; i < planList.size(); i++){
            int maxDay = planBoardService.getMaxByPbNum(planList.get(i).getPbNum());
            System.out.println("maxDay="+maxDay);
            maxDays.add(maxDay);
        }
        List<Accompany> accompanyRecent = homeService.findTop8byOrderByRegdateAsc();




        model.addAttribute("maxDays", maxDays);
        model.addAttribute("planList", planList);
        model.addAttribute("planDetailList", planDetailList);
        model.addAttribute("accompanyList", accompanyRecent);


        return "community/home";
    }

}
