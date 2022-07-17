package fpt.edu.capstone.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadFileRequest {
    private String type;
    private String uploadFileName;
}
