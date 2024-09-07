package ImageUploader.controllers;

import ImageUploader.DTO.TodoDTO;
import ImageUploader.Response.ApiResponse;
import ImageUploader.Service.Todo.ITodoService;
import ImageUploader.Service.Todo.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {
    private final ITodoService todoService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllLoggedInUserTodo() {

        return ResponseEntity.ok(todoService.getAllTodos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getTodoByID(@PathVariable Long id) {

        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createTodo(@RequestBody TodoDTO todoDTO) {

        return ResponseEntity.ok(todoService.addTodo(todoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateTodo(@PathVariable Long id, @RequestBody Map<String, Object> updates) {

        return ResponseEntity.ok(todoService.updateTodo(id, updates));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTodo(@PathVariable Long id) {

        return ResponseEntity.ok(todoService.deleteTodo(id));
    }

}
