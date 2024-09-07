package ImageUploader.Service.S3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    public String uploadImage(MultipartFile image) {

        try {
            String fileName = generateFileName(image);

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(image.getSize());
            objectMetadata.setContentType(image.getContentType());

            amazonS3.putObject(bucketName, fileName, image.getInputStream(), objectMetadata);

            return amazonS3.getUrl(bucketName, fileName).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private String generateFileName(MultipartFile file) {
        // Generate a unique file name to prevent overwriting
        return UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replaceAll(" ", "_");
    }
}
