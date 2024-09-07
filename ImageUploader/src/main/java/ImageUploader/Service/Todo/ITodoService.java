package ImageUploader.Service.Todo;

import ImageUploader.DTO.TodoDTO;
import ImageUploader.Response.ApiResponse;

import java.util.Map;

public interface ITodoService {

    ApiResponse addTodo(TodoDTO todoDTO);

    ApiResponse getAllTodos();

    ApiResponse getTodoById(Long id);

    ApiResponse deleteTodo(Long id);

    ApiResponse updateTodo(Long id, Map<String, Object> updates);
}
