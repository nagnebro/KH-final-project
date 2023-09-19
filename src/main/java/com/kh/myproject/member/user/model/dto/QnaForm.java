package com.kh.myproject.member.user.model.dto;

import com.kh.myproject.member.user.model.entity.Qna;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor // 모든 멤버 변수를 초기화하는 생성자
@NoArgsConstructor // 기본 생성자.
@EqualsAndHashCode // equals() 메서드랑 hashcode 메서드도 자동완성된다. 객체의 값을 비교할 때 equals나 hashcode메서드를 사용하는데
// 그 떄 굳이 오버라이딩 시킬 필요가 없어진다.
public class QnaForm {

    private String qna_writer;      // 문의글 작성자
    private String qna_title;       // 문의글 제목
    private String qna_content;     // 문의글 내용
    private LocalDateTime qna_date;          // 문의글 시간
    private String qna_answer;      // 문의글 답변


    // DTO 클래스에 데이터를 Entity(테이블과 매핑되는 클래스)로
    // 변환하는 메서드를 추가한다.




    public Qna toEntity(){
        return new Qna(null,qna_writer, qna_title, qna_content, qna_date, qna_answer);
    }

}
