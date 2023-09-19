package com.kh.myproject.community.accompany.controller;


import com.kh.myproject.community.accompany.dto.CommentForm;
import com.kh.myproject.community.accompany.entity.Accompany;
import com.kh.myproject.community.accompany.entity.Comment;
import com.kh.myproject.community.accompany.repository.CommentRepository;
import com.kh.myproject.community.accompany.service.CommentService;
import com.kh.myproject.member.user.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@SessionAttributes({"user", "accompany"})
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentService commentService;


    @PostMapping("/community/accompany/reWritePro") // http://localhost:8070/community/accompany/write
    public String commentWritePro(
            HttpSession session,
            CommentForm form,
            @RequestParam("co_content") String co_content
    ) {

        User user = (User) session.getAttribute("user");
        Accompany accompany = (Accompany) session.getAttribute("accompany");


        // Repository에게 Entity를 데이터베이스에 저장하게 한다
        // id 가 자동으로 증가된다.

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // DTO의 데이터를 Entity로 변환한다.
        form.setCo_content(co_content);
        form.setCo_regdate(sdf.format(date));
        form.setUser(user);
        form.setAccompany(accompany);
        Comment comment = form.toEntity();
        Comment saved = commentRepository.save(comment);
        System.out.println("form 태그 세터로 세팅 후 comment값" + comment);

        return "redirect:/community/accompany/detail?ac_num=" + accompany.getAc_num();
        /*"community/accompany/accompany_write";*/

    }

    @ResponseBody
    @PostMapping("community/accompany/commentEdit")
    public Comment commentEdit(@RequestBody CommentForm commentForm) {

        Comment comment = commentRepository.findById(commentForm.getCo_number()).orElse(null);
        System.out.println(comment);
        comment.setCo_content(commentForm.getCo_content());
        Comment saved = commentRepository.save(comment);

        System.out.println(commentForm.toString());
        System.out.println(saved.toString());

        return saved;

    }


    @ResponseBody
    @PostMapping("/community/accompany/commentDelete")
    public void commentDelete(Model model,
                                @RequestBody CommentForm commentForm
                                ){

        System.out.println("댓글 컨트롤러의 delete() 메서드 실행");
        System.out.println("commentForm:" + commentForm);

        commentRepository.deleteById(commentForm.getCo_number());

        model.addAttribute("comentDelMsg", commentForm.getCo_number() + "번 댓글 삭제 완료!");

    }

}


//    // 동행 게시글에 댓글 달기.
//    @PostMapping("/community/accompany/detailCoWrite")
//    public List<Comment> detailCoWrite(
//            @ModelAttribute("user_number") Long user_number,
//            @ModelAttribute("commentValue") Long commentValue){
//
//        // 해당 게시글의 댓글리스트를 모조리 가지고온다.
//        System.out.println(user_number);
//        System.out.println(commentValue);
//
////        List<Comment> commentList = commentService.findByUser(accompanyEntity.getUser());
//
//        return new List<Comment>();
//    }
//
//


//댓글 목록 보기
    /*@GetMapping("community/accompany/detail") // http://localhost:8070/community/accompany
    public String commentList(@RequestParam("ac_num") Long ac_num, Model model) {

        System.out.println("컨트롤러의 commentList() 실행");
        // 목록보기

        // db에서 정보를 가져오는 locig을 짜야함

        List<Comment> commentEntity = (List<Comment>) commentRepository.findById(ac_num).orElse(null);
        Comment co = commentEntity.get(0);
        User user = co.getUser();
        Accompany accompany = co.getAc_num();

        System.out.println("comment entity에 있는 user값 : " + user);
        System.out.println("comment entity에 있는 user값 : " + accompany);

        model.addAttribute("commentList : ", commentEntity);


        return "redirect:community/accompany/detail?ac_num="+ac_num;
    }*/

