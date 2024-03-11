document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('loginForm').addEventListener('submit',
        function (e) {
            e.preventDefault();

            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;

            fetch(`/api/users/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({email, password}),
            })
            .then(response => {
                const token = response.headers.get('Authorization');

                if (token) {
                    // 쿠키에 JWT 토큰 저장
                    document.cookie = `Authorization=${token}; path=/`;

                    // Ajax 요청 시 헤더에 토큰 설정
                    $.ajaxSetup({
                        beforeSend: function (xhr) {
                            xhr.setRequestHeader('Authorization', token);
                        }
                    });

                    return response.json();
                } else {
                    throw new Error('Login failed');
                }
            })
            .then(data => {
                console.log(data); // 성공 시 응답 데이터 처리
                // 로그인 성공 후의 로직을 여기에 구현하세요. 예: 페이지 리다이렉션
                // 로그인 성공 시 사용자 정보 저장
                localStorage.setItem('token', data.token);
                localStorage.setItem('email', data.email);
                localStorage.setItem('userName', data.userName);
            })
            .catch(error => {
                $('#loginResult').html('Login failed.');
                alert("Login failed");
            });
        });
});
