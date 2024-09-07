package ImageUploader.Response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class ApiResponse {

    private String message;
    private boolean success;
    private Object data;
}
