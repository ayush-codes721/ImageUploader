package ImageUploader.advices;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {

    @Builder.Default
    private boolean success=false;
    private String error;
}
