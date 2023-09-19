package com.kh.myproject.store.flight.controller;

import com.kh.myproject.api.kakaoPay.model.dto.PaybillDto;
import com.kh.myproject.member.user.model.entity.User;
import com.kh.myproject.store.flight.model.dto.FlightTicketDto;
import com.kh.myproject.store.flight.service.FlightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@SessionAttributes("user")
@Slf4j
public class FlightController {

    @Autowired
    private FlightService flightService;
    //공항리스트
    private final String airportUrl = "https://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getArprtList?";
    //노선목록
    private final String flightOpratInfoUrl = "http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getFlightOpratInfoList?";
    private final String serviceKey = "serviceKey=ZgRTKBFIJGjeIJ14VHOZrP9UMtis8xSBTJvnPqQIigzUQ4aIL8V03y5XCVZ5B8GAKHaJX%2FOz2UpnX%2FvgKqv38w%3D%3D&";
    private PaybillDto ticketDto;


    @GetMapping("/store/flight/flights")
    public ModelAndView flightMain(@RequestParam(value = "startAirport", required = false) String startAirport,
                                   @RequestParam(value = "endAirport", required = false) String endAirport,
                                   @RequestParam(value = "startDate", required = false) String startDate,
                                   ModelAndView mav) {

        log.info("startAirport={}", startAirport);
        log.info("endAirport={}", endAirport);

        if (startAirport != null) {
            mav.addObject("startAirport", startAirport);
            mav.addObject("endAirport", endAirport);
            mav.addObject("startDate", startDate);
        }

        mav.setViewName("store/flight/flights");

        return mav;
    }

    @GetMapping("/store/flight/pay/flightPayment")
    public ModelAndView flightTest(ModelAndView mav,
                                   @ModelAttribute("user") User user) {

        log.info("ticketDto={}", ticketDto);

        mav.addObject("ticket", ticketDto); //결제페이지에서 보여줄거
        mav.setViewName("store/pay/flightPaymentPage");
        return mav;
    }

    @GetMapping("/store/flight/airportList")
    public ResponseEntity<?> getAirportList() {
        String url = airportUrl;
        url += serviceKey + "&_type=json";
        return ResponseEntity.ok(flightService.getFlightList(url));
    }

    @GetMapping("/store/flight/searchFlight")
    public ResponseEntity<?> searchFlight(@RequestParam("startAirport") String startAirport,
                                          @RequestParam("endAirport") String endAirport,
                                          @RequestParam("startDate") String startDate,
                                          @RequestParam("pageNo") int pageNo) {

        log.info("startAirport={}", startAirport);
        log.info("endAirport={}", endAirport);
        log.info("startDate={}", startDate);
        log.info("pageNo={}", pageNo);


        String url = flightOpratInfoUrl;
        url += serviceKey + "pageNo=" + pageNo + "&depAirportId=" + startAirport + "&arrAirportId=" + endAirport +
                "&depPlandTime=" + startDate.replace("-", "") + "&_type=json";


        return ResponseEntity.ok(flightService.getFlightList(url));
    }

    @PostMapping("/store/flight/reservationFlight")
    public void saveFlight(@RequestBody PaybillDto ticket,
                           @ModelAttribute("user") User user) {
        log.info("ticket={}", ticket); //티켓 정보
        log.info("user={}", user.getUserId()); //로긴한 유저 정보
        ticket.setUser(user); //dto에 유저정보 저장
        ticketDto = ticket; //전역변수에 저장

    }

    /*@GetMapping("/store/flight/remove")
    public ModelAndView removeTicket(@RequestParam("ticketId")Long ticketId,
                                     ModelAndView mav){
        flightService.removeTicket(ticketId);

        mav.setViewName("tourMain");
        return mav;
    }*/


}
