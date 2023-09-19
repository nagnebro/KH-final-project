package com.kh.myproject.store.rentcar.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="com_info")
public class RentcarComEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long com_id;

    @Column
    private String com_name;
    @Column
    private String com_location;
    @Column
    private String com_tel;
    @Column
    private String com_opentime;




}
