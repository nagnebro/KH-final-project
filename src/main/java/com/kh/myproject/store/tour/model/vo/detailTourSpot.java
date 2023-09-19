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
public class detailTourSpot {

    private String chkPet;
    private String expAgeRange;
    private String expGuide;
    private String infoCenter;
    private String openDate;
    private String parking;
    private String restDate;
    private String useSeason;
    private String useTime;


}
