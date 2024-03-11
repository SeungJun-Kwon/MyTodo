$(function () {

    getTodos();

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

    console.log(`${todoName} ${content}`)

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
                getTodos();
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