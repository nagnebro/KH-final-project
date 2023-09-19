package com.kh.myproject.member.chat;


import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

@Service
public class SessionManager {

    // 실시간으로 소켓 통신이 이뤄질때마다 해당 정보를 잠시 가지고 있는 Map객체.
    // 메시지를 전송할떄는 두가지 동작이 시행된다.
    // 1. 나와같은 roomId를 가진 나를 제외한 나머지 1명의 소켓 세션 아이디에 메시지를 전송한다(send)
    // --> sessions에 roomId를 통해 session을 찾는다. 두개밖에 나오지 않을 것이다.
    // 2. 그와 별개로 db에는 또 chatMessage 객체를 insert한다.
    // 그러면 한쪽만 소켓 연결이 돼있더라도 나머지 한쪽은 다음번 접속시에 메시지를 수신할 수 있을 것이고
    // 접속했다면 실시간으로 통신이 가능한 것이다.



    // 소켓을 어떻게 담을까
    // room_id로 먼저 접근한다. 해당 roomid에는 userNumber와 socket으로 구분된
    // 1. roomId로 특정 roomId를 가진 Map<Long,Websocket>객체를 얻어온다
    // 2. 해당 객체에는 {userNumber1 : sokcet1, userNumber2 : socket2} 의 형태로 데이터를 들고있다. 없을 수도 있따

    // 이때 내가 추가하고자하는 userNumber의 값으로 socket을 sessions에 추가한다.
    // 그리고 해당 sessions를 추가한다.










    private final Map<Long, WebSocketSession> sessions = new HashMap<>();
    private final Map<Long, Map<Long, WebSocketSession>> roomList = new HashMap<>();

    public void addSession(Long roomId, Long userNumber, WebSocketSession session) {

        Map<Long, WebSocketSession> sessions = new HashMap<>(); // 세션은 그때그떄 다른 걸 넣어줘야한다.

        if (roomList.get(roomId) == null) {
            sessions.put(userNumber, session);
            roomList.put(roomId, sessions);
        } else {
            roomList.get(roomId).put(userNumber, session);
        }

        System.out.println(roomList);

    }

    public WebSocketSession getSession(Long roomId, Long userNumber) {


        Map<Long, WebSocketSession> chatRoom = roomList.get(roomId);
        WebSocketSession session = null;


        for (Long userKey : chatRoom.keySet()) {

            if (!userKey.equals(userNumber)) {
                session = chatRoom.get(userKey);
            }
        }
        return session;
    }

    public void removeSession(Long roomId, Long userNumber){

        roomList.get(roomId).remove(userNumber);


    }



}
