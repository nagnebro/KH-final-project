package com.kh.myproject.store.tour.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.LowerCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class detailCommon {

    private String contentId;
    private String contentTypeId;
    private String title;
    private String tel;
    private String telName;
    private String homePage;
    private String firstImage;
    private String addr1;
    private String addr2;
    private String zipCode;
    private String mapX;
    private String mapY;
    private String mLevel;
    private String overView;


}
