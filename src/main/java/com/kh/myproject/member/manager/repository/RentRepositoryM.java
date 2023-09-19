package com.kh.myproject.member.manager.repository;

import com.kh.myproject.store.rentcar.model.entity.RentReservationInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

public interface RentRepositoryM extends JpaRepository<RentReservationInfo, Long> {

    @Modifying
    @Transactional
    void deleteByRentReservationId(Long userNumber);




    int countAllBy();


    Page<RentReservationInfo> findAll(Pageable pageable);
}
