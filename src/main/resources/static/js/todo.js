// 현재 페이지 번호
let currentPage = 1;
// 페이지 크기
let pageSize = 5;
// 정렬 순서 (true = 오름차순, false = 내림차순)
let isAsc = true;

$(function () {

    getTodosPaging();

    $(`#create-todo`).on(`click`, function (event) {
        createTodo(event);
    });
});

// Todo 목록 조회
function getTodos() {
    $.ajax({
        url: '/api/todos',
        type: 'GET',
        success: function (data) {
            if (data.httpCode === 200) {
                displayTodos(data.data);
            }
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

// Todo 목록 조회(페이징)
function getTodosPaging() {
    $.ajax({
        url: `/api/todos/pages?page=${currentPage}&size=${pageSize}&isAsc=${isAsc}&sortBy=todoId`,
        type: 'GET',
        success: function (data) {
            if (data.httpCode === 200) {
                displayTodos(data.data.content);
                updatePagination(data.data);
            }
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

// 페이지 UI 관련 함수
function updatePagination(pageData) {
    const totalPages = pageData.totalPages;
    const pageInfo = `${currentPage} / ${totalPages}`;
    $(`#page-info`).text(pageInfo);

    $(`#prev-page`).prop('disabled', currentPage === 1);
    $(`#next-page`).prop('disabled', currentPage === totalPages);

    $(`#prev-page`).off('click').on('click', () => {
        if (currentPage > 1) {
            currentPage--;
            getTodosPaging();
        }
    });

    $('#next-page').off('click').on('click', () => {
        if (currentPage < totalPages) {
            currentPage++;
            getTodosPaging();
        }
    });
}

// Todo 목록 출력
function displayTodos(todos) {
    $(`#todoList`).empty();

    todos.forEach(todo => {
        const li = $('<li>');
        li.html(`
            <span>${todo.todoName}</span>
            <p>${todo.content}</p>
            <p>작성일: ${todo.createdAt}</p>
            <button onclick="completeTodo(${todo.todoId}, ${!todo.finished})">
                ${todo.finished ? '취소' : '완료'}
            </button>
        `);

        if (todo.finished) {
            li.addClass('completed');
        }

        console.log(li);

        $(`#todoList`).append(li);
    });
}

// Todo 생성
function createTodo(event) {
    event.preventDefault();

    const todoName = $('#todoName').val();
    const content = $('#content').val();

    $.ajax({
        url: '/api/todos',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            todoName: todoName,
            content: content
        }),
        success: function (data) {
            if (data.httpCode === 200) {
                getTodosPaging();
                // todoForm[0].reset();
            }
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

// Todo 완료/취소
function completeTodo(todoId, finished) {
    $.ajax({
        url: `/api/todos/${todoId}`,
        type: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify({
            finished: finished
        }),
        success: function (data) {
            if (data.httpCode === 200) {
                getTodos();
            }
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });
}