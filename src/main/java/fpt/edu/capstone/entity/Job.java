package fpt.edu.capstone.entity;

import fpt.edu.capstone.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "is_deleted")
    private int isDeleted = 0;

    @Column(name = "is_popular_job")
    private boolean isPopularJob;

    @Column(name = "is_new_job")
    private boolean isNewJob;

    @Column(name = "is_urgent_job")
    private boolean isUrgentJob;

    @Column(name = "recruiter_id")
    private long recruiterId;

    @Column(name = "weekday")
    private String weekday;

    @Column(name = "vietnam_country_id")
    private long countryId;
}
