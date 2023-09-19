package com.kh.myproject.store.rentcar.model;


import lombok.*;

import java.util.LongSummaryStatistics;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RentcarComDTO {

    private Long com_id;
    private String com_name;
    private String com_location;
    private String com_tel;
    private String com_opentime;

    public static RentcarComDTO fromEntity(RentcarComEntity entity) {
        return new RentcarComDTO(
                entity.getCom_id(),
                entity.getCom_name(),
                entity.getCom_location(),
                entity.getCom_tel(),
                entity.getCom_opentime()
        );
    }


}
