package com.example.demo.controller;


import com.example.demo.dtos.TodoDto;
import com.example.demo.entity.TodoEntity;
import com.example.demo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todo")
public class TodoController {
    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }


    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto){
        TodoDto savedTodoDto = todoService.addTodo(todoDto);
        return new ResponseEntity<>(savedTodoDto, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable Long id){
        TodoDto savedTodoDto = todoService.getTodo(id);
        return new ResponseEntity<>(savedTodoDto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos(){
        List<TodoDto> todos = todoService.getAllTodos();
        return new ResponseEntity<>(todos,HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable Long id,
                                              @RequestBody TodoDto todoDto){
        TodoDto todoDto1 = todoService.updateTodo(id,todoDto);
        return new ResponseEntity<>(todoDto1,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTodoById(@PathVariable Long id){
        todoService.deleteTodo(id);
        return ResponseEntity.ok("Todo deleted successfully");
    }

    @PatchMapping("{id}/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable Long id){
        TodoDto updateTodo = todoService.completeTodo(id);
        return new ResponseEntity<>(updateTodo,HttpStatus.OK);
    }

    @PatchMapping("{id}/incomplete")
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable Long id){
        TodoDto updateTodo = todoService.inCompleteTodo(id);
        return new ResponseEntity<>(updateTodo,HttpStatus.OK);
    }
}
