package com.kh.myproject.community.plan.model.dto;

import com.kh.myproject.community.plan.model.entity.PlanBoard;
import com.kh.myproject.community.plan.model.entity.PlanBoardDetail;
import lombok.*;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class PlanBoardDetailDTO {
    private Long pbdNum; //세부일정번호
    private String pbdPlaceName; //여행지명
    private String pbdCategoryGroupName; //장소분류명
    private double pbdX; //x좌표
    private double pbdY; //y좌표
    private int pbdDate; //일자번호()
    private PlanBoard planBoard;

    public PlanBoardDetail toEntity(){
        return PlanBoardDetail.builder()
                .pbdNum(pbdNum)
                .pbdPlaceName(pbdPlaceName)
                .pbdCategoryGroupName(pbdCategoryGroupName)
                .pbdX(pbdX)
                .pbdY(pbdY)
                .planBoard(planBoard)
                .pbdDate(pbdDate)
                .build();
    }
}
