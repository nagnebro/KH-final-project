var userNumber = -1; // document load후에 현재의 세션값으로 초기화된다.
var user;
var roomId = -1;
var webSocket = null;


var url = location.pathname.split('/');





$.ajax({

    method: 'POST',
    url: '/getUserNumber',
    data: {userNumber: userNumber}, // 임의로 설정한 userNumber를 넘겨주고 그걸 세션으로 더해준다.
    // 그러면 chatTest1에서는 세션1이 추가되고 chatTest2에서는 세션2기 추가될 것.
    // ***********실제로는 data쿼리없이 진짜 data만 받아와야한다.**********************
    // 여기서 userNumber를 얻어온 후
    success: function (data) {


        // 이떄 얻어낸 usernUmber를 컨트롤러의 세션user에서 얻어온 userNumber와 동일한 값으로 본다.
        userNumber = data;
        console.log(userNumber + "userNumber값");

        if (url[1] === 'chatTest1') {
            userNumber = 1;
        } else if (url[1] === 'chatTest2') {
            userNumber = 2;
        } else if (url[1] === 'chatTest3') {
            userNumber = 3;
        } else if (url[1] === 'chatTest4') {
            userNumber = 4;
        } else if (url[1] === 'manager') {

            userNumber = 0;
        }

    }, error: function () {

    }
})





console.log(userNumber)






//클릭관련 이벤트 함수는 위쪽 배치. 실제 실행되는 함수는 아래쪽 배치.
$('#quit-chat-btn').click(function () {
    $('.chat-modal').css('display', 'none');
    $('.chat-room-block').remove(); // 메시지를 모두 지운다.
    $('#message-input').addClass('disappear'); // 채팅입력하는 채팅창 disappear
    $('.message-block').remove(); // 채팅 메시지 블럭 제거
    $('#back-chat-btn').addClass('disappear'); // 뒤로가기버튼 disappear
    $('.chat-room-info').addClass('disappear');
    $('.my-message').remove();
    $('#chat-modal-content').removeClass('show-chat-room-info'); //  패딩 탑 속성 제거.

    if (webSocket != null) {

        webSocket.close();

    }

})


$('#back-chat-btn').click(function () { // 뒤로가기 버튼 클릭시 채팅내용을 갖고 있던 건 모두 지워버린후 기존의 채팅방 조회 목록으로 진입.

    $('#message-input').addClass('disappear'); // 채팅입력하는 채팅창 disappear
    $('.message-block').remove(); // 채팅 메시지 블럭 제거
    $('#back-chat-btn').addClass('disappear'); // 뒤로가기버튼 disappear
    $('.chat-room-info').addClass('disappear');
    $('#chat-modal-content').removeClass('show-chat-room-info'); //  패딩 탑 속성 제거.
    $('.my-message').remove();
    // 현재 열려 있는 채팅방의 소켓을 닫는다.

    webSocket.close(); // 이것만으로는 java의 소켓 세션이 종료되지 않는다.
    showChatRoom();

})


$('#live-chat').click(function () {

    //모달을 열때마다 userNumber를 요청한다.
    showChatRoom();

    // 소켓이 열린 상태라면 실행해서는 안된다.


    // 세션의 usernumber를 이용해 접속한 채팅방 목록을 불러온다.

})


// 메시지 전송을 눌렀을떄(Enter) sendMessage메서드 실행.
$('.message-input').on('keypress', function (e) {

        sendMessage(e);

    }
)


// 모달 열기 + 채팅방 목록 불러오는 함수
// 처음 모달을 열떄는 소켓 통신이 되어있어야한다.
function showChatRoom() {

    if (userNumber == -1) {

        Swal.fire('로그인 후 이용해 주세요.', '', 'info')

        return;
    }

    $.ajax({

        method: 'POST',
        // url: '/getUserNumber',      원래는 getUserNumber를 통해 관련데이터를 넘겨받는 식이지만
        url: '/getChatData',                                // 현재로서는 서로 다른 세션을 가져오기 위해 userNumber는 원래 가지고 있다고 가정하고 해당 userNumber로
        data: {userNumber: userNumber},                                // 채팅방 및 메세지 정보를 가지고 오는 ajax요청을 실행한다.
        success: function (data) {

            console.log(data)
            user = data.user; // 현재 접속한 유저 정보를 저장한다.

            userNumber = user.userNumber;

            $('.chat-modal').css('display', 'block');


            addLastMessage(data); // roomList정보에 맞게 소켓정보를 추가해준다.

            // 채팅방 목록 불러오기 완료, 불러온 채팅방 ui 추가.

            // 세션을 통해 유저 정보를 가지고 오고 해당 유저 정보를 가진 채팅방을 모두 불러온다.

        }, error: function () {

        }
    })

}

