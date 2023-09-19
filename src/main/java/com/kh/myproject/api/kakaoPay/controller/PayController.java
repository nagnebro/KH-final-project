package com.kh.myproject.api.kakaoPay.controller;

import com.kh.myproject.api.kakaoPay.model.dto.CrawlingDto;
import com.kh.myproject.api.kakaoPay.model.dto.KakaoPayApprovalVO;
import com.kh.myproject.api.kakaoPay.model.dto.KakaoPayReadyVO;
import com.kh.myproject.api.kakaoPay.model.dto.PaybillDto;
import com.kh.myproject.api.kakaoPay.service.PayService;
import com.kh.myproject.member.user.model.entity.User;
import com.kh.myproject.store.flight.model.entity.FlightTicketInfo;
import com.kh.myproject.store.flight.repository.FlightTicketRepository;
import com.kh.myproject.store.rentcar.model.RentcarInfoDTO;
import com.kh.myproject.store.rentcar.model.entity.RentReservationInfo;
import com.kh.myproject.store.rentcar.repository.RentReservationRepository;
import com.kh.myproject.store.rentcar.service.RentcarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@SessionAttributes({"tid", "user"}) // 세션에 저장된 값 탐색
public class PayController {

    @Autowired
    FlightTicketRepository flightTicketRepository;

    @Autowired
    private RentcarService rentcarService;

    @Autowired
    private RentReservationRepository rentReservationRepository;
    private PaybillDto paybillDto;

    private final PayService payService;


    @GetMapping("store/pay/payButton")
    public ModelAndView payButton() {

        ModelAndView payButton = new ModelAndView();
        payButton.setViewName("store/pay/payButton");

        return payButton;

    }

    // retcar 예약 페이지
    @GetMapping("/store/rentcar/pay/rentcarPaymentPage")
    public ModelAndView rentcarPaymentPage(@RequestParam("Car_info_id") Long Car_info_id,
                                           ModelAndView mav) {

        List<CrawlingDto> crawlList = payService.getAllCrawlingData();
        log.info("crawlList{}", crawlList);

        RentcarInfoDTO dto = rentcarService.rentcarPay(Car_info_id);

        mav.addObject("dto", dto);
        mav.addObject("crawlList", crawlList);


        mav.setViewName("store/pay/paymentPage");

        return mav;

    }


    // api 결제요청
    @PostMapping("/kakaoPay")
    // RedirectView 형식으로 html에서 카카오 api 호출시 CORS오류 (보안정책이라고 함). @ResponseBody로 POST 캡슐화 후 해결됨.
    public @ResponseBody KakaoPayReadyVO kakaoPay(@RequestBody PaybillDto billInfo,
                                                  @ModelAttribute("user") User user) {

        // 티켓에 유저정보 추가
        billInfo.setUser(user);

        // 결제 완료시 데이터 저장을 위해 전역변수에 저장
        paybillDto = billInfo;

        KakaoPayReadyVO readyResponse = payService.kakaoPayReady(billInfo);

        return readyResponse;

    }

    // api 결제 승인요청 / 완료시 데이터 저장
    @GetMapping("/pay/success")
    public ModelAndView kakaoPayCompleted(@RequestParam("pg_token") String pg_token, Model model) {
        log.info("###################### kakaoPaySuccess start");

        // 카카오 결재 요청하기
        KakaoPayApprovalVO approveResponse = payService.payApprove(pg_token);
        model.addAttribute("info", approveResponse);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("store/pay/success");

        // 결제 완료시 데이터베이스에 데이터 저장
        if (approveResponse.getQuantity() == 0) {

            paybillDto.setTid(approveResponse.getTid());
            FlightTicketInfo ticketEntity = paybillDto.toTicketEntity();
            flightTicketRepository.save(ticketEntity);

            return modelAndView;

        } else {

            paybillDto.setTid(approveResponse.getTid());
            RentReservationInfo rentEntity = paybillDto.toRentEntity();
            rentReservationRepository.save(rentEntity);

            return modelAndView;

        }
    }

    // 결제 취소시 실행 url
    @GetMapping("/pay/cancel")
    public ModelAndView payCancel() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("store/pay/cancel");

        return modelAndView;

    }

    // 결제 실패시 실행 url
    @GetMapping("/pay/fail")
    public ModelAndView payFail() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("store/pay/fail");

        return modelAndView;

    }

    /*
    //test 전용
    @GetMapping("/pay/test11")
    public ModelAndView test11(Model model) throws InterruptedException {
        List<CrawlingDto> reviews = seleniumComponent.getReviews();
        model.addAttribute("reviews", reviews);
        ModelAndView test11 = new ModelAndView();
        test11.setViewName("pay/test11");
        return test11;
    }

     */
}

//    @GetMapping("/pay/reviewCrawling")
//    public String reviewCrlw(Model model) throws Exception {
//        List<CrawlingDto> reviews = crawlingService.getReviewDatas();
//        model.addAttribute("reviews", reviews);
//
//        return "";
//    }
//}



