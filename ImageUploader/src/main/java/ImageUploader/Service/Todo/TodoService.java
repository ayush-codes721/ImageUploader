package ImageUploader.Service.Todo;

import ImageUploader.DTO.TodoDTO;
import ImageUploader.Exceptions.ResourceNotFoundException;
import ImageUploader.Response.ApiResponse;
import ImageUploader.model.Todo;
import ImageUploader.model.User;
import ImageUploader.repo.TodoRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TodoService implements ITodoService {

    private final TodoRepo todoRepo;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public ApiResponse addTodo(TodoDTO todoDTO) {
        Todo todo = modelMapper.map(todoDTO, Todo.class);
        User user = getUser();

        todo.setUser(user);
        Todo savedTodo = todoRepo.save(todo);

        TodoDTO savedTodoDto = modelMapper.map(savedTodo, TodoDTO.class);

        ApiResponse apiResponse = ApiResponse.builder()
                .success(true).
                message("todo added successfully").
                data(savedTodoDto).build();
        return apiResponse;
    }

    @Override
    public ApiResponse getAllTodos() {
        User user = getUser();

        List<TodoDTO> todoDTOS = todoRepo.findByUser(user)
                .stream()
                .map(todo -> modelMapper.map(todo, TodoDTO.class))
                .toList();


        return ApiResponse.builder()
                .message("All todos")
                .success(true)
                .data(todoDTOS)
                .build();


    }

    @Override
    public ApiResponse getTodoById(Long id) {

        Todo todo = todoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("todo not found"));
        User user = getUser();

        if (todo.getUser().getId() != user.getId()) {
            throw new
                    BadCredentialsException("todo not found");
        }

        return ApiResponse.builder()
                .message("todo")
                .success(true)
                .data(modelMapper.map(todo, TodoDTO.class))
                .build();


    }

    @Transactional
    @Override
    public ApiResponse deleteTodo(Long id) {
        User user = getUser();

        todoRepo.findById(id).ifPresentOrElse(todo -> {
            if (todo.getUser().getId() != user.getId()) {
                throw new BadCredentialsException("todo not found");
            } else {
                todoRepo.delete(todo);
            }
        }, () -> {
            throw new ResourceNotFoundException("todo not found");
        });


        return ApiResponse
                .builder()
                .message("todo deleted successfully")
                .success(true)
                .data("deleted")
                .build();
    }

    @Override
    public ApiResponse updateTodo(Long id, Map<String, Object> updates) {
        User user = getUser();
        Todo todo = todoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("todo not found"));

        updates.forEach((field, value) -> {
            Field fieldTobeUpdated = ReflectionUtils.findField(Todo.class, field);
            fieldTobeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldTobeUpdated, todo, value);
        });
        Todo updatedTodo = todoRepo.save(todo);
        return ApiResponse.builder()
                .message("todo updated")
                .success(true)
                .data(modelMapper.map(todo, TodoDTO.class))
                .build();
    }

    private User getUser() {

        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