function addLastMessage(data) {

    // 소켓 추가하는 기능.
    user = data.user; // 현재 접속한 유저 정보를 저장한다.
    var roomList = data.roomList; // 현재 속해있는 방의 리스트를 가지고 온다.
    var lastMessageList = data.lastMessageList;

    var body = ''

    for (var i = 0; i < roomList.length; i++) {

        var roomId = roomList[i].roomId;


        var tmpUser = roomList[i].user1.userNumber != user.userNumber ? roomList[i].user1 : roomList[i].user2;

        // 현재 유저 말고 다른 유저를 가지고 온다.

        body += `<div class="chat-room-block" onclick="joinChatRoom(this)" id=${roomId}>` +

            `<div class = "chat-room-block-left"><img class = "chat-room-img" src = "/file/profile_image/${tmpUser.userImg}"></div>` +
            `<div class = "chat-room-block-right">` +
            `<div class = "chat-room-name">${tmpUser.userId.split('@')[0]}</div>` +
            `<div class = "chat-room-content">${lastMessageList[i]}</div>` +
            `</div>` +
            `</div>`
    }

    $('.chat-modal-content').append(body);

}


// enter눌렸을 떄 실행되는 메서드.
function sendMessage(e) {


    if (e.key === "Enter" && $('.message-input').val().trim() != '') {

        e.preventDefault(); // enter키를 막아놓고 텍스트 전송만 실시한다.

        var query = {
            type: 'message',     // 데이터 전송 타입
            roomId: roomId,    // 채팅방번호
            content: $('.message-input').val().trim() // 전송 데이터.
        }

        webSocket.send(JSON.stringify(query));
        // 수신측에서는 onmessage함수내에서 블럭을 추가한다.


        var userId = user.userId.split('@')[0];

        var message = {
            user: user,
            content: $('.message-input').val(),
            sendTime: new Date(),
        }

        addMessageBlock(message);


        $('.message-input').focus();
        $('.message-input').val().trim();
        $('.message-input').val(''); // 빈칸으로 만들어준다.


        var scroll = document.getElementById('chat-modal-content'); // content의 스크롤 아래로 이동.
        scroll.scrollTop = scroll.scrollHeight;


    }
}


// 실제 소켓 연결이 된 후 채팅방에 접속하고 나가고 할때 이뤄지는 동작들(close, send, error등)
function run() {
    webSocket.onopen = function (e) {
        console.log('소켈열림.')
    }
    webSocket.close = function (e) {

        var data = {
            type: "close"
        }
        webSocket.send(JSON.stringify(data));

        console.log("소켓연결이 종료됐습니다.")
    }


    // 소켓 연결된 다른 클라이언트에게 메시지가 왔을 떄.
    webSocket.onmessage = function (e) {

        // 넘어온 데이터는 기존 타입에서 chatMessage 객체가 추가된 property일것이다.
        var data = JSON.parse(e.data);
        // var sendId = JSON.parse(data.sendId); //료 발신자 user객체

        var type = data.type; //메시지 전송타입.
        var message = JSON.parse(data.message);

        console.log(data);


        if (type === 'message') { // 전송된 데이터가 단순 메시지 전송이라면


            addMessageBlock(message);

        }
    }

}

// 메시지를 수신시, 송신이세 modal-content에 자식요소 div추가하는 메서드.
// 수신시에 실시간으로 메시지를 추가해줘야한다. 그러나 이떄 현재 내가 대화하는 roomId와
// 메시지를 발신한 사람의 roomId가 같아야만 한다.
// 소켓이 연결돼있다하더라도
function addMessageBlock(message) {


    var user = message.user; // db의 필드명인 sendId로 저장되는 것이 아니고 Message객체의 필드인 USer객체이름인 user로 저장된다.
    var date = new Date(message.sendTime); // date타입을 string으로 가지고 있기 때문에 다시 파싱한다.
    var hour = String(date.getHours()).padStart(2, '0');
    var minute = String(date.getMinutes()).padStart(2, '0');

    const formattedDate = `${hour}:${minute}`; // '09:11'
    var userId = user.userId.split('@')[0];
    var body = '';

    if (message.user.userNumber != userNumber) { //발신자와 수신자가 다를떄.


        // <div className="message-block" style="margin-top: 100px;">
        //     <div className="message-center">
        //         <div className="message-img">
        //             <img className="message-img" src="/img/store/rentcar/celtos.png" alt="">
        //         </div>
        //
        //         <div className="message-userId">${userIsadasdd}</div>
        //     </div>
        //     <div className="message-bottom" style="display : flex; padding-left : 15px">
        //         <div className="message-content"> s</div>
        //
        //         <div className="message-time">${formattedDate}</div>
        //     </div>
        // </div>
        //
        // body = '<div class = "message-block">' +
        //     '<div class = "message-img">' +
        //     `<img class = "message-img" src="/file/profile_image/${user.userImg}" alt="">` +
        //     '</div>' +
        //     `<div class = "message-center">` +
        //     `<div class = "message-userId">${userId}</div>` +
        //     `<div class = "message-content" >${message.content}</div>` +
        //     `</div>` +
        //     `<div class = "message-time">${formattedDate}</div>` +
        //     '</div>'

        body = `
                  <div class="message-block">
                    <div class="message-center">
                      <div class="message-img">
                             <img class = "message-img" src="/file/profile_image/${user.userImg}" alt=""> 
                      </div>
                      <div class="message-userId">${userId}</div>
                    </div>
                    <div class="message-bottom">
                      <div class="message-content">${message.content}</div>
                      <div class="message-time">${formattedDate}</div>
                    </div>
                  </div>`;

    } else {

        body = '<div class = "my-message">' +
            `<div class = "message-time">${formattedDate}</div>` +
            `<div class = "my-message-center">` +
            `<div class = "my-message-content" >${message.content}</div>` +
            `</div>` +
            '</div>'

    }


    $('.chat-modal-content').append(body); // 채팅창에 메시지를 추가한다.

}


