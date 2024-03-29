'use strict';   //使用JS的严格模式

var usernamePage = document.querySelector('#username-page');//HTML 的DOM querySelector()方法可以不需要额外的jQuery等支持，也可以方便的获取DOM元素，
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');//输入框的发送文本
var messageArea = document.querySelector('#messageArea');//消息框的两种消息：系统通知and聊天信息
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;
var room = null;
var destination = null;
var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();//trim意思是去除字段两边多余的空格

    room = document.querySelector('#room').value.trim();//频道号，trim意思是去除字段两边多余的空格


    if(username) {
        usernamePage.classList.add('hidden');    //隐藏了名字的页面
        chatPage.classList.remove('hidden');//显示聊天的界面

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic

    if(room == null){

    stompClient.subscribe('/topic/public', onMessageReceived);//客户端定义一个订阅地址，用来接收服务端的信息

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )}

    else {
        destination = "/topic/" + room;

        stompClient.subscribe(destination, onMessageReceived);//客户端定义一个订阅地址，用来接收服务端的信息

        // Tell your username to the server
        stompClient.send("/app/chat.addUserOneLine",
            {},
            JSON.stringify({sender: username, type: 'JOIN', room: room})  //需要传入的参数
        )}

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        var chatMessage = {  //一整个的对象
            sender: username,
            room:room,
            content: messageInput.value,
            type: 'CHAT'
        };

        if(room == null) stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));//JavaScript 对象转换为字符串。
        else stompClient.send("/app/chat.sendMessageOneLine", {}, JSON.stringify(chatMessage));//JavaScript 对象转换为字符串。
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {                       //join info
        messageElement.classList.add('event-message');
        message.content = message.sender + ' 加入了聊天室!';
    } else if (message.type === 'LEAVE') {              //left info
        messageElement.classList.add('event-message');
        message.content = message.sender + ' 离开了聊天室!';
    } else {
        messageElement.classList.add('chat-message');   //chat info

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)//为两个表单提交添加监听的事件
messageForm.addEventListener('submit', sendMessage, true)//发送聊天框的表单监听
