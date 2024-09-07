package ImageUploader.Service.User;

import ImageUploader.DTO.TodoDTO;
import ImageUploader.DTO.UserDTO;
import ImageUploader.Response.ApiResponse;
import ImageUploader.Response.ProfileResponse;
import ImageUploader.model.Todo;
import ImageUploader.model.User;
import ImageUploader.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService, UserDetailsService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        return List.of();
    }

    @Override
    public ApiResponse getLoggedInUserUserWithPost() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       UserDTO userDTO=convert(user);
        return ApiResponse.builder()
                .message("user todos")
                .success(true)
                .data(userDTO)
                .build();
    }

    private UserDTO convert(User user) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setUsername(user.getUsername());
        userDTO.setImageUrl(user.getImageUrl());
        List<TodoDTO> todoDTOS = userDTO.getTodos();
        for (Todo todo : user.getTodos()) {

            TodoDTO todoDTO = new TodoDTO();
            todoDTO.setId(todo.getId());
            todoDTO.setTitle(todo.getTitle());
            todoDTO.setContent(todo.getContent());
            todoDTOS.add(todoDTO);


        }

        return userDTO;

    }

    @Override
    public ApiResponse getMyProfile() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        return ApiResponse.builder()
                .message("user profile")
                .success(true)
                .data(modelMapper.map(user, ProfileResponse.class))
                .build();
    }


    @Override
    public User findUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new BadCredentialsException("user not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() -> new BadCredentialsException("user not found"));
    }
}
