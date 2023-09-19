package com.kh.myproject.store.rentcar.model;


import com.kh.myproject.store.rentcar.repository.RentcarComRepository;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RentcarInfoDTO {


    private Long car_info_id;
    private String car_name;
    private String car_year;
    private String car_nation;
    private String car_type;
    private String oil_type;
    private String driver_age;
    private int car_people;
    private int car_price;
    private int car_discount;
    private RentcarComEntity com_id;
    private String car_option;
    private String car_img;


    public static RentcarInfoDTO fromEntity(RentcarInfoEntity entity) {
        return new RentcarInfoDTO(
                entity.getCar_info_id(),
                entity.getCar_name(),
                entity.getCar_year(),
                entity.getCar_nation(),
                entity.getCar_type(),
                entity.getOil_type(),
                entity.getDriver_age(),
                entity.getCar_people(),
                entity.getCar_price(),
                entity.getCar_discount(),
                entity.getCom_id(),
                entity.getCar_option(),
                entity.getCar_img()
        );
    }




}
