package com.kh.myproject.member.user.repository;

import com.kh.myproject.member.user.model.entity.Qna;
import com.kh.myproject.member.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;


public interface QnaRepository extends JpaRepository<Qna, Long> {

    @Query("SELECT q FROM Qna q WHERE q.qnaWriter = :qna_writer")
    List<Qna> findAllByWriter(@Param("qna_writer") String qna_writer);

}