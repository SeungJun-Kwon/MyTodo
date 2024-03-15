# MyTodo
스파르타 코딩클럽 Spring 숙련주차 개인 과제

**링크 : <a href="http://13.124.62.104:8080/" target="_blank">MyTodo</a>**

---

## Todo 기능 설계

1. 회원가입
   
       POST API 사용해서 회원가입한다.
       유저 정보 받아서 값이 조건에 맞으면 반환한다.

2. 로그인

        POST API 사용해서 로그인한다.
        Controller 단에서 진행하는 것이 아니라 인증, 인가 필터를 통해 값을 검증하고 JWT를 발급하고 헤더에 추가한다.
   
3. Todo 카드 생성
   
        POST API 사용해서 카드를 생성한다.
        RequestBody로 생성할 카드 내용을 받는다.
        생성된 카드를 반환한다.
   
4. Todo 카드 조회
   
        GET API 사용해서 카드를 조회한다.
        유저를 기준으로 정렬한 전체 조회, 특정 유저의 카드를 조회한다.
        PathVariable로 유저의 ID를 입력받는다.
        특정 유저의 카드 조회시 해당 유저가 본인인지 검증 후 조회한다.
   
5. Todo 카드 수정
   
        PUT API 사용해서 카드를 수정한다.
        PathVariable로 수정할 카드의 ID를 받는다.
        RequestBody로 수정할 카드 내용을 받는다.
        수정하려는 카드의 유저 정보와 현재 유저 정보가 일치하는지 검증 후 수정한다.
        수정된 카드 내용을 반환한다.

6. Todo 카드 완료

        PUT API 사용해서 카드를 완료한다.
        PathVariable로 완료 여부를 받는다.
        수정과 똑같이 검증 후 완료된 카드 내용을 반환한다.

7. 댓글 작성

        POST API 사용해서 댓글을 작성한다.
        PathVariable로 댓글을 작성할 카드의 ID를 받는다.
        RequestBody로 작성할 댓글의 내용을 받는다.
        해당 카드가 존재하는지 검증한 후 작성한다.
        작성한 댓글의 내용을 반환한다.

8. 댓글 삭제

        DELETE API 사용해서 댓글을 삭제한다.
        PathVariable로 삭제할 댓글의 ID를 받는다.
        삭제할 댓글의 작성자와 현재 유저 정보가 일치하는지 검증 후 삭제한다.
        삭제한 댓글의 ID를 반환한다.

9. 댓글 수정

        PUT API 사용해서 댓글을 수정한다.
        RequestBody로 수정할 댓글의 내용을 받는다.
        해당 댓글이 존재하는지, 유저 정보가 일치하는지 검증 후 댓글을 수정한다.

---

## ERD

<img src = "https://github.com/SeungJun-Kwon/MyTodo/assets/80217301/3facfcb8-ef0e-4051-ae4a-7ef32af5fd44" width = 1000>

```
CREATE TABLE `users` (
	`id`	bigint	NOT NULL,
	`password`	varchar(255)	NOT NULL,
	`role`	enum	NOT NULL,
	`username`	varchar(255)	NOT NULL
);

CREATE TABLE `cards` (
	`id`	bigint	NOT NULL,
	`created_at`	datetime(6)	NULL,
	`modified_at`	datetime(6)	NULL,
	`cardname`	varchar(255)	NOT NULL,
	`content`	varchar(255)	NOT NULL,
	`isfinished`	bit	NOT NULL,
	`user_id`	bigint	NOT NULL
);

CREATE TABLE `comments` (
	`id`	bigint	NOT NULL,
	`created_at`	datetime(6)	NULL,
	`modified_at`	datetime(6)	NULL,
	`content`	varchar(255)	NOT NULL,
	`user_id`	bigint	NOT NULL,
	`card_id`	bigint	NOT NULL
);

ALTER TABLE `users` ADD CONSTRAINT `PK_USERS` PRIMARY KEY (
	`id`
);

ALTER TABLE `cards` ADD CONSTRAINT `PK_CARDS` PRIMARY KEY (
	`id`
);
```

---

## API 설계서

#### Signup

<img src = "https://github.com/SeungJun-Kwon/MyTodo/assets/80217301/377bc962-9aab-43cb-a474-b13b2ced260c" width = 1000>

```
{
    "httpCode": 200,
    "msg": "abcde123 유저 회원 가입 완료",
    "data": {
        "username": "abcde123"
    }
}
```

#### Login

<img src = "https://github.com/SeungJun-Kwon/MyTodo/assets/80217301/cda0a8e7-24a8-4773-bd1e-30cf0600965e" width = 1000>

```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYmNkZTEyMyIsImF1dGgiOiJVU0VSIiwiZXhwIjoxNzA3MjExMjYxLCJpYXQiOjE3MDcyMDc2NjF9.NPL3dRteTE4Mq7kKpAI9JmGkO29vfORVCPjYlCBlkRU
```

#### Create Card

<img src = "https://github.com/SeungJun-Kwon/MyTodo/assets/80217301/4b334a78-f713-4125-83d3-5e34938b540a" width = 1000>

```
{
    "httpCode": 200,
    "msg": "카드 작성 성공!",
    "data": {
        "username": "abcde123",
        "cardname": "카드 이름",
        "content": "카드 내용",
        "isfinished": false,
        "cratedAt": "2024-02-06T17:21:29.3072806",
        "modifiedAt": "2024-02-06T17:21:29.3072806"
    }
}
```

#### Get All Cards

