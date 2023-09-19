package com.kh.myproject.community.plan.controller;


import com.kh.myproject.community.plan.model.dto.PlanBoardDTO;
import com.kh.myproject.community.plan.model.dto.PlanBoardDetailDTO;
import com.kh.myproject.community.plan.model.entity.PlanBoard;
import com.kh.myproject.community.plan.model.entity.PlanBoardDetail;
import com.kh.myproject.community.plan.repository.PlanBoardDetailRepository;
import com.kh.myproject.community.plan.repository.PlanBoardRepository;
import com.kh.myproject.community.plan.service.PlanBoardService;
import com.kh.myproject.member.user.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.lang.Integer.parseInt;

@Controller
//@RestController
@SessionAttributes("user")
@Slf4j
public class PlanController {


    @Autowired
    PlanBoardService planBoardService;

//    private Map<Integer, ArrayList<PlanBoardDetailDTO>> planMap = new HashMap<>();
    private List<PlanBoardDetailDTO> planDetailTemporalList = new ArrayList<>(); //컨트롤러 안에서만 쓰는 전역변수

    //일정 리스트(일정 메인)
    @GetMapping("/community/plan") // http://localhost:8080/community/plan
    public String communityplan(Model model) {

        planDetailTemporalList.clear();

        // 1. 서비스한테 게시글 목록 요청

        List<PlanBoardDTO> planList = planBoardService.getAllPlanBoardList();
        model.addAttribute("planList", planList);
        System.out.println(planList);

        List<PlanBoardDetailDTO> planDetailList = planBoardService.getAllPlanBoardDetailList();
        model.addAttribute("planDetailList", planDetailList);
        System.out.println(planDetailList);

        List<Integer> maxDays = new ArrayList<>();
        for(int i = 0; i < planList.size(); i++){
            int maxDay = planBoardService.getMaxByPbNum(planList.get(i).getPbNum());
            System.out.println("maxDay="+maxDay);
            maxDays.add(maxDay);
        }
        model.addAttribute("maxDays", maxDays);
        return "community/plan/plan";
    }

    //일정 글 정보
    @GetMapping("/community/plan/detail") // http://localhost:8080/community/plan/detail
    public String communityplandetail(@RequestParam("pbNum") Long pbNum, Model model) {

        PlanBoardDTO planBoardDTO = planBoardService.getOnePlanBoard(pbNum);
//        planBoardDTO.setUser(user); //일정 담기 시 에도 추가
//        planBoardService.savePlanBoard(planBoardDTO); //일정 담기 시에도 추가
        int maxDay = planBoardService.getMaxByPbNum(pbNum);

        if(planDetailTemporalList.isEmpty()){
            List<PlanBoardDetailDTO> planBoardDetailDTOList = planBoardService.getAllPlanBoardDetailByPbNum(pbNum);
            planDetailTemporalList = planBoardDetailDTOList;

        } else {

            for(int i = 0; i < planDetailTemporalList.size(); i++){
                if(planDetailTemporalList.get(i).getPbdDate() > maxDay){
                    maxDay = planDetailTemporalList.get(i).getPbdDate();
                }
            }
        }

        //조회수 나중에 추가


        log.info("board={}", planBoardDTO);
        log.info("maxDay={}", maxDay);

        model.addAttribute("plan", planBoardDTO);
        model.addAttribute("planDetailList", planDetailTemporalList);
        model.addAttribute("maxDay", maxDay);

        return "community/plan/plan_detail";
    }

    //일정 글 쓰기
    @GetMapping("/community/plan/write") // http://localhost:8080/community/plan/write
    public String communityplanwrite(Model model, @ModelAttribute("user")User user, @RequestParam(value = "option", required = false) String option) {

        model.addAttribute(user);

        if(option == null){
            planDetailTemporalList.clear();
        }

        System.out.println(planDetailTemporalList);
        int maxDay = 0;
        if (!planDetailTemporalList.isEmpty()) {
            model.addAttribute("planList", planDetailTemporalList);
            for(int i = 0; i < planDetailTemporalList.size(); i++){
                if(planDetailTemporalList.get(i).getPbdDate() > maxDay){
                    maxDay = planDetailTemporalList.get(i).getPbdDate();
                }
            }
            model.addAttribute("maxDay", maxDay);
        }

        System.out.println("maxDay = " + maxDay);
        return "community/plan/plan_write";

    }

    //일정 글쓰기 - 장소추가
    @GetMapping("/community/plan/add") // http://localhost:8080/community/plan/add
    public String communityplanadd(@RequestParam("day") int day, @RequestParam("type") String type,
                                   Model model) {
        model.addAttribute("day", day);
        model.addAttribute("type", type);

        return "community/plan/plan_add";
    }


    //일정 글쓰기 - 장소추가(Plan_add)에서 일정 글 쓰기(Plan_write)로 값을 보낼때 중간에 거치는 컨트롤러
    @PostMapping("/community/plan/move")
    @ResponseBody
    public void move(@RequestBody PlanBoardDetailDTO[] dtoList) {

        Collections.addAll(planDetailTemporalList, dtoList);
    }

    //일정 삭제하는 컨트롤러(Plan_write에서 삭제버튼 클릭 시)
    @PostMapping("/community/plan/deletePlan")
    @ResponseBody
    public void deletePlan(@RequestParam("day") String day, @RequestParam("placeName") String placeName){

        for(int i = 0; i< planDetailTemporalList.size(); i++ ){

            if(planDetailTemporalList.get(i).getPbdPlaceName().equals(placeName) && planDetailTemporalList.get(i).getPbdDate() == parseInt(day)){
                planDetailTemporalList.remove(i);
            }
        }
    }


    //Plan_write에서 작성 완료 버튼 클릭 시 Plan에 추가
    @PostMapping("/community/plan/completePlan")
    @ResponseBody
    public String completePlan(@RequestBody PlanBoardDTO boardDTO,
                               @ModelAttribute("user") User user) {
        // 1. Dto user 정보 저장
        boardDTO.setUser(user);

        if(boardDTO.getPbTitle().equals("")){
            boardDTO.setPbTitle(user.getUserName()+"님의 여행일정");
        }

        // 2. service에서 db에 저장할때 필요한거 다 넘겨줌
        planBoardService.savePlanBoard(boardDTO, planDetailTemporalList);

        // 9. 저장이 완료되면 planList 초기화
        planDetailTemporalList.clear();

        return "community/plan/plan";
    }

    //Plan_Detail 에서 수정 완료 버튼 클릭 시 Plan에 추가
    @PostMapping("/community/plan/completeUpdatePlan")
    @ResponseBody
    public String completeUpdatePlan(@RequestBody PlanBoardDTO boardDTO,
                                     @ModelAttribute("user") User user){

        // 1. Dto user 정보 저장
        boardDTO.setUser(user);

        planBoardService.deleteAllPlanBoardDetail(boardDTO.getPbNum());
        /*planBoardService.updatePlanBoard(boardDTO, planDetailTemporalList);*/
        planBoardService.savePlanBoard(boardDTO, planDetailTemporalList);
        /*planBoardService.deleteBoard(boardDTO.getPbNum());*/

        planDetailTemporalList.clear();

        return "redirect:community/plan/plan";
    }

    // 일정 삭제 하기
    @PostMapping("/community/plan/completeDeletePlan")
    @ResponseBody
    public String completeDeletePlan(@RequestBody PlanBoardDTO boardDTO,
                                     @ModelAttribute("user") User user){

        planBoardService.deleteBoard(boardDTO.getPbNum());
        return "community/plan/plan";
    }





}








