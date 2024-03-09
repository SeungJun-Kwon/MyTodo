const todoForm = document.getElementById('todoForm');
const todoList = document.getElementById('todoList');

// Todo 목록 조회
function getTodos() {
    fetch('/api/todos')
    .then(response => response.json())
    .then(data => {
        if (data.httpCode === 200) {
            displayTodos(data.data);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// Todo 목록 출력
function displayTodos(todos) {
    todoList.innerHTML = '';

    todos.forEach(todo => {
        const li = document.createElement('li');
        li.innerHTML = `
      <span>${todo.todoName}</span>
      <p>${todo.content}</p>
      <p>작성일: ${todo.createdAt}</p>
      <button onclick="completeTodo(${todo.todoId}, ${!todo.finished})">
        ${todo.finished ? '취소' : '완료'}
      </button>
    `;

        if (todo.finished) {
            li.classList.add('completed');
        }

        todoList.appendChild(li);
    });
}

// Todo 생성
function createTodo(event) {
    event.preventDefault();

    const todoName = document.getElementById('todoName').value;
    const content = document.getElementById('content').value;

    fetch('/api/todos', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            todoName: todoName,
            content: content,
        }),
    })
    .then(response => response.json())
    .then(data => {
        if (data.httpCode === 200) {
            getTodos();
            todoForm.reset();
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

// Todo 완료/취소
function completeTodo(todoId, finished) {
    fetch(`/api/todos/${todoId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            finished: finished,
        }),
    })
    .then(response => response.json())
    .then(data => {
        if (data.httpCode === 200) {
            getTodos();
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

todoForm.addEventListener('submit', createTodo);

// 페이지 로드 시 Todo 목록 조회
getTodos();