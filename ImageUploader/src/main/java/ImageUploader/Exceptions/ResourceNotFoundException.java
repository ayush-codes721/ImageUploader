package ImageUploader.Exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String todoNotFound) {
        super(todoNotFound);
    }
}
