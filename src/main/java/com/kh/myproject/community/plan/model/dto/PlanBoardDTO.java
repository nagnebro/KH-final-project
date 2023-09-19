package com.kh.myproject.community.plan.model.dto;

import com.kh.myproject.community.plan.model.entity.PlanBoard;
import com.kh.myproject.community.plan.model.entity.PlanBoardDetail;
import com.kh.myproject.member.user.model.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class PlanBoardDTO {
    private Long pbNum; //게시글번호
    /*private LocalDateTime pbWriteDate; // 작성일자*/
    private String pbTitle; //글제목
    private  String pbStartDate; //시작날짜
    private  String pbEndDate; //종료날짜
    private  String pbRegion; //여행지역
    private int pbViewCount; //조회수
    private User user; //userid

    public PlanBoard toEntity(){
        return PlanBoard.builder()
                .pbNum(pbNum)
                .pbTitle(pbTitle)
                .pbStartDate(pbStartDate)
                .pbEndDate(pbEndDate)
                .pbRegion(pbRegion)
                .pbViewCount(pbViewCount)
                .user(user)
                .build();
    }
}
