package com.kh.myproject.store.rentcar.model.dto;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CrawlingDto {
    private String img;
    private String rating;
    private String date;
    private String ratingDetail;
    private String reviewText;
}
