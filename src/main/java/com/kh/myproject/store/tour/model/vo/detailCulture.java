package com.kh.myproject.store.tour.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.LowerCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class detailCulture {

    private String chkPetCulture;
    private String discountInfo;
    private String infoCenterCulture;
    private String parkingCulture;
    private String parkingFee;
    private String restDateCulture;
    private String useFee;
    private String useTimeCulture;
    private String spendTime;
}
