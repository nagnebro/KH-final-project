package com.kh.myproject.api.kakaoPay.model.dto;

import com.kh.myproject.member.user.model.entity.User;
import com.kh.myproject.store.flight.model.entity.FlightTicketInfo;
import com.kh.myproject.store.rentcar.model.entity.RentReservationInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaybillDto {
    
    private Long rentReservationId;
    private String rentName;
    private String rentType;
    private String rentCompany;
    private String rentOption;
    private String rentPrice;
    private String rentDepartureDate;
    private String rentArrivalDate;
    private String rentImg;
    private Long rentAccommodate;
    private String rentYear;
    private String rentOil;

    private Long ticTicketId;
    private String ticFlightDepartureDate;
    private String ticFlightArrivalDate;
    private String ticSeatGrade;
    private String ticAirlineName;
    private String ticAirlineLogo;
    private String ticFee;
    private String ticFromLocation;
    private String ticToLocation;
    private String ticVihicleId;
    private String tid;
    private User user;

    private Boolean checkFlag;

    public FlightTicketInfo toTicketEntity() {
        return FlightTicketInfo.builder()
                .ticTicketId(ticTicketId)
                .ticFlightDepartureDate(ticFlightDepartureDate)
                .ticFlightArrivalDate(ticFlightArrivalDate)
                .ticSeatGrade(ticSeatGrade)
                .ticAirlineName(ticAirlineName)
                .ticAirlineLogo(ticAirlineLogo)
                .ticFee(ticFee)
                .ticFromLocation(ticFromLocation)
                .ticToLocation(ticToLocation)
                .ticVihicleId(ticVihicleId)
                .user(user)
                .tid(tid)
                .build();
    }

    public RentReservationInfo toRentEntity() {
        return RentReservationInfo.builder()
                .rentReservationId(rentReservationId)
                .rentName(rentName)
                .rentType(rentType)
                .rentCompany(rentCompany)
                .rentOption(rentOption)
                .rentPrice(rentPrice)
                .rentDepartureDate(rentDepartureDate)
                .rentArrivalDate(rentArrivalDate)
                .rentImg(rentImg)
                .rentAccommodate(rentAccommodate)
                .rentYear(rentYear)
                .rentOil(rentOil)
                .tid(tid)
                .user(user)
                .build();
    }
}
