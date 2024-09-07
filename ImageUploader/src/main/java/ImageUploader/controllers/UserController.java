package ImageUploader.controllers;

import ImageUploader.Response.ApiResponse;
import ImageUploader.Service.User.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping
    ResponseEntity<ApiResponse> getMyProfile() {

        return ResponseEntity.ok(userService.getMyProfile());
    }

    @GetMapping("/all")
    ResponseEntity<ApiResponse> getLoggedInUserUserWithPost() {

        return ResponseEntity.ok(userService.getLoggedInUserUserWithPost());
    }
}
