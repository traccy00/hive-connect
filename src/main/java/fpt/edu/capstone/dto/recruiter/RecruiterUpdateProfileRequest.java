package fpt.edu.capstone.dto.recruiter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecruiterUpdateProfileRequest {
    private long id;
    private String additionalLicense;
    private String avatar;
    private String fullName;
    private String phone;
    private boolean gender;
    private String position;
    private String linkedinAccount;
    private String businessLicense;
    private String avatarUrl;
}
