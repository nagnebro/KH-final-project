package com.kh.myproject.store.tour.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.LowerCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class detailEvent {

    private String ageLimit;
    private String bookingPlace;
    private String discountInfoFestival;
    private String eventEndDate;
    private String eventHomePage;
    private String eventPlace;
    private String eventStartDate;
    private String placeInfo;
    private String playTime;
    private String program;
    private String spendTimeFestival;
    private String sponsor2;
    private String sponsor2tel;
    private String useTimeFestival;
}
