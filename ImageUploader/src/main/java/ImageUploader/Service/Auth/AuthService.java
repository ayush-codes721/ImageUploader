package ImageUploader.Service.Auth;

import ImageUploader.Request.LoginRequest;
import ImageUploader.Request.SignupRequest;
import ImageUploader.Response.ApiResponse;
import ImageUploader.Response.LoginResponse;
import ImageUploader.Response.UserSignupResponse;
import ImageUploader.Service.JWT.JwtService;
import ImageUploader.Service.S3.S3Service;
import ImageUploader.model.User;
import ImageUploader.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final S3Service s3Service;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse signup(SignupRequest signupRequest) {

        if (userRepo.findByUsername(signupRequest.getUsername()).isPresent()) {
            throw new BadCredentialsException("user already exist");
        }

        User userTobeSaved = modelMapper.map(signupRequest, User.class);
        if (signupRequest.getImage() == null) {
            throw new BadCredentialsException("image is requried");
        }
        String imageUrl = s3Service.uploadImage(signupRequest.getImage());

        userTobeSaved.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userTobeSaved.setImageUrl(imageUrl);
        User savedUser = userRepo.save(userTobeSaved);

        UserSignupResponse signupResponse = modelMapper.map(savedUser, UserSignupResponse.class);
        ApiResponse apiResponse = ApiResponse.builder()
                .success(true)
                .message("signup successfull")
                .data(signupResponse)
                .build();
        return apiResponse;
    }

    @Override
    public ApiResponse login(LoginRequest loginRequest) {


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        User user = (User) authentication.getPrincipal();
        String token = jwtService.createAccessToken(user);
        LoginResponse loginResponse = LoginResponse.builder().userID(user.getId()).token(token).build();
        return ApiResponse.builder()
                .success(true)
                .message("user verified")
                .data(loginResponse)
                .build();
    }


}
