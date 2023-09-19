package com.kh.myproject.community.plan.model.entity;

import com.kh.myproject.community.plan.model.dto.PlanBoardDTO;
import com.kh.myproject.member.user.model.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="`plan_board`")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`pb_num`")
    private Long pbNum; //게시글번호

    /*@CreationTimestamp
    @Column
    private LocalDateTime pbWriteDate; // 작성일자*/

    @Column
    private String pbTitle; //글제목

    @Column
    private  String pbStartDate; //떠나는날

    @Column
    private  String pbEndDate;  //돌아오는날

    @Column
    private  String pbRegion; //여행지역

    @Column
    private int pbViewCount;  //조회수

    @JoinColumn(name = "user_number")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private User user;  //유저번호

    @OneToMany(mappedBy = "planBoard", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PlanBoardDetail> planBoardDetails = new ArrayList<>();

    public PlanBoardDTO toDto(){
        return PlanBoardDTO.builder()
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
