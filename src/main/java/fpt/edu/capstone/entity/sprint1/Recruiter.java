package fpt.edu.capstone.entity.sprint1;

import javax.persistence.*;

@Entity
@Table(name = "recruiter")
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

    @Column(name = "linkedin_account")
    private String linkedInAccount;

    @Column(name = "business_license")
    private String businessLicense;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "phone_number")
    private String phoneNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isVerifyAccount() {
        return verifyAccount;
    }

    public void setVerifyAccount(boolean verifyAccount) {
        this.verifyAccount = verifyAccount;
    }

    public boolean isVerifyPhoneNumber() {
        return verifyPhoneNumber;
    }

    public void setVerifyPhoneNumber(boolean verifyPhoneNumber) {
        this.verifyPhoneNumber = verifyPhoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLinkedInAccount() {
        return linkedInAccount;
    }

    public void setLinkedInAccount(String linkedInAccount) {
        this.linkedInAccount = linkedInAccount;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
