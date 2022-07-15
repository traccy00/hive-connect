package fpt.edu.capstone.dto.recruiter;

import fpt.edu.capstone.dto.UploadFileRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UploadBusinessLicenseRequest {
    private long recruiterId;
    private List<UploadFileRequest> uploadFileRequests;
}
