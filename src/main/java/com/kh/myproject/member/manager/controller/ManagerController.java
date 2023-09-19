package com.kh.myproject.member.manager.controller;


import com.kh.myproject.community.accompany.entity.Accompany;
import com.kh.myproject.community.plan.model.entity.PlanBoard;
import com.kh.myproject.member.manager.model.entity.Manager;
import com.kh.myproject.member.manager.service.*;
import com.kh.myproject.member.user.model.entity.Qna;
import com.kh.myproject.member.user.model.entity.User;
import com.kh.myproject.store.flight.model.entity.FlightTicketInfo;
import com.kh.myproject.store.rentcar.model.entity.RentReservationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@SessionAttributes("manager")
public class ManagerController {


    @Autowired
    UserServiceM userServiceM;

    @Autowired
    QnaServiceM qnaServiceM;

    @Autowired
    RentServiceM rentServiceM;

    @Autowired
    FlightServiceM flightServiceM;

    @Autowired
    AccompanyServiceM accompanyServiceM;

    @Autowired
    PlanServiceM planServiceM;

    @Autowired
    CommentServiceM commentServiceM;

    @Autowired
    ChatRoomServiceM chatRoomServiceM;

    @Autowired
    ChatMessageServiceM chatMessageServiceM;


    @GetMapping("/manager/home")
    public ModelAndView managerUser(
            @ModelAttribute("check_manager") Manager check_manager,
            ModelAndView modelAndView,
            HttpSession session) {

        System.out.println("managerHome메서드입니다" + check_manager);
        System.out.println(session.getAttribute("manager") + "manager값");

        if (check_manager.getManagerId() != null || session.getAttribute("manager") != null) {
            //세션값이 있거나 userCOntroller에서 로그인 요청이 들어왔다면
            // 첫번째 조건은 첫 로그인이고 두번쨰 조건은 로그인 이후 세션 등록한 조건이다.


            modelAndView.setViewName("member/manager/home");
            List<User> userList = userServiceM.findAllUser();

            int qnaCount = qnaServiceM.countByQna();
            int countMen = userServiceM.countByUserGender("M");
            int countWomen = userServiceM.countByUserGender("F");
            int userCount = userServiceM.selectUserCount(); // 총회원수..
            // 로그인 체크는 매니저로 하는데 세션 등록은 user로 한다. user에 admin계정을 만들어 놓고
            // 그 유저로 로그인을 시키는 것.

            User manager = userServiceM.getUserByManager(check_manager);


            modelAndView.addObject("userCount", userCount);
            modelAndView.addObject("manager", manager);
            modelAndView.addObject("userList", userList);
            modelAndView.addObject("qnaCount", qnaCount);
            modelAndView.addObject("countMen", countMen);
            modelAndView.addObject("countWomen", countWomen);

            // 렌트카 테이블에서 상위 예약정보 몇개를 빼온다.
            // 항공편 테이블에서 상위 예약정보 몇개를 빼온다.
            // 게시글 목록에서 각 카ㅌ고리별 상위 게시글 1개씩 빼온다.

        } else {

            // url로 접속했을 경우 에러페이지로 이동시킨다.
            modelAndView.setViewName("redirect:/errorPage");

        }

        return modelAndView;
    }

    @GetMapping("/manager/logout")
    public String logout(SessionStatus sessionStatus) {

        sessionStatus.setComplete(); // 매니저 세션을 죽인다.

        return "redirect:/";
    }

    @PostMapping("/test/getUserChart")
    @ResponseBody
    public Map<String, Object> getUserChart() {

        List<Integer> countList = userServiceM.getUserJoinCount();
        List<Object[]> list = userServiceM.getUserAgeCount();
        Map<String, BigInteger> ageMap = new HashMap<>();


        for (int i = 0; i < list.size(); i++) {

            for (int j = 0; j < list.get(i).length / 2; j++) {

                ageMap.put((String) list.get(i)[j * 2], (BigInteger) list.get(i)[j * 2 + 1]);

            }
        }


        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("countList", countList);
        resultMap.put("ageMap", ageMap);

        return resultMap;
    }


