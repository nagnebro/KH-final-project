
package com.kh.myproject.community.accompany.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kh.myproject.member.user.model.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "accompany")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class Accompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ac_num")
    private Long ac_num;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "userNumber")
    private User user;

    @Column
    private Date ac_regdate; //작성일자

    @Column
    private String ac_title; //글 제목

    @Column
    private String ac_text; //글 내용t

    @Column
    private String ac_people; //동행자 수

    @Column
    private String ac_region; //동행 지역


    @Column
    private Date ac_startdate; //시작 날짜

    @Column
    private Date ac_enddate; //종료 날짜

    @Column
    private String ac_status; //모집 상태

    @Column
    private String ac_picture; //사진

    @Column
    private int ac_viewcount; // 조회수

    @Column
    private String ac_travelstyle; //여행취향

    @Column
    private String ac_personalhash; // 해쉬 태그

    @JsonIgnore
    @OneToMany(mappedBy = "accompany", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}