package com.kh.myproject.store.rentcar.controller;

import com.kh.myproject.store.rentcar.model.RentcarComDTO;
import com.kh.myproject.store.rentcar.model.RentcarInfoDTO;
import com.kh.myproject.store.rentcar.model.RentcarInfoEntity;
import com.kh.myproject.store.rentcar.repository.RentcarRepository;
import com.kh.myproject.store.rentcar.service.RentcarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@Slf4j
@SessionAttributes("user")
public class RentcarController {

    @Autowired
    private RentcarService rentcarService;

    @Autowired
    private RentcarRepository rentcarRepository;


    @GetMapping("/store/rentcar/rentcarMain")
    public String rentcarMain() {

        return "store/rentcar/rentcarMain";

    }


    //메인에서 지역버튼 눌렀을 때 컨트롤러
    @GetMapping("/store/rentcar/rentcarReserve")
    public String rentcarReserve(@RequestParam String input_location,
                                 @RequestParam String depart_date,
                                 @RequestParam String arrive_date,
                                 @RequestParam String input_birth,
                                    HttpSession session) {


        System.out.println(input_location);
        System.out.println(depart_date);
        System.out.println(arrive_date);
        System.out.println(input_birth);

        session.setAttribute("input_location", input_location);
        session.setAttribute("depart_date", depart_date);
        session.setAttribute("arrive_date", arrive_date);
        session.setAttribute("input_birth", input_birth);




        return "store/rentcar/rentcarReserve";

    }




    @RequestMapping("/store/rentcar/rentcarReserve") // http://localhost:8080/store/rentcar/MainSearch
    public String sample(@RequestParam String input_location,
                         @RequestParam String depart_date,
                         @RequestParam String arrive_date,
                         @RequestParam String input_birth,
                         HttpSession session) {

        System.out.println(input_location);
        System.out.println(depart_date);
        System.out.println(arrive_date);
        System.out.println(input_birth);

        session.setAttribute("input_location", input_location);
        session.setAttribute("depart_date", depart_date);
        session.setAttribute("arrive_date", arrive_date);
        session.setAttribute("input_birth", input_birth);




        return "store/rentcar/rentcarReserve";

    }


/*

    @GetMapping("/store/rentcar/thTest")
    public String thttt(){

        return "store/rentcar/thTest";
    }

*/


    //키워드 검색결과

    @GetMapping("/rentcarReserveKeyword")
    public ResponseEntity<List<RentcarInfoDTO>> reserveSearch(@RequestParam(value = "searchKeyword") String searchKeyword) {

        System.out.println("reserveSearch 컨트롤러 메서드 실행");
        System.out.println(searchKeyword);

        List<RentcarInfoDTO> rentcarList = rentcarService.searchKeyword(searchKeyword);

        return ResponseEntity.ok(rentcarList);


    }

    //정렬기준 결과 가져오기

    @GetMapping("/highPriceList")
    public ResponseEntity<List<RentcarInfoDTO>> highPriceList() {


        List<RentcarInfoDTO> rentcarList = rentcarService.FindDiscountDesc(rentcarService.findAll());

        return ResponseEntity.ok(rentcarList);
    }


    @GetMapping("/lowPriceList")
    public ResponseEntity<List<RentcarInfoDTO>> lowPriceList() {


        List<RentcarInfoDTO> rentcarList = rentcarService.FindDiscountAsc(rentcarService.findAll());

        return ResponseEntity.ok(rentcarList);
    }

    @GetMapping("/carTypeList")
    public ResponseEntity<List<RentcarInfoDTO>> carTypeList() {


        List<RentcarInfoDTO> rentcarList = rentcarService.FindTypeAsc(rentcarService.findAll());

        return ResponseEntity.ok(rentcarList);
    }


    //필터링 버튼 결과 가져오기
    // 구분 전체
    @GetMapping("/nationall")
    public ResponseEntity<List<RentcarInfoDTO>> nationall() {


        List<RentcarInfoDTO> rentcarList = rentcarService.FindBynationAll();

        return ResponseEntity.ok(rentcarList);
    }

    // 구분 국내
    @GetMapping("/nationDomestic")
    public ResponseEntity<List<RentcarInfoDTO>> nationDomestic() {


        List<RentcarInfoDTO> rentcarList = rentcarService.FindBynationDomestic();

        return ResponseEntity.ok(rentcarList);
    }

    // 구분 해외
    @GetMapping("/nationOversea")
    public ResponseEntity<List<RentcarInfoDTO>> nationOversea() {


        List<RentcarInfoDTO> rentcarList = rentcarService.FindBynationOversea();

        return ResponseEntity.ok(rentcarList);
    }

    // 차종 전체
    @GetMapping("/FindByTypeAll")
    public ResponseEntity<List<RentcarInfoDTO>> FindByTypeAll() {


        List<RentcarInfoDTO> rentcarList = rentcarService.FindByTypeAll();

        return ResponseEntity.ok(rentcarList);
    }

