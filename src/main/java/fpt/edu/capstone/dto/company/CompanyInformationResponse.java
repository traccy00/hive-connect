package fpt.edu.capstone.dto.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInformationResponse {

    private long id;
    private String fieldWork;
    private String name;
    private String email;
    private String phone;
    private String description;
    private String website;
    private String numberEmployees;
    private String address;
    private String avatar;
    private String taxCode;
    private String mapUrl;

}
