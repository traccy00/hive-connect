package fpt.edu.capstone.dto.recruiter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApprovalLicenseRequest {
    long recruiterId;
    String type;
    String approvalStatus;
}
