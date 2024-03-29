let currentRoomId = 0;

// STOMP 클라이언트 초기화
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket' // 엔드포인트
});

// 연결 성공 시 콜백 함수
stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    setConnected(true);
    currentRoomId = $("#roomId").val();
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

// 메시지 수신
function onMessageReceived(payload) {
    const message = JSON.parse(payload.body);
    const messageRow = `
        <tr>
            <td>${message.userName}</td>
            <td>${message.content}</td>
        </tr>
    `;
    $("#messageList").append(messageRow);
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

    $("#messageList").html("");
}

// 메세지 보내는 함수
function sendMessage() {
    const userName = $("#userName").val();
    const content = $("#messageInput").val();
    stompClient.publish({
        destination: `/app/messages/${currentRoomId}`,
        body: JSON.stringify({'userName': userName, 'content': content})
    });
    $("#messageInput").val("");
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

$(function () {
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