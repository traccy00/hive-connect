package fpt.edu.capstone.dto.job;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateJobRequest {
    private long jobId;
    private long fieldId;
    private long countryId;
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
    private String benefit;
    private String weekday;
    private String flag;//Draft, Posted
}
