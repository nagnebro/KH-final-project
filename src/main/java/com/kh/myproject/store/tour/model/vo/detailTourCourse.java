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
public class detailTourCourse {

    private String distance;
    private String infoCenterTourcourse;
    private String schedule;
    private String takeTime;

}
