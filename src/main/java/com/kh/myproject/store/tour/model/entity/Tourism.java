package com.kh.myproject.store.tour.model.entity;

import com.kh.myproject.store.tour.model.dto.TourismDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "area_tourism")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Tourism {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long areaId;
    @Column
    private String areaName;
    @Column
    private int placeId;
    @Column
    private String placeName;

    public TourismDto toDto(){
        return TourismDto.builder()
                .areaId(areaId)
                .areaName(areaName)
                .placeId(placeId)
                .placeName(placeName)
                .build();
    }


}