// 채팅방을 들어왔을 때 실행되는 메서드.
// 채팅방에 들어왔을떄는 기존의 버튼을 제거하고 위쪽의 뒤로가기 버튼을 추가해줘야한다.
function joinChatRoom(element) {


    // 방을 접속하게 되면 현재 접속한 방의 번호와 유저정보를 가지고 있어야한다.
    //유저 정보는 어차피 한번 불러오면 세션에서 동일한 유저일 것임.
    roomId = element.id;
    webSocket = new WebSocket('ws://' + location.host + '/ws/' + roomId + '/' + userNumber);

    run(); // addEvenetListener를 추가한다고 생각하면 됨.


    // webSocketList[roomId] = webSocket; // webSocketList에 roomId를 키값으로 해서 webSocket을 차례대로 put한다
    // webSocket = webSocketList[roomId]; // 현재 접속한 방번호로 socket을 교체한다.
    // // 채팅방을 접속하고 나면 대화목록을 모두 불러와야 한다.


    $('.chat-chat-modal-content').remove(); // 채팅방 목록을 제거한다.
    $('#back-chat-btn').removeClass('disappear'); // 뒤로가기 버튼을 생성한다.

    $.ajax({

        method: 'POST',
        url: '/getMessageList',
        data: {roomId: roomId},
        success: function (data) {

            var chatMessageList = data.chatMessageList;
            var chatRoom = data.chatRoom;
            var tmpUser = chatRoom.user1.userNumber != userNumber ? chatRoom.user1 : chatRoom.user2;

            var body = '';

            $('.chat-room-block').remove(); // 채팅방목록을 제거한다.


            for (var i = 0; i < chatMessageList.length; i++) {

                var message = chatMessageList[i]
                addMessageBlock(message);


            }


            ``
            // 이전 대화내용을 모두 불러온다.
            // userNumber는 전역변수로 선언돼있다.
            $('.chat-modal-content').append(body);
            $('.modal-btn').remove(); // 모달 나가기 버튼 없애기
            $('.message-input').removeClass('disappear');
            $('.chat-room-info').removeClass('disappear');
            $('.chat-modal-content').addClass('show-chat-room-info')//패딩 탑 추가ㅏ

            var chatRoomInfo = document.getElementById("chat-room-info"); //채팅방 타이틀바
            chatRoomInfo.innerText = tmpUser.userId.split('@')[0];

            var scroll = document.getElementById('chat-modal-content');
            scroll.scrollTop = scroll.scrollHeight;


        }, error: function () {

        }

    })


}


// show 채팅방목록


// show 채팅방 들어가ㄴㄴㄴ


// accompany_detail의 1:1채팅 클릭시
// 1:1채팅 버튼 클릭시.
// 로그인이 돼있는 상태여야한다.
// detail은 로그인하지 않은 상태에서도 볼 수 있으므로 session을 검사하낟.
// ===========댓글에서 채팅하기 클릭시 로그인하지 않았을때 유효성 검사 추가===========
$('.chat-btn').click(function () {

    console.log('1:1 채팅 클릭!');


    console.log($(this).val());
    var writerNumber = $(this).val();

    console.log(userNumber)
    console.log(writerNumber)

    if (userNumber == writerNumber) { // 내가 나한테 채팅을 걸려고 한다면.


        Swal.fire({
            title: '본인에게는 채팅을 거실 수 없습니다.',
            icon: 'error',
            confirmButtonColor: '#00b8ff',
            confirmButtonText: '확인'
        });

    } else if (userNumber == -1) {
        Swal.fire({
            title: '로그인 후 채팅하실 수 있습니다.',
            icon: 'error',
            confirmButtonColor: '#00b8ff',
            confirmButtonText: '확인'
        }).then(function(){
            location.href='/member/login';
        });

    } else if (userNumber != -1) { // 현재 가지고 있는 userNumber값이 없을때만 채팅방을 생성한다.
        $.ajax({


            url: '/addChatRoom',
            method: 'POST',
            data: {
                writerNumber: writerNumber,
                userNumber: userNumber
                // 맨 처음 userNumber를 한번 얻어온 후로 그 userNumber가 곧 세션값이라고 본다.
                // 따라서 컨트롤러에서 세션을 얻는것이 아닌, 여기서 쿼리로 넘겨준다.
            },
            success: function (data) {

                console.log('통신성공 data' + data);

                showChatRoom();

            }, error: function () {


            }


        })
    }

})