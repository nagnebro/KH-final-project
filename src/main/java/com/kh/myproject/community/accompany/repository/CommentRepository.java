package com.kh.myproject.community.accompany.repository;

import com.kh.myproject.community.accompany.entity.Comment;
import com.kh.myproject.member.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByUser(User user);

    @Query("select co from Comment co join Accompany ac on ac.ac_num = co.accompany.ac_num where co.accompany.ac_num = :ac_num")
    List<Comment> findAllByAccompany_Acnum(Long ac_num);



}
