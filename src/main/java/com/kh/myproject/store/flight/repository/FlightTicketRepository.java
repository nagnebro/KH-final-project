package com.kh.myproject.store.flight.repository;

import com.kh.myproject.store.flight.model.entity.FlightTicketInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightTicketRepository extends JpaRepository<FlightTicketInfo, Long> {
    List<FlightTicketInfo> findByUser_UserNumber(Long userNumber);

//    @Query("SELECT t FROM FlightTicketInfo t WHERE t.status = :status AND t.user.userId = :userId")
//    List<FlightTicketInfo> findByStatusAndUserId(int status, String userId);

}
