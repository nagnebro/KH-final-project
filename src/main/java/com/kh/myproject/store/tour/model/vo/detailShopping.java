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
public class detailShopping {

    private String chkPetShopping;
    private String cultureCenter;
    private String fairDay;
    private String infoCenterShopping;
    private String openTime;
    private String parkingShopping;
    private String restDateShopping;
    private String saleItem;
    private String saleItemCost;
    private String shopGuide;
}
