package com.example.demo.service;


import com.example.demo.dtos.TodoDto;
import com.example.demo.entity.TodoEntity;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TodoRepository;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        TodoEntity todoEntity = modelMapper.map(todoDto,TodoEntity.class);

        TodoEntity savedTodoEntity = todoRepository.save(todoEntity);

        TodoDto savedTodoDto = modelMapper.map(savedTodoEntity,TodoDto.class);

        return savedTodoDto;
    }

    @Override
    public TodoDto getTodo(Long id) {

       TodoEntity todoEntity = todoRepository.findById(id)
               .orElseThrow(()->new ResourceNotFoundException("Resource Not found with Id :"+id));
        TodoDto todoDto = modelMapper.map(todoEntity,TodoDto.class);
        return todoDto;
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<TodoEntity> getAllTodos = todoRepository.findAll();

        return  getAllTodos.stream().map((todo)->modelMapper.map(todo,TodoDto.class)).collect(Collectors.toList());

    }

    @Override
    public TodoDto updateTodo(Long id, TodoDto todoDto) {
        TodoEntity todoEntity = todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Todo not found with Id :"+id));
        todoEntity.setTitle(todoDto.getTitle());
        todoEntity.setDescription(todoDto.getDescription());
        todoEntity.setCompleted(todoDto.isCompleted());
        TodoEntity updatedTodo = todoRepository.save(todoEntity);
        return modelMapper.map(updatedTodo,TodoDto.class);
    }

    @Override
    public void deleteTodo(Long id) {
        TodoEntity todoEntity =todoRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Todo is not found with given id :"+id));
        todoRepository.deleteById(id);
    }

    @Override
    public TodoDto completeTodo(Long id) {
        TodoEntity todoEntity = todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Todo not found with the given id :"+id));

        todoEntity.setCompleted(Boolean.TRUE);
        TodoEntity savedTodoEntity = todoRepository.save(todoEntity);
        return modelMapper.map(savedTodoEntity,TodoDto.class);


    }

    @Override
    public TodoDto inCompleteTodo(Long id) {

        TodoEntity todoEntity =todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Todo not found with id :"+id));

        todoEntity.setCompleted(Boolean.FALSE);
        TodoEntity updatedTodo = todoRepository.save(todoEntity);
        return modelMapper.map(updatedTodo,TodoDto.class);
    }
}
