package com.kh.myproject.member.manager.repository;

import com.kh.myproject.community.accompany.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

public interface CommentRepositoryM extends JpaRepository<Comment, Long> {


    @Modifying
    @Transactional
    void deleteByUserUserNumber(Long userNumber);
}
