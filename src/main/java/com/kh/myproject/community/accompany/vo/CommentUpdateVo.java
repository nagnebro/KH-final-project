package com.kh.myproject.community.accompany.vo;

/*
vo - 통신용으로 사용하는 객체를 정의
comment update VO 를 만듬
Entity가 userNumber를 못 받아와서 통신용으로 vo를 만듬 */

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;


@Getter
@Setter
public class CommentUpdateVo {

    private Long co_number;
    private String co_content;




}
