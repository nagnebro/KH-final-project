package com.kh.myproject.member.manager.service;

import com.kh.myproject.member.manager.repository.RentRepositoryM;
import com.kh.myproject.store.rentcar.model.entity.RentReservationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RentServiceM {
    @Autowired
    private RentRepositoryM rentRepositoryM;

    public List<RentReservationInfo> getAllRent() {

        return rentRepositoryM.findAll();
    }
    public void deleteRent(Long userNumber){

        rentRepositoryM.deleteByRentReservationId(userNumber);
    }


    public int selectRentCount(){

        int count = rentRepositoryM.countAllBy();
        return count;
    }

    public List<RentReservationInfo> findRentByPage(int pageNo){

        Pageable pageable = PageRequest.of(pageNo-1,10);
        Page<RentReservationInfo> pageList = rentRepositoryM.findAll(pageable);
        List<RentReservationInfo> rentList = pageList.getContent();

        return rentList;
    }


}
