package fpt.edu.capstone.entity;

import fpt.edu.capstone.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job")
public class Job extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "company_id")
    private long companyId;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "work_place")
    private String workPlace;

    @Column(name = "work_form")
    private String workForm;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "from_salary")
    private long fromSalary;

    @Column(name = "to_salary")
    private long toSalary;

    @Column(name = "number_recruits")
    private long numberRecruits;

    @Column(name = "rank")
    private String rank;

    @Column(name = "experience")
    private String experience;

    @Column(name = "gender")
    private boolean gender;

    @Column(name = "job_description")
    private String jobDescription;

    @Column(name = "job_requirement")
    private String jobRequirement;

    @Column(name = "benifit")
    private String benifit;

    @Column(name = "field_id")
    private long fieldId;

    @Column(name = "career_id")
    private long careerId;

    @Column(name = "is_deleted")
    private int isDeleted;

    @Column(name = "is_popular_job")
    private boolean isPopularJob;

    @Column(name = "is_new_job")
    private boolean isNewJob;

    @Column(name = "is_urgent_job")
    private boolean isUrgentJob;

    @Column(name = "recruiter_id")
    private long recruiterId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getWorkForm() {
        return workForm;
    }

    public void setWorkForm(String workForm) {
        this.workForm = workForm;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public long getFromSalary() {
        return fromSalary;
    }

    public void setFromSalary(long fromSalary) {
        this.fromSalary = fromSalary;
    }

    public long getToSalary() {
        return toSalary;
    }

    public void setToSalary(long toSalary) {
        this.toSalary = toSalary;
    }

    public long getNumberRecruits() {
        return numberRecruits;
    }

    public void setNumberRecruits(long numberRecruits) {
        this.numberRecruits = numberRecruits;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobRequirement() {
        return jobRequirement;
    }

    public void setJobRequirement(String jobRequirement) {
        this.jobRequirement = jobRequirement;
    }

    public String getBenifit() {
        return benifit;
    }

    public void setBenifit(String benifit) {
        this.benifit = benifit;
    }

    public long getFieldId() {
        return fieldId;
    }

    public void setFieldId(long fieldId) {
        this.fieldId = fieldId;
    }

    public long getCareerId() {
        return careerId;
    }

    public void setCareerId(long careerId) {
        this.careerId = careerId;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isPopularJob() {
        return isPopularJob;
    }

    public void setPopularJob(boolean popularJob) {
        isPopularJob = popularJob;
    }

    public boolean isNewJob() {
        return isNewJob;
    }

    public void setNewJob(boolean newJob) {
        isNewJob = newJob;
    }

    public boolean isUrgentJob() {
        return isUrgentJob;
    }

    public void setUrgentJob(boolean urgentJob) {
        isUrgentJob = urgentJob;
    }

    public long getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(long recruiterId) {
        this.recruiterId = recruiterId;
    }
}
