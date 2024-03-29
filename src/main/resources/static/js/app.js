let currentRoomId = 0;
let messageList;
let messageInput;

// STOMP 클라이언트 초기화
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket' // 엔드포인트
});

// 연결 성공 시 콜백 함수
stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    setConnected(true);
    currentRoomId = $("#roomId").val();
    getRoomMessages(currentRoomId);
    stompClient.subscribe(`/topic/chat-rooms/${currentRoomId}`,
        onMessageReceived); // 서버로부터 받은 메시지(Body)를 보여줌
};

// 웹소켓 오류 발생 시 콜백 함수
stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

// STOMP 메시지 브로커에서 오류 발생 시 콜백 함수
stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

// 방에 있는 모든 메시지
function getRoomMessages(roomId) {
    $.ajax({
        url: "/api/messages/" + roomId,
        type: "GET",
        dataType: "json",
        success: function (messages) {
            // 메시지 목록 처리 로직 작성
            messages.forEach(function (message) {
                // 각 메시지에 대한 처리 로직 작성
                console.log("Message: " + message.content);
                // 메시지를 화면에 출력하거나 다른 작업 수행
                const messageRow = `
                    <tr>
                        <td>${message.userName}</td>
                        <td>${message.content}</td>
                    </tr>
                `;
                messageList.append(messageRow);
            });
        },
        error: function (xhr, status, error) {
            console.error(
                "Failed to retrieve room messages. Status: " + status);
        }
    });
}

// 메시지 수신
function onMessageReceived(payload) {
    const message = JSON.parse(payload.body);
    const messageRow = `
        <tr>
            <td>${message.userName}</td>
            <td>${message.content}</td>
        </tr>
    `;
    messageList.append(messageRow);
}

// 연결에 따른 UI 설정
function setConnected(connected) {
    $("#joinRoom").prop("disabled", connected);
    $("#exitRoom").prop("disabled", !connected);
    $("#sendMessage").prop("disabled", !connected);

    if (connected) {
        $("#messageTable").show();
    } else {
        $("#messageTable").hide();
    }

    messageList.html("");
}

// 메세지 보내는 함수
function sendMessage() {
    const userName = $("#userName").val();
    const content = messageInput.val();
    stompClient.publish({
        destination: `/app/chat-rooms/${currentRoomId}/messages`,
        body: JSON.stringify({'userName': userName, 'content': content})
    });
    messageInput.val("");
}

// STOMP 에 웹소켓 연결 활성화
function connect() {
    stompClient.activate();
}

// 웹소켓 연결 비활성화 및 연결 상태 false 로 설정
function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

// 방 목록 가져오기 함수
function getRoomList() {
    $.get("/api/chat-rooms", function (data) {
        var roomListUl = $("#room-list-ul");
        roomListUl.empty();
        data.forEach(function (room) {
            var li = $("<li></li>").text(room.name);
            li.click(function () {
                roomId = room.id;
                connectToRoom();
            });
            roomListUl.append(li);
        });
    });
}

$(function () {
    messageList = $("#messageList");
    messageInput = $("#messageInput");

    $("#joinRoom").click(function () {
        currentRoomId = $("#roomId").val();
        connect();
    });

    $("#exitRoom").click(function () {
        disconnect();
    });

    $("#sendMessage").click(function () {
        sendMessage();
    })
});