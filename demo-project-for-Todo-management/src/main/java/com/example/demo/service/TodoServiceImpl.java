package com.example.demo.service;


import com.example.demo.dtos.TodoDto;
import com.example.demo.entity.TodoEntity;
import com.example.demo.repository.TodoRepository;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService{

    private TodoRepository todoRepository;
    private ModelMapper  modelMapper;

    public TodoServiceImpl(TodoRepository todoRepository, ModelMapper modelMapper) {
        this.todoRepository = todoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TodoDto addTodo(TodoDto todoDto) {

        TodoEntity todoEntity= new TodoEntity(
                todoDto.getId(),
                todoDto.getTitle(),
                todoDto.getDescription(),
                todoDto.isCompleted()
        );

        TodoEntity savedTodoEntity = todoRepository.save(todoEntity);

        TodoDto savedTodoDto = new TodoDto(
                savedTodoEntity.getId(),
                savedTodoEntity.getTitle(),
                savedTodoEntity.getDescription(),
                savedTodoEntity.isCompleted()
        );

        return savedTodoDto;
    }
}
