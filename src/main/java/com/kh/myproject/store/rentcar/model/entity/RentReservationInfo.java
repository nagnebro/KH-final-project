package com.kh.myproject.store.rentcar.model.entity;

import com.kh.myproject.member.user.model.entity.User;
import com.kh.myproject.store.rentcar.model.dto.RentReservationDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "rent_info")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RentReservationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentReservationId;

    @Column
    private String rentName;
    @Column
    private String rentType;
    @Column
    private String rentCompany;
    @Column
    private String rentOption;
    @Column
    private String rentPrice;
    @Column
    private String rentDepartureDate;
    @Column
    private String rentArrivalDate;
    @Column
    private String rentImg;
    @Column
    private Long rentAccommodate;
    @Column
    private String rentYear;
    @Column
    private String rentOil;
    @Column
    private String tid;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "userNumber")
    private User user;

    public RentReservationDto toDto() {
        return RentReservationDto.builder()
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