    @ResponseBody
    @PostMapping("/manager/deleteUser")
    public List<User> deleteUser(@ModelAttribute(name = "user_number")
                                 Long user_number) {


//        System.out.println(user_number);
//
//        // 외래키로 설정한 테이블의 모든 데이터를 지운다.
//        // 동행글, 게시글, 댓글, 렌트카, 항공권
//        flightServiceM.deleteTicketByUserNumber(user_number);
//        rentServiceM.deleteRent(user_number);
//        commentServiceM.deleteByUserUserNumber(user_number);
//        accompanyServiceM.deleteAccompanyByUserUserNumber(user_number);
//        planServiceM.deleteByUserUserNumber(user_number);
//
//        // 채팅방을 삭제하면 메시지도 삭제된다.
//        chatRoomServiceM.deleteChatRoom(user_number);
//
//        System.out.println("accompany삭제 실행?");

        userServiceM.deleteUser(user_number);

        // 외래키로 설정한 테이블의 모든 데이터를 지운다.
        List<User> userList = userServiceM.findAllUser();


        System.out.println(userList);

        return userList;

    }


    // 유저게시판이 보여지는 페이지. 페이징 처리 구현.
    @GetMapping("manager/user")
    public String pageTest(Model model,
                           @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                           @RequestParam(value = "search_word", defaultValue = "") String search_word,
                           @RequestParam(value = "search_option", defaultValue = "all") String search_option) {

        // 기본적인 로직 순서는 다음과 같다.
        // 1. 처음 페이지를 띄울떄는 모든 유저 리스트를 가지고 갈 것이다
        // search_word가 없는 경우에는 전체 유저를 보여주도록 한다 (ㅇjs에서 searchword가 없을떄는 검색 자체를 못하게 막아놨기 떄문에 검색어가 없다는 것은
        // 전체 유저를 조회한다는 것과 같다.)
        // 2. 검색어를 입력해서 들어왔을 떄는 search_word의 값이 있을 것이고 그때는 search_option을 통해
        // userList를 해당 필드에 맞게 쿼리문을 날린다.
        // 그럻게 받아온 userList는 곧 아래 계산에서 쓰일 userCount가 된다.
        // 문제는 페이지 이동을 할때에도 계속 같은 검색결과가 유지돼야하기 떄문에
        // model객체에 입력했던 검색어와(input태그의 value, search_word) 선택한 옵션(seleect태그의 value, search_option)
        // 값을 추가해주고 이를 input태그의 hidden값에 지정해준다(js를 이용)
        // 이렇게 되면 계속해서 같은 옵션이 저장이 돼있을 것이고 페이지 이동이 돼도 계속 같은 조건으로
        // 쿼리문을 날려 똑같은 userList를 반환할 것이다. 단 이떄 삭제하면 조건이 풀려버릴 것이다.


        int totalPgae = 0; // 그래서 총 나타내야할 페이지 수.
        int startNo = 0; // 보여줘야할 페이지 번호의 시작 ex) pageno = 3일 경우 startno = 21, endno = 30
        int endNo = 0;
        int pageStartNo = 0; // 페이지 시작돼야할 번호. ex) 글이70개면 1번부터 7번까지, 71개면 1번부터 8번까지.
        int pageEndNo = 0; // 페이지 끝나야 할 번호. 1페이지면 10페이지가 끝번호고 11페이지면 20페이지가 끝번호고.
        boolean lastPageCheck = true;
        int userCount = 0;
        boolean noUser = false;


        List<User> userList = new ArrayList<>();


        if (search_word.equals("")) { // 검색어로 유저 조회중인게 아니고 전체 유저를 조회중일 떄

            userCount = userServiceM.selectUserCount();
            userList = userServiceM.findUserByPage(pageNo);

        } else if (!search_word.equals("")) {  // 검색어로 유저 조회했을 경우.


            // 페이지 처리에 따라 like문 조회해야한다.
            userList = userServiceM.selectUserBySearchWord(pageNo, search_word, search_option);
            userCount = userServiceM.countBysearchWorld(search_word, search_option);

        }


        totalPgae = userCount % 10 > 0 && userCount != 0 ? userCount / 10 + 1 : userCount / 10;

        if (pageNo < 1 || totalPgae < pageNo) { // 혹시나 유저가 url로 이상한 값을 입력하고 들어올 경우의 예외처리
            pageNo = 1;
        }


        // 여기서 pageNo는 1,2,3,4,5 페이지를 뜻한다.
        // startNo는 글의 번호라고 생각하면 된다.
//        int endNo = 0; // 페이지 번호의 끝. -> 쿼리문 조회시 필요했으나 필요없을 듯.


        // 다음 이전 버튼 클릭시 처리
        // 다음 버튼을 클릭하면 현재 페이지에서 10페이지 뒤로 이동하는 것이 아니고
        // 5페이지에 머물고 있었더라도 11페이지로 이동하고
        // 이전페이지도 마찬가지로 15페이지였음에도 이전으로 넘어가면 10으로 머물게끔?
        // 어떻게 처리할까. 크게 중요한 것 아니다만.
        // 내 생각엔 처음부터 보는 사용자가 있을 수 있으니 11페이지로, 이전 같은 경우 10페이지로 가는게 맞는 듯하다.


        // 다음 버튼 누를 경우 현재 pageEndNo+1로 이동시킨다.
        // 이전 버튼 누를 경우 pageStartNo-1로 넘긴다.
        // 물론 이전 다음 버튼은 넘길 데이터가 있을떄만 활성화 돼야한다.


        // search로 회원 조회했는데 정보가 없다면, 혹은 db에 그냥 유저 데이터가 없는 상태라면.


        //유저 데이터가 150개다
        // 현재 페이지가 1~10페이지면 괜찮다. 그러나.
        // 현재페이지가 11~20이라면 20까지 보여줘서는 안된다
        // 따라서 userCOunt/10의 값이 pageEndNo보다 작을 경우 pageEndNo를 바꿔준다.

        //이전버튼과 처음버튼은  pageEndNo가 10인경우에는 보여주지 않는다.
        //다음버튼과 끝 버튼은 pageEndNo가 userCount/10 보다 클경우에 보여주지 않는다.


        startNo = (pageNo - 1) * 10 + 1;
        pageStartNo = pageNo / 10 * 10 + 1;
        pageStartNo = pageNo % 10 == 0 ? pageStartNo - 10 : pageStartNo; // 21~30을 보여줘야 하는데 30일떄는 startNo이 31이된다.
        pageEndNo = ((pageNo / 10 + 1) * 10);
        pageEndNo = pageNo % 10 == 0 ? pageEndNo - 10 : pageEndNo; // 21~30을 보여줘야 하는데 30일떄는 startNo이 31이된다.


        // 유저가 561명인데 pageEndNo가 60이면 56까지만 보여준다. 나머지 있으면 57까보여준다.
        System.out.println("userCOunt" + userCount);
        System.out.println("pageEndNo" + pageEndNo);

        if (totalPgae <= pageEndNo) { // 현재 보여주고 있는 페이지라인이(ex 1~10, 11~20 pageEndNo가 totlaPage보다 크거나 같을 경우. 다음과 끝은 보여주면 안된다.


            pageEndNo = userCount / 10;
            pageEndNo = userCount % 10 > 0 ? pageEndNo + 1 : pageEndNo;
            lastPageCheck = false; // 굳이 얘를 안쓰고 UseCOunt/10과 pageEndNo을 비교해도된다. 같은 뜻임.
        }

        // 시작 페이지보다 작은 값, 마지막 페이지보다 큰 값이 들어온다면 예외처리한다.
        // 마지막페이지 값

        noUser = userCount == 0 ? true : false;

        // 이전과 처음페이지도 처음과 끝에는 보여주면 안된다.
        model.addAttribute("noUser", noUser);
        model.addAttribute("totalPage", totalPgae);
        model.addAttribute("lastPageCheck", lastPageCheck);
        model.addAttribute("pageStartNo", pageStartNo);
        model.addAttribute("pageEndNo", pageEndNo);
        model.addAttribute("userList", userList);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search_option", search_option);
        model.addAttribute("search_word", search_word);


        return "member/manager/user";
    }

