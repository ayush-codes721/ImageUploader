package ImageUploader.Service.User;

import ImageUploader.DTO.UserDTO;
import ImageUploader.Response.ApiResponse;
import ImageUploader.model.User;

import java.util.List;

public interface IUserService {

    List<UserDTO> getAllUsers();


    ApiResponse getLoggedInUserUserWithPost();

    ApiResponse getMyProfile();

    User findUserById(Long id);




}
