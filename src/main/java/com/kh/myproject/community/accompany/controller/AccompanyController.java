package com.kh.myproject.community.accompany.controller;

import com.kh.myproject.community.accompany.dto.AccompanyForm;
import com.kh.myproject.community.accompany.entity.Accompany;
import com.kh.myproject.community.accompany.entity.Comment;
import com.kh.myproject.community.accompany.repository.AccompanyRepository;
import com.kh.myproject.community.accompany.repository.CommentRepository;
import com.kh.myproject.community.accompany.service.AccompanyService;
import com.kh.myproject.member.user.model.entity.User;
import com.kh.myproject.member.user.service.UserService;
import com.kh.myproject.store.flight.model.entity.FlightTicketInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes({"user", "accompany"})
public class AccompanyController {

    @Autowired
    AccompanyRepository accompanyRepository;
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    AccompanyService accompanyService;

    @Autowired
    UserService userService;

//    //여행커뮤니티 홈(메인페이지 병합 전 삭제)
//    @GetMapping("/community/home") //http://localhost:8070/community/home
//    public String communityhome() {
//
//        System.out.println("communityhome 테스트..");
//
//        return "community/home";
//    }


    //동행 리스트(동행 메인)
    @GetMapping("/community/accompany") // http://localhost:8070/community/accompany
    public String communityAccompany(

            Model model,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(required = false, name = "orderby") String orderby,
            @RequestParam(required = false, name = "startAt") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startAt,
            @RequestParam(required = false, name = "endAt") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endAt,
            @RequestParam(required = false, name = "regionAt") String regionAt


    ) {


        int totalPgae = 0;
        int startNo = 0;
        int endNo = 0;
        int pageStartNo = 0;
        int pageEndNo = 0;
        boolean lastPageCheck = true;
        int accompanyCount = 0;
        boolean noList = false;


        List<Accompany> aList = new ArrayList<>();


        if (orderby != null && orderby.equals("recent")) {
            aList = accompanyRepository.findByAc_numOrderByAc_regdateDesc();

        } else if (orderby != null && orderby.equals("viewcount")) {
            aList = accompanyRepository.findByAc_numOrderByAc_viewcountDesc();
        }
        else if (orderby != null && orderby.equals("countComment")) {
            aList = accompanyRepository.findAccompanyWithCommentCount();
        }else if(orderby == null){

            accompanyCount = accompanyService.selectAcoompanyCount();
            aList = accompanyService.findAccompanyByPage(pageNo);

        }


        totalPgae = accompanyCount % 12 > 0 && accompanyCount != 0 ? accompanyCount / 12 + 1 : accompanyCount / 12;

        if (pageNo < 1 || totalPgae < pageNo) { // 혹시나 유저가 url로 이상한 값을 입력하고 들어올 경우의 예외처리
            pageNo = 1;
        }


        pageStartNo = pageNo / 10 * 10 + 1;
        pageStartNo = pageNo % 10 == 0 ? pageStartNo - 10 : pageStartNo; // 21~30을 보여줘야 하는데 30일떄는 startNo이 31이된다.
        pageEndNo = ((pageNo / 10 + 1) * 10);
        pageEndNo = pageNo % 10 == 0 ? pageEndNo - 10 : pageEndNo;
        if (totalPgae <= pageEndNo) { // 현재 보여주고 있는 페이지라인이(ex 1~10, 11~20 pageEndNo가 totlaPage보다 크거나 같을 경우. 다음과 끝은 보여주면 안된다.


            pageEndNo = accompanyCount / 12;
            pageEndNo = accompanyCount % 12 > 0 ? pageEndNo + 1 : pageEndNo;
            lastPageCheck = false; // 굳이 얘를 안쓰고 UseCOunt/10과 pageEndNo을 비교해도된다. 같은 뜻임.
        }

        // 시작 페이지보다 작은 값, 마지막 페이지보다 큰 값이 들어온다면 예외처리한다.
        // 마지막페이지 값

        noList = accompanyCount == 0 ? true : false;

        // 이전과 처음페이지도 처음과 끝에는 보여주면 안된다.
        model.addAttribute("noList", noList);
        model.addAttribute("totalPage", totalPgae);
        model.addAttribute("lastPageCheck", lastPageCheck);
        model.addAttribute("pageStartNo", pageStartNo);
        model.addAttribute("pageEndNo", pageEndNo);
//            model.addAttribute("accompanyList", accompanyList);
        model.addAttribute("pageNo", pageNo);



        // 예슬님 코드 시작

//        System.out.println("컨트롤러의 ");
//        // 목록보기
//
//        // db에서 정보를 가져오는 locig을 짜야함
//        List<Accompany> accompanyEntity;
//        if (orderby != null && orderby.equals("recent")) {
//            accompanyEntity = accompanyRepository.findByAc_numOrderByAc_regdateDesc();
//
//        } else if (orderby != null && orderby.equals("viewcount")) {
//            accompanyEntity = accompanyRepository.findByAc_numOrderByAc_viewcountDesc();
//        }
//        else if (orderby != null && orderby.equals("countComment")) {
//            accompanyEntity = accompanyRepository.findAccompanyWithCommentCount();
//            System.out.println("accompanyEntity값을 넣어줌" + accompanyEntity);
//        }
//        else if (startAt != null && endAt != null) {
//            // 시작 날짜와 종료 날짜 사이의 게시글 검색
//            accompanyEntity = accompanyRepository.findByAc_startdateBetween(startAt, endAt);
//        }
//        else if (regionAt != null) {
//            // 지역 선택하면 해당 지역 게시글만 검색
//            accompanyEntity = accompanyRepository.findByAc_regionContains(regionAt);
//        }
////            else if (orderby != null && startAt != null && endAt != null) {
////            // 최신순, 조회orderby != null &&기간, 지역순 중복쿼리
////            accompanyEntity = accompanyRepository.findByAc_startdateWithinAndAc_enddateOrderByAc_regdateDesc(startAt, endAt);
////        }
//
//        else {
//            accompanyEntity = accompanyRepository.findByAc_numOrderByAc_regdateDesc();
//        }

        model.addAttribute("accompanyList", aList);


        return "community/accompany/accompany";
    }


//        Accompany ac = accompanyEntity.get(0);
//
//
//        User acUser = ac.getUser();
//        System.out.println(acUser);


