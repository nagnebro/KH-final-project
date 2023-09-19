package com.kh.myproject.store.flight.service;

import com.kh.myproject.member.user.model.entity.User;
import com.kh.myproject.store.flight.model.entity.FlightTicketInfo;
import com.kh.myproject.store.flight.repository.FlightTicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
@Slf4j
public class FlightService {

    @Autowired
    private FlightTicketRepository flightRepository;

    public void saveFlight(FlightTicketInfo TicketInfo){

        flightRepository.save(TicketInfo);
    }

    public List<FlightTicketInfo> getTicketList(Long userNum){
        return flightRepository.findByUser_UserNumber(userNum);
    }

    public void removeTicket(Long ticketId){
        flightRepository.deleteById(ticketId);
    }
    public String getFlightList(String url){

        StringBuilder result = new StringBuilder();

        try{
            URL resUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) resUrl.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            while((line = br.readLine()) != null){
                result.append(line);
            }
            br.close();
            conn.disconnect();

        } catch(Exception e){
            e.printStackTrace();
        }

        return result.toString();
    }
    
    
    // 여기서부터 manager에서 항공 정보를 가져오기 위한 쿼리문
    
    


}
