package com.kh.myproject.api.kakaoPay.model.entity;

import com.kh.myproject.api.kakaoPay.model.dto.CrawlingDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "crawling_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrawlingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ElementCollection
//    @CollectionTable(name = "crawling_img", joinColumns = @JoinColumn(name = "crawling_id"))
//    @Column(name = "img")
//    private List<String> img;

    @Column(name = "rating")
    private String rating;

    @Column(name = "date")
    private String date;

    @Column(name = "rating_detail")
    private String ratingDetail;

    @Column(name = "review_text", length = 5000)
    private String reviewText;

    public CrawlingDto toDto() {
        return CrawlingDto.builder()
//                .img(this.img)
                .rating(this.rating)
                .date(this.date)
                .ratingDetail(this.ratingDetail)
                .reviewText(this.reviewText)
                .build();
    }

}
