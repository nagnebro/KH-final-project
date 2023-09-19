package com.kh.myproject.api.kakaoPay.model.dto;

import com.kh.myproject.api.kakaoPay.model.entity.CrawlingEntity;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CrawlingDto {
    //    private List<String> img;
    private String rating;
    private String date;
    private String ratingDetail;
    private String reviewText;

    public CrawlingEntity toEntity() {
        return CrawlingEntity.builder()
//                .img(this.img)
                .rating(this.rating)
                .date(this.date)
                .ratingDetail(this.ratingDetail)
                .reviewText(this.reviewText)
                .build();
    }
}
