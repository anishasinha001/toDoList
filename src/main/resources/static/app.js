const API_URL = "/api/todos";


// Load todos when page opens
document.addEventListener("DOMContentLoaded", loadTodos);


// GET /api/todos
async function loadTodos() {

    const response = await fetch(API_URL);

    const todos = await response.json();

    displayTodos(todos);
}


// Display todos
function displayTodos(todos) {

    const todoList = document.getElementById("todoList");

    todoList.innerHTML = "";

    todos.forEach(todo => {

        const todoDiv = document.createElement("div");

        todoDiv.className = "todo";

        todoDiv.innerHTML = `

            <h3 class="${todo.completed ? 'completed' : ''}">
                ${todo.title}
            </h3>

            <p>
                ${todo.description || ""}
            </p>

            <div class="actions">

                <button onclick="editTodo(${todo.id})">
                    Edit
                </button>

                <button onclick="deleteTodo(${todo.id})">
                    Delete
                </button>

                <button onclick="completeTodo(${todo.id}, ${todo.completed})">
                    ${todo.completed ? "Mark Pending" : "Complete"}
                </button>

            </div>
        `;

        todoList.appendChild(todoDiv);
    });
}


// POST /api/todos
async function addTodo() {

    const title =
        document.getElementById("title").value;

    const description =
        document.getElementById("description").value;

    if (!title.trim()) {

        alert("Please enter a task");

        return;
    }

    const todo = {

        title: title,

        description: description,

        completed: false
    };

    await fetch(API_URL, {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(todo)
    });

    document.getElementById("title").value = "";

    document.getElementById("description").value = "";

    loadTodos();
}


// PUT /api/todos/{id}
async function editTodo(id) {

    const title =
        prompt("Enter new title:");

    if (!title) {
        return;
    }

    const description =
        prompt("Enter new description:");

    const todo = {

        title: title,

        description: description,

        completed: false
    };

    await fetch(`${API_URL}/${id}`, {

        method: "PUT",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(todo)
    });

    loadTodos();
}


// DELETE /api/todos/{id}
async function deleteTodo(id) {

    const confirmed =
        confirm("Are you sure you want to delete this task?");

    if (!confirmed) {
        return;
    }

    await fetch(`${API_URL}/${id}`, {

        method: "DELETE"
    });

    loadTodos();
}


// Complete / pending
async function completeTodo(id, currentStatus) {

    const response =
        await fetch(`${API_URL}/${id}`);

    const todo =
        await response.json();

    todo.completed = !currentStatus;

    await fetch(`${API_URL}/${id}`, {

        method: "PUT",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(todo)
    });

    loadTodos();
}