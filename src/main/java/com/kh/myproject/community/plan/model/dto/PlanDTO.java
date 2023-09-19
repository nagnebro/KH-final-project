package com.kh.myproject.community.plan.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanDTO {

    private int day;
    private String place_name;
    private String address_name;
    private String category_group_name;
    private String id;
}
