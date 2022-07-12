package fpt.edu.capstone.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "gender")
    private boolean gender;

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

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Column(name = "created_at")
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    private  LocalDateTime updateAt;

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "avatar_url")
    private String avatarUrl;
}
