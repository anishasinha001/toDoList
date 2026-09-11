package org.example.service;

import org.example.model.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TodoService {

    private final List<Todo> todos = new ArrayList<>();

    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Todo> getAllTodos() {
        return todos;
    }

    public Todo getTodoById(Long id) {

        return todos.stream()
                .filter(todo -> todo.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Todo not found with id: " + id));
    }

    public Todo addTodo(Todo todo) {

        todo.setId(idGenerator.getAndIncrement());

        todos.add(todo);

        return todo;
    }

    public Todo updateTodo(Long id, Todo updatedTodo) {

        Todo existingTodo = getTodoById(id);

        existingTodo.setTitle(updatedTodo.getTitle());
        existingTodo.setDescription(updatedTodo.getDescription());
        existingTodo.setCompleted(updatedTodo.isCompleted());

        return existingTodo;
    }

    public void deleteTodo(Long id) {

        Todo todo = getTodoById(id);

        todos.remove(todo);
    }
}