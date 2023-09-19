package com.kh.myproject.store.flight.model.dto;

import com.kh.myproject.member.user.model.entity.User;
import com.kh.myproject.store.flight.model.entity.FlightTicketInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightTicketDto {
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
    private User user;
    private String tid;

    public FlightTicketInfo toEntity() {
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
}
