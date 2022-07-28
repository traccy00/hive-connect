package fpt.edu.capstone.dto.CV;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindCVResponse {
    private long cvId;
    private String candidateName;
    private List<String> workPositionExperiences;
    private List<String> schools;
    private String careerGoal;
    private String candidateAddress;
    private Object sumExperienceYear;
//    private String updatedAt;
//    private int viewCount;
//    private int openContactCount;
}
