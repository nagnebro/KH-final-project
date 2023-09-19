package com.kh.myproject.member.manager.service;

import com.kh.myproject.store.flight.model.entity.FlightTicketInfo;
import com.kh.myproject.member.manager.repository.FlightTicketRepositoryM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FlightServiceM {

    @Autowired
    private FlightTicketRepositoryM flightRepository;




    // 여기서부터 manager에서 항공 정보를 가져오기 위한 쿼리문


    public int selectTicketCount(){

        int result = flightRepository.selectTicketCount();
        return result;
    }

    public List<FlightTicketInfo> findTicketByPage(int pageNo){

        //        flightRepository.findUserByPage(startNo,endNo);
        Pageable pageable = PageRequest.of(pageNo-1,10);
        Page<FlightTicketInfo> tickets = flightRepository.findAll(pageable);
        List<FlightTicketInfo> ticketList = tickets.getContent();


        return ticketList;
    }

    public List<FlightTicketInfo> selectTicketBySearchWord(int pageNo,String search_word,String search_option){

        Page<FlightTicketInfo> ticketList = null;
        Pageable pageable = PageRequest.of(pageNo-1,10); // 정렬기능 추가안했음.

        if(search_option.equals("ticAirlineName")){ // 항공사명으로 조회했을 떄

            ticketList = flightRepository.findByTicAirlineNameLike(pageable,"%" + search_word +"%");
        }else if(search_option.equals("user_name")){ // 예약자명으로 조회했을 떄
            ticketList = flightRepository.findByUserNameLike(pageable,"%" +search_word+"%");

        }

        List<FlightTicketInfo> resultList = ticketList.getContent();
        System.out.println("resultList" + resultList);
        return resultList;
    }

    public int countBysearchWorld(String search_word, String search_option){

        int result = 0;

        if(search_option.equals("user_name")){
            result = flightRepository.countByUserNameTLike("%" + search_word +"%");
        }else if(search_option.equals("ticAirlineName"))
            result = flightRepository.countByTicAirlineNameLike("%" + search_word +"%");

        System.out.println("countbysearchWoprld의 값 :" + result);
        return result;
    }


    public void deleteTicket(Long ticTicketId){

        flightRepository.deleteById(ticTicketId);
    }

    public List<FlightTicketInfo> findAll(){

        return flightRepository.findAll();
    }

    public void deleteTicketByUserNumber(Long user_number){

        flightRepository.deleteByUserNumber(user_number);
    }


}