    @GetMapping("/manager/qna")
    public ModelAndView qna(
            ModelAndView modelAndView,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "search_word", defaultValue = "") String search_word,
            @RequestParam(value = "search_option", defaultValue = "all") String search_option,
            Model model) {





        int totalPgae = 0;
        int startNo = 0;
        int endNo = 0;
        int pageStartNo = 0;
        int pageEndNo = 0;
        boolean lastPageCheck = true;
        int qnaCount = 0;
        boolean noList = false;


        List<Qna> qnaList = new ArrayList<>();


        if (search_word.equals("")) { // 검색어로 유저 조회중인게 아니고 전체 유저를 조회중일 떄

            qnaCount = qnaServiceM.selectQnaCount();
            qnaList = qnaServiceM.findQnaByPage(pageNo);

        }
        totalPgae = qnaCount % 10 > 0 && qnaCount != 0 ? qnaCount / 10 + 1 : qnaCount / 10;

        if (pageNo < 1 || totalPgae < pageNo) { // 혹시나 유저가 url로 이상한 값을 입력하고 들어올 경우의 예외처리
            pageNo = 1;
        }


        startNo = (pageNo - 1) * 10 + 1;
        pageStartNo = pageNo / 10 * 10 + 1;
        pageStartNo = pageNo % 10 == 0 ? pageStartNo - 10 : pageStartNo; // 21~30을 보여줘야 하는데 30일떄는 startNo이 31이된다.
        pageEndNo = ((pageNo / 10 + 1) * 10);
        pageEndNo = pageNo % 10 == 0 ? pageEndNo - 10 : pageEndNo; // 21~30을 보여줘야 하는데 30일떄는 startNo이 31이된다.

        if (totalPgae <= pageEndNo) { // 현재 보여주고 있는 페이지라인이(ex 1~10, 11~20 pageEndNo가 totlaPage보다 크거나 같을 경우. 다음과 끝은 보여주면 안된다.


            pageEndNo = qnaCount / 10;
            pageEndNo = qnaCount % 10 > 0 ? pageEndNo + 1 : pageEndNo;
            lastPageCheck = false; // 굳이 얘를 안쓰고 UseCOunt/10과 pageEndNo을 비교해도된다. 같은 뜻임.
        }

        // 시작 페이지보다 작은 값, 마지막 페이지보다 큰 값이 들어온다면 예외처리한다.
        // 마지막페이지 값

        noList = qnaCount == 0 ? true : false;

        // 이전과 처음페이지도 처음과 끝에는 보여주면 안된다.
        model.addAttribute("noList", noList);
        model.addAttribute("totalPage", totalPgae);
        model.addAttribute("lastPageCheck", lastPageCheck);
        model.addAttribute("pageStartNo", pageStartNo);
        model.addAttribute("pageEndNo", pageEndNo);
//            model.addAttribute("accompanyList", accompanyList);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search_option", search_option);
        model.addAttribute("search_word", search_word);



        modelAndView.setViewName("member/manager/qna");

        modelAndView.addObject("qnaList", qnaList);

        // 렌트카 테이블에서 상위 예약정보 몇개를 빼온다.
        // 항공편 테이블에서 상위 예약정보 몇개를 빼온다.
        // 게시글 목록에서 각 카ㅌ고리별 상위 게시글 1개씩 빼온다.


        return modelAndView;
    }

    @ResponseBody
    @PostMapping("/manager/deleteQna")
    public List<Qna> deleteQna(@ModelAttribute(name = "qnaNumber")
                               String qnaNumber) {

        System.out.println(qnaNumber);
        qnaServiceM.deleteQna(qnaNumber);

        List<Qna> qnaList = qnaServiceM.getAllQna();

        System.out.println(qnaList);

        return qnaList;
    }

    @ResponseBody
    @PostMapping("/manager/answerQna")
    public void answerQna(@RequestParam("qnaNumber") String qnaNumber,
                          @RequestParam("qnaAnswer") String qnaAnswer) {

        System.out.println(qnaNumber);
        System.out.println(qnaAnswer);

        qnaServiceM.updateAnswer(qnaNumber, qnaAnswer);

    }


    ///항공권 예약 내역 보기
    @GetMapping("/manager/flight")
    public String flight(Model model,
                         @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                         @RequestParam(value = "search_word", defaultValue = "") String search_word,
                         @RequestParam(value = "search_option", defaultValue = "all") String search_option) {


        int totalPgae = 0; // 그래서 총 나타내야할 페이지 수.
        int startNo = 0; // 보여줘야할 페이지 번호의 시작 ex) pageno = 3일 경우 startno = 21, endno = 30
        int endNo = 0;
        int pageStartNo = 0; // 페이지 시작돼야할 번호. ex) 글이70개면 1번부터 7번까지, 71개면 1번부터 8번까지.
        int pageEndNo = 0; // 페이지 끝나야 할 번호. 1페이지면 10페이지가 끝번호고 11페이지면 20페이지가 끝번호고.
        boolean lastPageCheck = true;
        int ticketCount = 0;
        boolean noTicket = false;


        List<FlightTicketInfo> ticketList = new ArrayList<>();


        System.out.println("search_wod" + search_word);
        System.out.println("search_option" + search_option);

        if (search_word.equals("")) { // 검색어로 유저 조회중인게 아니고 전체 유저를 조회중일 떄

            ticketCount = flightServiceM.selectTicketCount();
            ticketList = flightServiceM.findTicketByPage(pageNo);

        } else if (!search_word.equals("")) {  // 검색어로 유저 조회했을 경우.


            // 페이지 처리에 따라 like문 조회해야한다.
            ticketList = flightServiceM.selectTicketBySearchWord(pageNo, search_word, search_option);
            ticketCount = flightServiceM.countBysearchWorld(search_word, search_option);

        }


        totalPgae = ticketCount % 10 > 0 && ticketCount != 0 ? ticketCount / 10 + 1 : ticketCount / 10;

        if (pageNo < 1 || totalPgae < pageNo) { // 혹시나 유저가 url로 이상한 값을 입력하고 들어올 경우의 예외처리
            pageNo = 1;
        }

        pageStartNo = pageNo / 10 * 10 + 1;
        pageStartNo = pageNo % 10 == 0 ? pageStartNo - 10 : pageStartNo; // 21~30을 보여줘야 하는데 30일떄는 startNo이 31이된다.
        pageEndNo = ((pageNo / 10 + 1) * 10);
        pageEndNo = pageNo % 10 == 0 ? pageEndNo - 10 : pageEndNo; // 21~30을 보여줘야 하는데 30일떄는 startNo이 31이된다.


        if (totalPgae <= pageEndNo) { // 유저가 100보다 크면 10페이지는 무조건 보여주면되고 100보다 작으면 그 몫에 나머지 있으면 +1만큼 보여준다.


            pageEndNo = ticketCount / 10;
            pageEndNo = ticketCount % 10 > 0 ? pageEndNo + 1 : pageEndNo;
            lastPageCheck = false; // 굳이 얘를 안쓰고 UseCOunt/10과 pageEndNo을 비교해도된다. 같은 뜻임.
        }


        noTicket = ticketCount == 0 ? true : false;

        // 이전과 처음페이지도 처음과 끝에는 보여주면 안된다.
        model.addAttribute("noTicket", noTicket);
        model.addAttribute("totalPage", totalPgae);
        model.addAttribute("lastPageCheck", lastPageCheck);
        model.addAttribute("pageStartNo", pageStartNo);
        model.addAttribute("pageEndNo", pageEndNo);
        model.addAttribute("ticketList", ticketList);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search_option", search_option);
        model.addAttribute("search_word", search_word);


        return "member/manager/flight";
    }

    @ResponseBody
    @PostMapping("/manager/deleteTicket")
    public List<FlightTicketInfo> deleteTicket(@ModelAttribute(name = "ticTicketId")
                                               Long ticTicketId) {


        System.out.println(ticTicketId);
        flightServiceM.deleteTicket(ticTicketId);
        // 외래키로 설정한 테이블의 모든 데이터를 지운다.
        // 렌트카 예약 내역, 동행게시글, 댓글 등


        List<FlightTicketInfo> ticketList = flightServiceM.findAll();


        System.out.println(ticketList);

        return ticketList;

    }

    //    렌트카

    @GetMapping("/manager/rentcar")
    public ModelAndView rent(
            Model model,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "search_word", defaultValue = "") String search_word,
            @RequestParam(value = "search_option", defaultValue = "all") String search_option,
            ModelAndView modelAndView) {




        int totalPgae = 0; // 그래서 총 나타내야할 페이지 수.
        int startNo = 0; // 보여줘야할 페이지 번호의 시작 ex) pageno = 3일 경우 startno = 21, endno = 30
        int endNo = 0;
        int pageStartNo = 0; // 페이지 시작돼야할 번호. ex) 글이70개면 1번부터 7번까지, 71개면 1번부터 8번까지.
        int pageEndNo = 0; // 페이지 끝나야 할 번호. 1페이지면 10페이지가 끝번호고 11페이지면 20페이지가 끝번호고.
        boolean lastPageCheck = true;
        int rentCount = 0;
        boolean noList = false;


        List<RentReservationInfo> rentList = new ArrayList<>();


        if (search_word.equals("")) { // 검색어로 유저 조회중인게 아니고 전체 유저를 조회중일 떄

            rentCount = rentServiceM.selectRentCount();
            rentList = rentServiceM.findRentByPage(pageNo);

        }
//        else if (!search_word.equals("")) {  // 검색어로 유저 조회했을 경우.
//
//
//            // 페이지 처리에 따라 like문 조회해야한다.
//            rentList = flightServiceM.selectTicketBySearchWord(pageNo, search_word, search_option);
//            rentCount = flightServiceM.countBysearchWorld(search_word, search_option);
//
//        }


        totalPgae = rentCount % 10 > 0 && rentCount != 0 ? rentCount / 10 + 1 : rentCount / 10;

        if (pageNo < 1 || totalPgae < pageNo) { // 혹시나 유저가 url로 이상한 값을 입력하고 들어올 경우의 예외처리
            pageNo = 1;
        }

        pageStartNo = pageNo / 10 * 10 + 1;
        pageStartNo = pageNo % 10 == 0 ? pageStartNo - 10 : pageStartNo; // 21~30을 보여줘야 하는데 30일떄는 startNo이 31이된다.
        pageEndNo = ((pageNo / 10 + 1) * 10);
        pageEndNo = pageNo % 10 == 0 ? pageEndNo - 10 : pageEndNo; // 21~30을 보여줘야 하는데 30일떄는 startNo이 31이된다.


        if (totalPgae <= pageEndNo) { // 유저가 100보다 크면 10페이지는 무조건 보여주면되고 100보다 작으면 그 몫에 나머지 있으면 +1만큼 보여준다.


            pageEndNo = rentCount / 10;
            pageEndNo = rentCount % 10 > 0 ? pageEndNo + 1 : pageEndNo;
            lastPageCheck = false; // 굳이 얘를 안쓰고 UseCOunt/10과 pageEndNo을 비교해도된다. 같은 뜻임.
        }


        noList = rentCount == 0 ? true : false;

        // 이전과 처음페이지도 처음과 끝에는 보여주면 안된다.
        model.addAttribute("noList", noList);
        model.addAttribute("totalPage", totalPgae);
        model.addAttribute("lastPageCheck", lastPageCheck);
        model.addAttribute("pageStartNo", pageStartNo);
        model.addAttribute("pageEndNo", pageEndNo);
        model.addAttribute("rentList", rentList);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search_option", search_option);
        model.addAttribute("search_word", search_word);




        modelAndView.setViewName("member/manager/rentcar");



        return modelAndView;
    }

    @ResponseBody
    @PostMapping("/manager/deleteRent")
    public void deleteRent(@ModelAttribute(name = "userNumber")
                           Long userNumber) {

        System.out.println(userNumber);
        rentServiceM.deleteRent(userNumber);

        List<RentReservationInfo> rentList = rentServiceM.getAllRent();

        System.out.println(rentList);

    }

    // 동행글

    @GetMapping("/manager/accompany")
    public ModelAndView accompany(
            ModelAndView modelAndView,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "search_word", defaultValue = "") String search_word,
            @RequestParam(value = "search_option", defaultValue = "all") String search_option,
            Model model) {


        int totalPgae = 0;
        int startNo = 0;
        int endNo = 0;
        int pageStartNo = 0;
        int pageEndNo = 0;
        boolean lastPageCheck = true;
        int accompanyCount = 0;
        boolean noList = false;


        List<Accompany> aList = new ArrayList<>();



            accompanyCount = accompanyServiceM.selectAcoompanyCount();
            aList = accompanyServiceM.findAccompanyByPage(pageNo);

            totalPgae = accompanyCount % 10 > 0 && accompanyCount != 0 ? accompanyCount / 10 + 1 : accompanyCount / 10;

            if (pageNo < 1 || totalPgae < pageNo) { // 혹시나 유저가 url로 이상한 값을 입력하고 들어올 경우의 예외처리
                pageNo = 1;
            }


            startNo = (pageNo - 1) * 10 + 1;
            pageStartNo = pageNo / 10 * 10 + 1;
            pageStartNo = pageNo % 10 == 0 ? pageStartNo - 10 : pageStartNo; // 21~30을 보여줘야 하는데 30일떄는 startNo이 31이된다.
            pageEndNo = ((pageNo / 10 + 1) * 10);
            pageEndNo = pageNo % 10 == 0 ? pageEndNo - 10 : pageEndNo; // 21~30을 보여줘야 하는데 30일떄는 startNo이 31이된다.

            if (totalPgae <= pageEndNo) { // 현재 보여주고 있는 페이지라인이(ex 1~10, 11~20 pageEndNo가 totlaPage보다 크거나 같을 경우. 다음과 끝은 보여주면 안된다.


                pageEndNo = accompanyCount / 10;
                pageEndNo = accompanyCount % 10 > 0 ? pageEndNo + 1 : pageEndNo;
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
            model.addAttribute("search_option", search_option);
            model.addAttribute("search_word", search_word);


            modelAndView.setViewName("member/manager/accompany");
            System.out.println(aList);
            modelAndView.addObject("aList", aList);


            return modelAndView;

    }




        // 일정글 삭제
        @ResponseBody
        @PostMapping("/manager/deleteAccompany")
        public void deleteAccompany (@ModelAttribute(name = "acNum")
                Long acNum){

            // 게시글에 관련된 댓글을 모두 삭제하고 게시글을 삭제한다.
//        commentServiceM.deleteById(acNum);
            accompanyServiceM.deleteById(acNum);


        }


//    일정 글 목록
        @GetMapping("/manager/plan")
        public ModelAndView plan (
                ModelAndView modelAndView,
                @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                @RequestParam(value = "search_word", defaultValue = "") String search_word,
                @RequestParam(value = "search_option", defaultValue = "all") String search_option,
                Model model
            ){




            int totalPgae = 0;
            int startNo = 0;
            int endNo = 0;
            int pageStartNo = 0;
            int pageEndNo = 0;
            boolean lastPageCheck = true;
            int planBoardCount = 0;
            boolean noList = false;


            List<PlanBoard> planList = new ArrayList<>();


            if (search_word.equals("")) { // 검색어로 유저 조회중인게 아니고 전체 유저를 조회중일 떄

                planBoardCount = planServiceM.selectPlanBoardCount();
                planList = planServiceM.findPlanBoardByPage(pageNo);

            }
            totalPgae = planBoardCount % 10 > 0 && planBoardCount != 0 ? planBoardCount / 10 + 1 : planBoardCount / 10;

            if (pageNo < 1 || totalPgae < pageNo) { // 혹시나 유저가 url로 이상한 값을 입력하고 들어올 경우의 예외처리
                pageNo = 1;
            }


            startNo = (pageNo - 1) * 10 + 1;
            pageStartNo = pageNo / 10 * 10 + 1;
            pageStartNo = pageNo % 10 == 0 ? pageStartNo - 10 : pageStartNo; // 21~30을 보여줘야 하는데 30일떄는 startNo이 31이된다.
            pageEndNo = ((pageNo / 10 + 1) * 10);
            pageEndNo = pageNo % 10 == 0 ? pageEndNo - 10 : pageEndNo; // 21~30을 보여줘야 하는데 30일떄는 startNo이 31이된다.

            if (totalPgae <= pageEndNo) { // 현재 보여주고 있는 페이지라인이(ex 1~10, 11~20 pageEndNo가 totlaPage보다 크거나 같을 경우. 다음과 끝은 보여주면 안된다.


                pageEndNo = planBoardCount / 10;
                pageEndNo = planBoardCount % 10 > 0 ? pageEndNo + 1 : pageEndNo;
                lastPageCheck = false; // 굳이 얘를 안쓰고 UseCOunt/10과 pageEndNo을 비교해도된다. 같은 뜻임.
            }

            // 시작 페이지보다 작은 값, 마지막 페이지보다 큰 값이 들어온다면 예외처리한다.
            // 마지막페이지 값

            noList = planBoardCount == 0 ? true : false;

            model.addAttribute("noList", noList);
            model.addAttribute("totalPage", totalPgae);
            model.addAttribute("lastPageCheck", lastPageCheck);
            model.addAttribute("pageStartNo", pageStartNo);
            model.addAttribute("pageEndNo", pageEndNo);
            model.addAttribute("pageNo", pageNo);
            model.addAttribute("search_option", search_option);
            model.addAttribute("search_word", search_word);



            modelAndView.setViewName("member/manager/plan");
            modelAndView.addObject("planList", planList);


            return modelAndView;
        }

        @ResponseBody
        @PostMapping("/manager/deletePlan")
        public void deletePlan (@ModelAttribute(name = "pbNum")
                Long pbNum){


            // 캐스케이드 설정으로 planBoard를 지우면 planBoardDetail도 함께 삭제된다

            planServiceM.deletePlan(pbNum);
        }
    }
