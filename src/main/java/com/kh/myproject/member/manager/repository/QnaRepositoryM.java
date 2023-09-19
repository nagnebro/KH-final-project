package com.kh.myproject.member.manager.repository;

import com.kh.myproject.member.user.model.entity.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;




public interface QnaRepositoryM extends JpaRepository<Qna, Long> {

    @Query("SELECT q FROM Qna q WHERE q.qnaWriter = :qna_writer")
    List<Qna> findAllByWriter(@Param("qna_writer") String qna_writer);


    @Modifying
    @Transactional
    @Query("update Qna q" +
            " set q.qnaAnswer = :qna_answer" +
            " where q.qnaNumber = :qna_number")
    void updateAnswer(@Param("qna_number")Long qnaNumber,
                      @Param("qna_answer")String qnaAnswer);

    int countAllBy();



    Page<Qna> findAll(Pageable pageable);



}
