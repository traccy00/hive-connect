package fpt.edu.capstone.dto.job;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class JobResponse {
    private long jobId;
    private long categoryId;
    private long recruiterId;
    private String companyName;
    private String jobName;
    private String jobDescription;
    private long fromSalary;
    private long toSalary;
    private long numberRecruits;
    private String rank;
    private String workForm;
    private String experience;
    private boolean gender;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String workPlace;
    private long jobViewCount;
    private String techStackRequire;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int isDeleted;
}
