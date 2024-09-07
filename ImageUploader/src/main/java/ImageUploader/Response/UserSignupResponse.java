package ImageUploader.Response;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserSignupResponse {

    private Long id;

    private String name;
    private String username;
    private String password;
    private String imageUrl;
}