    //동행 글 쓰기
    @GetMapping("/community/accompany/write") // http://localhost:8070/community/accompany/write
    public String accompanyWrite(
            HttpSession session
    ) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/community/home";
        }

        return "community/accompany/accompany_write";
        /*"community/accompany/accompany_write";*/

    }

    @PostMapping("/community/accompany/writePro") // http://localhost:8070/community/accompany/write
    public String accompanyWritePro(
            HttpSession session,
            AccompanyForm form,
            @RequestParam(value = "start_date", defaultValue = "") String start_date,
            @RequestParam(value = "end_date", defaultValue = "") String end_date,
            @RequestParam(value = "ac_region", defaultValue = "") String ac_region,
            @RequestParam("ac_title") String ac_title,
            @RequestParam("ac_text") String ac_text,
            @RequestParam("ac_people") String ac_people,
            @RequestParam("accompany_image") MultipartFile multipartFile) {


        User user = (User) session.getAttribute("user");
        // accompany 의 date는 java에서도 date타입이기 때문에 우선은 form데이터를 받을때는 string으로 받지만 이후에 date타입으로 포매팅 시켜서 저장한다.
        // date타입으로 처리할떄의 장점은 날짜 계산이 편하다는 것이다. 물론 db에서 날짜를 가져오기를 start_date와 end_date를 diff함수를 이용해
        // 날짜 차이를 가지고 올 수 있기는 하지만 여러개의 글을 동시에 보여줘야하는 경우가 있기 때문에 글목록을 보여줄때마다 항상 날짜의 차이를 따로 가지고 올수는 없다.

        System.out.println("file값 : accompany_image" + multipartFile);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        // form데이터로 넘어온 문자열 형태의 날짜를 date타입으로 파싱 시킨다.
        Date ac_startdate = new Date();
        Date ac_enddate = new Date();
        try {
            ac_startdate = sdf.parse(start_date);
            ac_enddate = sdf.parse(end_date);
        } catch (Exception e) {

            e.printStackTrace();
        }


        // image명은 현재 accompany게시글의 최대값 +1 + .확장자명으로 지정한다.
        int ac_max = accompanyService.getMaxAcNum();
        String ac_picture
                = accompanyService.saveAccompanyImage(String.valueOf("acc" + (ac_max + 1)), multipartFile);

        // Repository에게 Entity를 데이터베이스에 저장하게 한다
        // id 가 자동으로 증가된다.


        // DTO의 데이터를 Entity로 변환한다.
        form.setAc_picture(ac_picture);
        form.setAc_enddate(ac_enddate);
        form.setAc_startdate(ac_startdate);
        form.setAc_regdate(new Date());
        form.setUser(user);
        Accompany accompany = form.toEntity();
        Accompany saved = accompanyRepository.save(accompany);
        System.out.println("form 태그 세터로 세팅 후 " + accompany);


        return "redirect:/community/accompany";
        /*"community/accompany/accompany_write";*/

    }


    //클릭으로 동행게시글을 들어갔을떄
    @GetMapping("community/accompany/detail")
    public String AccompanyDetail(@RequestParam(value = "ac_num", defaultValue = "0") Long ac_num,
                                  Model model) {
        System.out.println("컨트롤러의 AccompanyDetail() 메서드를 실행");
        System.out.println("ac_num = " + ac_num);

        // 해당페이지로 접속시 해당 게시글의 조회수를 올려줘야한다. 조회수가 올라간 상태로 게시글을 반환해야한다.
        Accompany accompanyEntity = accompanyRepository.findById(ac_num).orElse(null);
        accompanyService.increaseViewCount(accompanyEntity.getAc_num());
        // 객체를 찾아오기전에 미리 조회수를 올리고 찾아오기보다는 찾아오고 있을때 걔의조회수를 올려야하는데
        // 그러면 클라이언트는 증가되기전의 조회수를 보므로 임의로 객체의 변수값을 바꿔준다.
        accompanyEntity.setAc_viewcount(accompanyEntity.getAc_viewcount() + 1);

        System.out.println("accompanyEntity:" + accompanyEntity);
        List<Comment> commentEntity = commentRepository.findAllByAccompany_Acnum(ac_num);
        System.out.println("commentEntity 값 " + commentEntity);

        model.addAttribute("commentList", commentEntity);
        model.addAttribute("accompany", accompanyEntity);

        return "community/accompany/accompany_detail";

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


    // 동행 글의 수정 클릭 > 해당 글 번호 불러오기
    @GetMapping("community/accompany/edit")
    public String accompanyEdit(@RequestParam("ac_num") Long ac_num, Model model) {


        System.out.println("컨트롤러의 edit() 메서드를 실행");
        System.out.println("ac_num=" + ac_num);

//        User user = (User) session.getAttribute("user");
//
//        if ( user == null) {
//
//            return "redirect:/";
//        }

        // 수정할 데이터를 얻어온다.
        Accompany accompanyEntity = accompanyRepository.findById(ac_num).orElse(null);

        // 테이블에서 데이터를 가져와서 edit.html 로 파일을 넘기기 위해서
        // model 인터페이스 객체에 넣어준다.

        model.addAttribute("accompany", accompanyEntity);

        return "community/accompany/accompany_edit";

    }


    // 글 번호를 가지고 수정하는 메서드
    // http://localhost:8070/community/accompany/update
    @PostMapping("/community/accompany/update")
    public String Accompanyupdate(
            Model model, //모델
            @RequestParam("enddate") String enddate,
            @RequestParam("startdate") String startdate,
            @RequestParam("accompany_image") MultipartFile multipartFile,
            @RequestParam("user_number") Long user_number,
            @RequestParam("ac_num") Long ac_num,
            AccompanyForm form
    ) {

        User user = userService.getUserByNumber(user_number);
        Accompany accompany = accompanyService.getAccompany(ac_num);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date ac_startdate = new Date();
        Date ac_enddate = new Date();
        try {
            ac_startdate = sdf.parse(startdate);
            ac_enddate = sdf.parse(enddate);
        } catch (Exception e) {

            e.printStackTrace();
        }


        // image명은 현재 accompany게시글의 최대값 +1 + .확장자명으로 지정한다.
        int ac_max = accompanyService.getMaxAcNum();
        String ac_picture
                = accompanyService.saveAccompanyImage(String.valueOf("acc" + (ac_max + 1)), multipartFile);

        // Repository에게 Entity를 데이터베이스에 저장하게 한다
        // id 가 자동으로 증가된다.

        form.setAc_regdate(accompany.getAc_regdate());
        form.setAc_viewcount(accompany.getAc_viewcount());
        form.setAc_status(accompany.getAc_status());
        form.setAc_travelstyle(accompany.getAc_travelstyle());
        form.setAc_personalhash(accompany.getAc_personalhash());
        form.setAc_picture(ac_picture); // 파일명 수정
        form.setAc_startdate(ac_startdate); // 동행시작일
        form.setAc_enddate(ac_enddate); // 동행 종료일
        form.setUser(user);

        Accompany saved = form.toEntity();
        List<Comment> comments = commentRepository.findAllByAccompany_Acnum(saved.getAc_num());
        saved.setComments(comments);
        accompanyService.saveAccompany(saved);

        // 수정한 글 1건만 보여주고 싶을 때는
        return "redirect:/community/accompany";

    }


    // 글 삭제하기
    @ResponseBody
    @PostMapping("/community/accompany/delete")
    public String Accompanydelete(@RequestParam("ac_num") Long ac_num,
                                  RedirectAttributes rttr) {

        //RedirectAttributes : 리디렉션을 수행할때, 다른컨트롤러 메서드로 attributes를 전달하는데 이용
        // addAttribute() : 주소창에 정보 노출되어도 상관없는 정보 보임, 정보 넘김

        //addFlashAttribute 의 경우 데이타가 post 형식으로 전달,
        // 세션에 저장되고 오직 다음요청에서만 접근 가능, 세션에 저장되어 사용된 뒤 자동 삭제
        // 검증결과, 성공 실패여부 메세지 등 임시 사용되는 데이터에 사용, 주소창에 표기되지 x

        System.out.println("컨트롤러 delete() 메서드를 실행");
        System.out.print("ac_num : " + ac_num);


        // 삭제할 데이터를 가져온다.
        Accompany accompanyEntity = accompanyRepository.findById(ac_num).orElse(null);
        List<Comment> commentEntities = commentRepository.findAllByAccompany_Acnum(ac_num);
        System.out.println(accompanyEntity.toString());
        System.out.println(commentEntities);
        accompanyEntity.setComments(commentEntities);
        //데이터 삭제

        /*commentRepository.deleteAll(commentEntities);*/
        accompanyRepository.delete(accompanyEntity);

        rttr.addFlashAttribute("msg", ac_num + "번 글 삭제 완료!");
        return "redirect:/community/accompany";
    }


}