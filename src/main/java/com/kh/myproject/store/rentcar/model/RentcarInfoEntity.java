package com.kh.myproject.store.rentcar.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="car_info")
public class RentcarInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="car_info_id")
    private Long car_info_id;

    @Column
    private String car_name;
    @Column
    private String car_year;
    @Column
    private String car_nation;
    @Column
    private String car_type;
    @Column
    private String oil_type;
    @Column
    private String driver_age;
    @Column
    private int car_people;
    @Column
    private int car_price;
    @Column
    private int car_discount;

    @ManyToOne()
    @JoinColumn(name = "com_id")
    private RentcarComEntity com_id;
    @Column
    private String car_option;
    @Column
    private String car_img;





}
