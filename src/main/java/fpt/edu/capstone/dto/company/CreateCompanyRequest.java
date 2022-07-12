package fpt.edu.capstone.dto.company;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCompanyRequest {
    //Lĩnh vực hoạt động
    private long fieldWork;
    private String name;
    private String email;
    private String phoneNumber;
    private String description;
    private String website;
    private String numberEmployees;
    private String address;
    private String avatar;
    private String taxCode;
    private String mapUrl;
    private long creatorId;
}
