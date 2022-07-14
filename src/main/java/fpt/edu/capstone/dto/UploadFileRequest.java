package fpt.edu.capstone.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFileRequest {
    private String path;
    private String uploadFileName;
}
