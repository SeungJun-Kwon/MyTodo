document.addEventListener('DOMContentLoaded', function () {
    const signupForm = document.getElementById('signupForm');

    signupForm.addEventListener('submit', function (e) {
        e.preventDefault();

        // 입력 값을 가져옵니다.
        const email = document.getElementById('email').value;
        const userName = document.getElementById('userName').value;
        const password = document.getElementById('password').value;

        // 여기에 입력 값 검증 로직을 추가할 수 있습니다.

        // 서버로 회원가입 요청을 보냅니다.
        fetch('/api/users/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({email, userName, password}),
        })
        .then(response => response.json())
        .then(data => {
            if (data.httpCode === 200) {
                // 회원가입 성공 처리
                window.location.href = '/login.html';
            } else {
                // 회원가입 실패 처리
                alert(data.message);
            }
        })
        .catch(error => {
            console.error('회원가입 에러:', error);
        });
    });
});
