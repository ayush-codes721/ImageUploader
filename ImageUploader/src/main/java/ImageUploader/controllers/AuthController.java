package ImageUploader.controllers;

import ImageUploader.Request.LoginRequest;
import ImageUploader.Request.SignupRequest;
import ImageUploader.Response.ApiResponse;
import ImageUploader.Service.Auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    ResponseEntity<ApiResponse> signup(@ModelAttribute SignupRequest signupRequest) {


        return ResponseEntity.ok(authService.signup(signupRequest));
    }

    @PostMapping("/login")
    ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
