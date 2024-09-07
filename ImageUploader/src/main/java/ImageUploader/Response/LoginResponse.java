package ImageUploader.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private Long userID;
    private String token;

}
