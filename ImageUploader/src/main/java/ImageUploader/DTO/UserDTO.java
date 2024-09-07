package ImageUploader.DTO;

import ImageUploader.model.Todo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {

    private Long id;

    private String name;
    private String username;
    private String password;
    private String imageUrl;

    private List<TodoDTO> todos=new ArrayList<>();
}

