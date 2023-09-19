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
public class detailLeports {

    private String chkPetLeports;
    private String infoCenterLeports;
    private String openPeriod;
    private String parkingFeeLeports;
    private String parkingLeports;
    private String reservation;
    private String restDateLeports;
    private String useFeeLeports;
    private String useTimeLeports;
}
