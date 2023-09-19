package com.kh.myproject.store.rentcar.repository;

import com.kh.myproject.store.rentcar.model.entity.RentReservationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentReservationRepository extends JpaRepository<RentReservationInfo, Long> {

}
