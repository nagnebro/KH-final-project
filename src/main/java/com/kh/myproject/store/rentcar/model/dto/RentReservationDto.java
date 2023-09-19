package com.kh.myproject.store.rentcar.model.dto;

import com.kh.myproject.member.user.model.entity.User;
import com.kh.myproject.store.rentcar.model.entity.RentReservationInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentReservationDto {

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
    private String tid;
    private User user;

    public RentReservationInfo toEntity() {
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