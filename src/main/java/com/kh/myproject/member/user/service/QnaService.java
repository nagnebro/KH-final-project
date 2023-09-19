package com.kh.myproject.member.user.service;

import com.kh.myproject.member.user.model.entity.Qna;
import com.kh.myproject.member.user.repository.QnaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class QnaService {

    @Autowired
    private QnaRepository qnaRepository;

    public List<Qna> getQna(String qna_writer){
        System.out.println("QnaService의 getQna 실행");
        return qnaRepository.findAllByWriter(qna_writer);
    }
    public void submitQna(Qna qna){
        qnaRepository.save(qna);
    }

    


}