    // 차종 경형
    @GetMapping("/FindByTypeSmall")
    public ResponseEntity<List<RentcarInfoDTO>> FindByTypeSmall() {


        List<RentcarInfoDTO> rentcarList = rentcarService.FindByTypeSmall();

        return ResponseEntity.ok(rentcarList);
    }

    // 차종 승용
    @GetMapping("/FindByTypeMid")
    public ResponseEntity<List<RentcarInfoDTO>> FindByTypeMid() {


        List<RentcarInfoDTO> rentcarList = rentcarService.FindByTypeMid();

        return ResponseEntity.ok(rentcarList);
    }

    // 차종 suv
    @GetMapping("/FindByTypeSuv")
    public ResponseEntity<List<RentcarInfoDTO>> FindByTypeSuv() {


        List<RentcarInfoDTO> rentcarList = rentcarService.FindByTypeSuv();

        return ResponseEntity.ok(rentcarList);
    }

    // 차종 승합
    @GetMapping("/FindByTypeRv")
    public ResponseEntity<List<RentcarInfoDTO>> FindByTypeRv() {


        List<RentcarInfoDTO> rentcarList = rentcarService.FindByTypeRv();

        return ResponseEntity.ok(rentcarList);
    }





    // 유종 전체
    @GetMapping("/FindByOilAll")
    public ResponseEntity<List<RentcarInfoDTO>> FindByOilAll() {


        List<RentcarInfoDTO> rentcarList = rentcarService.FindByOilAll();

        return ResponseEntity.ok(rentcarList);
    }



    // 유종 디젤
    @GetMapping("/FindByOilDiesel")
    public ResponseEntity<List<RentcarInfoDTO>> FindByOilDiesel() {


        List<RentcarInfoDTO> rentcarList = rentcarService.FindByOilDiesel();

        return ResponseEntity.ok(rentcarList);
    }



    // 유종 전기
    @GetMapping("/FindByOilElec")
    public ResponseEntity<List<RentcarInfoDTO>> FindByOilElec() {


        List<RentcarInfoDTO> rentcarList = rentcarService.FindByOilElec();

        return ResponseEntity.ok(rentcarList);
    }



    // 유종 가솔린
    @GetMapping("/FindByOilGasoline")
    public ResponseEntity<List<RentcarInfoDTO>> FindByOilGasoline() {


        List<RentcarInfoDTO> rentcarList = rentcarService.FindByOilGasoline();

        return ResponseEntity.ok(rentcarList);
    }



    // 유종 lpg
    @GetMapping("/FindByOilLpg")
    public ResponseEntity<List<RentcarInfoDTO>> FindByOilLpg() {


        List<RentcarInfoDTO> rentcarList = rentcarService.FindByOilLpg();

        return ResponseEntity.ok(rentcarList);
    }



    // 유종 하이브리드
    @GetMapping("/FindByOilHybrid")
    public ResponseEntity<List<RentcarInfoDTO>> FindByOilHybrid() {


        List<RentcarInfoDTO> rentcarList = rentcarService.FindByOilHybrid();

        return ResponseEntity.ok(rentcarList);
    }



    // 나이 21세 이상
    @GetMapping("/FindByAgeDown")
    public ResponseEntity<List<RentcarInfoDTO>> FindByAgeDown() {


        List<RentcarInfoDTO> rentcarList = rentcarService.FindByAgeDown();

        return ResponseEntity.ok(rentcarList);
    }


    // 나이 26세 이상
    @GetMapping("/FindByAgeUp")
    public ResponseEntity<List<RentcarInfoDTO>> FindByAgeUp() {


        List<RentcarInfoDTO> rentcarList = rentcarService.FindByAgeUp();

        return ResponseEntity.ok(rentcarList);
    }








    //렌트카 업체 리스트 가져오기
    @GetMapping("/store/rentcar/rentcarChoice")
    public String rentcarChoice(@RequestParam("car_name") String car_name,
                                @RequestParam("cartype") String cartype,
                                @RequestParam("caryear") String caryear,
                                @RequestParam("carprice") String carprice,
                                @RequestParam(value="carimg", required=false) String carimg,
                                Model model, HttpSession session) {

        List<RentcarInfoEntity> rentcarComList = rentcarService.FindCombycarname(car_name);

        System.out.println(car_name);
        System.out.println(cartype);
        System.out.println(caryear);
        System.out.println(carprice);
        System.out.println(carimg);

        session.setAttribute("cartype", cartype);
        session.setAttribute("caryear", caryear);
        session.setAttribute("carprice", carprice);
        session.setAttribute("car_name", car_name);
        session.setAttribute("carimg", carimg);
        model.addAttribute("rentcarComList",rentcarComList);


        return "store/rentcar/rentcarChoice";

    }


}
