package com.kh.myproject.member.manager.model.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor // 모든 멤버 변수를 초기화하는 생성자
@NoArgsConstructor // 기본 생성자.
@EqualsAndHashCode // equa
public class Manager {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 기본키 값을 자동으로 생성한다.
    private Long id;
    // user_number 필드는 auto_increment이면서 pk이다.

    @Column
    private String managerId;

    @Column
    private String managerPassword;

}
