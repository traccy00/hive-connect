package fpt.edu.capstone.dto.recruiter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RecruiterProfileResponse {

    private long id;
    private String userName;
    private String avatar;
    private long companyId;
    private String companyName;
    private String companyAddress;
    private String email;
    private String fullName;
    private String phone;
    private String gender;
    private String position;
    private String linkedinAccount;
    private String businessLicense;
}
