package fpt.edu.capstone.dto.job;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobForRecruiterResponse {
    private long jobId;
    private String jobName;
    private String companyName;
    private String workplace;
    private long fromSalary;
    private long toSalary;
//    private long viewCount;
    private long applyCount;
}
