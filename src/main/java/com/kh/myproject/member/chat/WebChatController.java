package com.kh.myproject.member.chat;


import com.kh.myproject.member.chat.model.entity.ChatMessage;
import com.kh.myproject.member.chat.model.entity.ChatRoom;
import com.kh.myproject.member.chat.service.ChatMessageService;
import com.kh.myproject.member.chat.service.ChatRoomService;
import com.kh.myproject.member.user.model.entity.User;
import com.kh.myproject.member.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@SessionAttributes({"user","user1","user2","user3","user4"})

public class WebChatController {


    @Autowired
    ChatRoomService chatRoomService;

    @Autowired
    ChatMessageService chatMessageService;

    @Autowired
    UserService userService;

//    @PostMapping("/getUserNumber")
//    @ResponseBody
//    public Map<String,Object> getUserNumber(HttpSession session){
//
//        List<ChatRoom> roomList = null;
//        Map<String, Object> userInfo = new HashMap<>();
//        long userNumber = -1;
//
//        // null 일수 있기 때문에 sessionㄱ 객체로 뽑아낸다
//        User user = (User)session.getAttribute("user");
//
//        System.out.println("usergetNumber" + user.getUserNumber());
//
//        if(user == null){
//            user = userService.getUserByNumber(Long.valueOf(1));
//        }
//        if(user != null){ // 이미 1번 유저가 접속해있다면.
//
//            if(user.getUserNumber() == 1) {
//                user = userService.getUserByNumber(Long.valueOf(2));
//
//            }else if(user.getUserNumber() == 2){
//                user = userService.getUserByNumber(Long.valueOf(3));
//
//            } else if(user.getUserNumber() == 3){
//                user = userService.getUserByNumber(Long.valueOf(4));
//
//            }
//        }
//
//        List<String> lastMessageList = null;
//        if(user != null) {
//
//            userNumber = user.getUserNumber();
//            roomList = chatRoomService.getChatRoomList(userNumber);
//            lastMessageList = chatMessageService.getLastMessageList(userNumber,roomList);
//
//        }
//
//
//        // 채팅방을 열게되면 채팅방 목록에 해당하는 마지막 메시지 리스트를 받아와야한다.
//        // 여기도 바꿔야하낟. 테스트하려면.
//        userInfo.put("user",user);
//        userInfo.put("roomList",roomList); // roomList객체를 반환한다.
//        userInfo.put("lastMessageList",lastMessageList);
//        return userInfo;
//    }

    @PostMapping("/getUserNumber")
    @ResponseBody
    public Long getUserNumber(HttpSession session,@ModelAttribute("userNumber")Long userNumber,Model model){

        User user = (User) session.getAttribute("user");
        System.out.println(user);

//        if(user == null) {
//            User user = userService.getUserByNumber(userNumber);
//            Long userNumber = Long.valueOf(-1);
//        }
//
        if(userNumber == 2){
            model.addAttribute("user2",user);
        }else if(userNumber == 1){
            model.addAttribute("user1",user);
        }else if(userNumber == 3){
            model.addAttribute("user3",user);
        }else if(userNumber == 4){
            model.addAttribute("user4",user);
        }


        if(user != null){
            userNumber = user.getUserNumber();
        }
        System.out.println("userNumber입니다" + userNumber);

        return userNumber;
    }



    @GetMapping("addSession2")
    public String addSession2(Model model,HttpSession session){
        List<ChatRoom> roomList = null;


        User user2 = (User)session.getAttribute("user2");
        User user3 = (User)session.getAttribute("user3");
        List<User> userList = new ArrayList<>();

        if(user2 !=null){
            userList.add(user2);

        }else if(user3 != null){
            userList.add(user3);

        }

        User user = new User();
        long userNumber = 2;

        user = userService.getUserByNumber(userNumber);
        model.addAttribute("user2",user);

        return "community/home";

    }


    @PostMapping("/getMessageList")
    @ResponseBody
    public Map<String,Object> getMessageList(@ModelAttribute("roomId")Long roomId){


        Map<String,Object> chatRoomInfo = new HashMap<>();

        List<ChatMessage> chatMessageList = chatMessageService.getAllMessage(roomId);
        ChatRoom chatRoom = chatRoomService.getChatRoomByRoomId(roomId);

        chatRoomInfo.put("chatRoom",chatRoom);
        chatRoomInfo.put("chatMessageList",chatMessageList);

        return chatRoomInfo;
    }


    @RequestMapping("/chatTest1")
    public String chatTest2(Model model, HttpSession session){


        User user = new User();
        long userNumber = 1;

        user = userService.getUserByNumber(userNumber);
        model.addAttribute("user1",user);

        return "community/home";

    }


    @RequestMapping("/chatTest2")
    public String chatTest1(Model model, HttpSession session){


        User user = new User();
        long userNumber = 2;

        user = userService.getUserByNumber(userNumber);
        model.addAttribute("user2",user);

        return "community/home";

    }

    @RequestMapping("/chatTest3")
    public String chatTest3(Model model, HttpSession session){


        User user = new User();
        long userNumber = 3;

        user = userService.getUserByNumber(userNumber);
        model.addAttribute("user3",user);

        return "community/home";

    }


    @RequestMapping("/chatTest4")
    public String chatTest4(Model model, HttpSession session){


        User user = new User();
        long userNumber = 4;

        user = userService.getUserByNumber(userNumber);
        model.addAttribute("user4",user);

        return "community/home";

    }


    @PostMapping("/getChatData")
    @ResponseBody
    public Map<String,Object> getChatData(@ModelAttribute("userNumber")Long userNumber){


        System.out.println("userNUmber : " + userNumber);



        List<ChatRoom> roomList = null;
        List<String> lastMessageList = null;
        Map<String, Object> userInfo = new HashMap<>();


        // null 일수 있기 때문에 sessionㄱ 객체로 뽑아낸다
        User user = userService.getUserByNumber(userNumber);

        if(user != null) {

            userNumber = user.getUserNumber();
            roomList = chatRoomService.getChatRoomList(userNumber);
            lastMessageList = chatMessageService.getLastMessageList(userNumber,roomList);

        }


        // 채팅방을 열게되면 채팅방 목록에 해당하는 마지막 메시지 리스트를 받아와야한다.
        // 여기도 바꿔야하낟. 테스트하려면.
        userInfo.put("user",user);
        userInfo.put("roomList",roomList); // roomList객체를 반환한다.
        userInfo.put("lastMessageList",lastMessageList);
        return userInfo;


    }

    @ResponseBody
    @RequestMapping("/addChatRoom")
    public void addChatRoom(@ModelAttribute("writerNumber")Long writerNumber,
                              @ModelAttribute("userNumber")Long userNumber){


            chatRoomService.addChatRoom(writerNumber, userNumber);

    }




}
