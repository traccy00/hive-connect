package fpt.edu.capstone.dto.job;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CandidateAppliedJobResponse {
    private long jobId;
    private long candidateId;
    private String candidateName;
    private String avatar;
    private String experienceYear;
    private List<String> experienceDescription;
    private String education;
    private String careerGoal;
    List<CandidateAppliedInfor> list;
    private long rowCount;
}

