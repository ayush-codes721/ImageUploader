package ImageUploader.DTO;

import ImageUploader.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class TodoDTO {

    private Long id;

    private String title;
    private String content;


}
