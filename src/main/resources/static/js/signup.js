$(document).ready(function() {
    const signupForm = $('#signupForm');

    signupForm.submit(function(e) {
        e.preventDefault();

        // 입력 값을 가져옵니다.
        const email = $('#email').val();
        const userName = $('#userName').val();
        const password = $('#password').val();

        // 여기에 입력 값 검증 로직을 추가할 수 있습니다.

        // 서버로 회원가입 요청을 보냅니다.
        $.ajax({
            type: 'POST',
            url: `/api/users/signup`,
            contentType: 'application/json',
            data: JSON.stringify({ email, userName, password }),
            success: function(data) {
                if (data.httpCode === 200) {
                    // 회원가입 성공 처리
                    window.location.href = '/login.html';
                } else {
                    // 회원가입 실패 처리
                    alert(data.message);
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error('회원가입 에러:', textStatus);
            }
        });
    });
});