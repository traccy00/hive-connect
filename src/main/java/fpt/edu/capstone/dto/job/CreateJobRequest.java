package fpt.edu.capstone.dto.job;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobRequest {
    private long companyId;
    private long recruiterId;
    private long fieldId;
    private String jobName;
    private String workPlace;
    private String workForm;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private long fromSalary;
    private long toSalary;
    private long numberRecruits;
    private String rank;
    private String experience;
    private boolean gender;
    private String jobDescription;
    private String jobRequirement;
    private String benifit;
    private boolean isPopularJob;
    private boolean isNewJob;
    private boolean isUrgentJob;
}
