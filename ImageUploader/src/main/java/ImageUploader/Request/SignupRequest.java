package ImageUploader.Request;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SignupRequest {

    private String name;
    private String username;
    private String password;
    private MultipartFile image;
}
