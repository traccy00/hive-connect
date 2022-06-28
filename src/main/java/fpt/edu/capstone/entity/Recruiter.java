package fpt.edu.capstone.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "recruiter")
@Getter
@Setter
public class Recruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "company_id")
    private long companyId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "verify_account")
    private boolean verifyAccount;

    @Column(name = "verify_phonenumber")
    private boolean verifyPhoneNumber;

    @Column(name = "gender")
    private String gender;

    @Column(name = "position")
    private String position;

    @Column(name = "linkedin_url")
    private String linkedInAccount;

    @Column(name = "business_license")
    private String businessLicense;

    @Column(name = "additional_license")
    private String additionalLicense;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;
}