<img src = "https://github.com/SeungJun-Kwon/MyTodo/assets/80217301/618519de-a184-4817-be60-b96e9e70d7da" width = 1000>

```
{
    "httpCode": 200,
    "msg": "카드 전체 조회 성공!",
    "data": [
        {
            "username": "abcde123",
            "cardname": "카드 이름",
            "content": "카드 내용",
            "isfinished": false,
            "cratedAt": "2024-02-06T17:21:29.307281",
            "modifiedAt": "2024-02-06T17:21:29.307281"
        },
        {
            "username": "abcde123",
            "cardname": "카드 이름 2",
            "content": "카드 내용 2",
            "isfinished": false,
            "cratedAt": "2024-02-06T17:21:41.391928",
            "modifiedAt": "2024-02-06T17:21:41.391928"
        }
    ]
}
```

#### Get Cards By User

<img src = "https://github.com/SeungJun-Kwon/MyTodo/assets/80217301/682de009-244c-4d30-afcd-dbde77980c0b" width = 1000>

```
{
    "httpCode": 200,
    "msg": "abcde123 회원 카드 조회 성공!",
    "data": [
        {
            "username": "abcde123",
            "cardname": "카드 이름",
            "content": "카드 내용",
            "isfinished": false,
            "cratedAt": "2024-02-06T17:21:29.307281",
            "modifiedAt": "2024-02-06T17:21:29.307281"
        },
        {
            "username": "abcde123",
            "cardname": "카드 이름 2",
            "content": "카드 내용 2",
            "isfinished": false,
            "cratedAt": "2024-02-06T17:21:41.391928",
            "modifiedAt": "2024-02-06T17:21:41.391928"
        }
    ]
}
```

#### Update Card

<img src = "https://github.com/SeungJun-Kwon/MyTodo/assets/80217301/d8383088-d09e-4eb9-ba7c-2dfbcae129dc" width = 1000>

```
{
    "httpCode": 200,
    "msg": "abcde123 회원의 1 카드 수정 성공!",
    "data": {
        "username": "abcde123",
        "cardname": "카드 수정입니다",
        "content": "내용 수정입니다",
        "isfinished": false,
        "cratedAt": "2024-02-06T17:21:29.307281",
        "modifiedAt": "2024-02-06T17:21:29.307281"
    }
}
```

#### Finish Card

<img src = "https://github.com/SeungJun-Kwon/MyTodo/assets/80217301/aa8ff708-7780-46df-b0a6-3afd8564fe24" width = 1000>

```
{
    "httpCode": 200,
    "msg": "abcde123 회원의 1 카드 완료!",
    "data": {
        "username": "abcde123",
        "cardname": "카드 수정입니다",
        "content": "내용 수정입니다",
        "isfinished": true,
        "cratedAt": "2024-02-06T17:21:29.307281",
        "modifiedAt": "2024-02-06T17:24:12.621626"
    }
}
```

#### Create Comment

<img src = "https://github.com/SeungJun-Kwon/MyTodo/assets/80217301/5fd14c55-26b1-4a35-b7e7-2a4f9b8997d5" width = 1000>

```
{
    "httpCode": 200,
    "msg": "댓글입니다 댓글 작성 성공!",
    "data": {
        "username": "abcde123",
        "cardname": "카드 수정입니다",
        "content": "댓글입니다",
        "cratedAt": "2024-02-06T17:25:52.0130961",
        "modifiedAt": "2024-02-06T17:25:52.0130961"
    }
}
```

#### Update Comment

<img src = "https://github.com/SeungJun-Kwon/MyTodo/assets/80217301/c49314b6-f363-4518-88aa-1e4bca4fb309" width = 1000>

```
{
    "httpCode": 200,
    "msg": "수정했습니다 댓글 수정 성공!",
    "data": {
        "username": "abcde123",
        "cardname": "카드 수정입니다",
        "content": "수정했습니다",
        "cratedAt": "2024-02-06T17:25:52.013096",
        "modifiedAt": "2024-02-06T17:25:52.013096"
    }
}
```

#### Delete Comment

<img src = "https://github.com/SeungJun-Kwon/MyTodo/assets/80217301/9327c01a-6e35-4364-b04e-40f091d795ab" width = 1000>

```
{
    "httpCode": 200,
    "msg": "1 댓글 삭제 성공!",
    "data": 1
}
```

---

## :fire: 예외 처리

#### 회원가입 입력값 검증

```
{
    "httpCode": 400,
    "msg": "[사용자 이름의 크기가 4에서 10 사이여야 합니다., 사용자 비밀번호의 크기가 8에서 15 사이여야 합니다.]",
    "data": null
}
```

#### 로그인 실패

```
{
    "httpCode": 400,
    "msg": "로그인 실패! ID나 비밀번호를 확인해주세요.",
    "data": null
}
```

#### 토큰 검증 실패

```
{
    "httpCode": 400,
    "msg": "토큰이 유효하지 않습니다.",
    "data": null
}
```

#### 존재하지 않는 유저 ID로 조회

```
{
    "httpCode": 400,
    "msg": "유저 정보가 일치하지 않습니다.",
    "data": null
}
```

#### 존재하지 않는 카드로 접근

```
{
    "httpCode": 400,
    "msg": "해당 카드가 존재하지 않습니다.",
    "data": null
}
```

#### 존재하지 않는 댓글로 접근

```
{
    "httpCode": 400,
    "msg": "존재하지 않는 댓글입니다.",
    "data": null
}
```
