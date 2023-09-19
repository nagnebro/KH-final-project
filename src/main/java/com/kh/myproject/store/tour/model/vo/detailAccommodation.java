package com.kh.myproject.store.tour.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.LowerCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class detailAccommodation {

    private String checkInTime;
    private String checkOutTime;
    private String infoCenterLodging;
    private String parkingLodging;
    private String pickup;
    private String roomCount;
    private String reservationLodging;
    private String reservationUrl;
    private String subFacility;
    private String barbecue;
    private String refundRegulation;
}
