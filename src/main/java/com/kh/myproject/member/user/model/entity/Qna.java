package com.kh.myproject.member.user.model.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor // 모든 멤버 변수를 초기화하는 생성자
@NoArgsConstructor // 기본 생성자.
@EqualsAndHashCode // equa
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaNumber;


    private String qnaWriter; // 문의글 작성자

    @Column
    private String qnaTitle; // 문의글 제목

    @Column
    private String qnaContent; // 문의글 내용

    @CreationTimestamp
    @Column(name = "qna_date")
    private LocalDateTime qnaDate;

    @Column
    private String qnaAnswer; // 문의글 답변
}
