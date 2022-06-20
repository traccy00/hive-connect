package fpt.edu.capstone.dto.job;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecruiterPostResponse {
    private String categoryName;
    private long companyId;
    private String companyName;
    private String jobName;
    private String jobDescription;
    private long fromSalary;
    private long toSalary;
    private long numberRecruits;
    private String rank;
    private String workForm;
    private String experience;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String workPlace;
    private long jobViewCount;
    private String techStackRequire;
    private String hashtag;
    private String title;
    private String mapUrl;
    private String latitude;
    private String longitude;
}
