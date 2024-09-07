package ImageUploader.Service.Auth;

import ImageUploader.DTO.UserDTO;
import ImageUploader.Request.LoginRequest;
import ImageUploader.Request.SignupRequest;
import ImageUploader.Response.ApiResponse;
import ImageUploader.Response.UserSignupResponse;

public interface IAuthService {

    ApiResponse signup(SignupRequest signupRequest);

    ApiResponse login(LoginRequest loginRequest);
}
