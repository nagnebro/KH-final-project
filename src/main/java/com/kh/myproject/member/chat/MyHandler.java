package com.kh.myproject.member.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kh.myproject.member.chat.model.entity.ChatMessage;
import com.kh.myproject.member.chat.service.ChatMessageService;
import com.kh.myproject.member.chat.service.ChatRoomService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


// 이 클래스에서 실제 클라이언트간의 채팅과 관련된 모든 로직이 수행된다.
@Service
@SessionAttributes("user")
@Slf4j
public class MyHandler extends TextWebSocketHandler {

    Map<String, WebSocketSession> sessionMap = new HashMap<>(); //웹소켓 세션을 담아둘 맵

    @Autowired
    ChatRoomService chatRoomService;

    @Autowired
    ChatMessageService chatMessageService;

    @Autowired
    WebChatController webChatController;

    @Autowired
    SessionManager sessionManager;


    // 소켓 연결이 완료됐을 때 실행되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {


        log.info("연결완료");

        String str = session.getUri().getPath().substring((session.getId().lastIndexOf("/")) + 5);
        Long roomId = Long.parseLong(str.split("/")[0]);
        Long userNumber = Long.parseLong(str.split("/")[1]);

        log.info("연결된 소켓의 RoomIㅇ는 {}",roomId);

        sessionManager.addSession(roomId, userNumber, session);

    }


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {


        String type = "";  // 전송된 메시지의 타입.
        JSONObject jsonObject = null;
        JSONParser jsonParser = new JSONParser();
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());
        String str = session.getUri().getPath().substring((session.getId().lastIndexOf("/")) + 5);
        System.out.println("str = " + str);

        Long roomId = Long.parseLong(str.split("/")[0]);
        Long userNumber = Long.parseLong(str.split("/")[1]);

        WebSocketSession yourSession = sessionManager.getSession(roomId, userNumber);


        try {

            type = (String) ((JSONObject) jsonParser.parse(message.getPayload())).get("type");

            if (type.equals("close")) { // 소켓 종료 알림시 세션 제거후 바로 메서드 종료.
                sessionManager.removeSession(roomId, userNumber);
                return;
            }

            jsonObject = (JSONObject) jsonParser.parse(message.getPayload());

            String content = (String) jsonObject.get("content");
            ChatMessage chatMessage = chatMessageService.saveMessage(roomId, userNumber, content);// 저장 한 후의 message content를 가지고 온다.
            String parseSendMessage = "";
            parseSendMessage = objectMapper.writeValueAsString(chatMessage);

            jsonObject.put("message", parseSendMessage);

            if (yourSession != null) {
                yourSession.sendMessage(new TextMessage(jsonObject.toJSONString()));
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

        System.out.println("error");
        ;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

        log.info("핸들러에의해 remove 메서드 실행(비정상적인 경로로 종료 / reoload, 페이지 종료 등");
        String str = session.getUri().getPath().substring((session.getId().lastIndexOf("/")) + 5);
        Long roomId = Long.parseLong(str.split("/")[0]);
        Long userNumber = Long.parseLong(str.split("/")[1]);
        sessionManager.removeSession(roomId, userNumber);

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


}

