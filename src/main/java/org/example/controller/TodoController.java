package org.example.controller;

import org.example.model.Todo;
import org.example.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // GET all todos
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {

        return ResponseEntity.ok(todoService.getAllTodos());
    }

    // GET todo by ID
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable Long id) {

        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    // CREATE todo
    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(todoService.addTodo(todo));
    }

    // UPDATE todo
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(
            @PathVariable Long id,
            @RequestBody Todo todo) {

        return ResponseEntity.ok(
                todoService.updateTodo(id, todo)
        );
    }

    // DELETE todo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {

        todoService.deleteTodo(id);

        return ResponseEntity.noContent().build();
    }
}