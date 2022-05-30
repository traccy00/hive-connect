package fpt.edu.capstone.dto.job;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobRequest {
    private long categoryId;
    private long recruiterId;
    private String jobName;
    private String jobDescription;
    private long fromSalary;
    private long toSalary;
    private long numberRecruits;
    private String rank;
    private String workForm;
    private String experience;
    private boolean gender;
    private long startDate;
    private long endDate;
    private String workPlace;
    private long jobViewCount;
    private String techStackRequire;
    private int isDeleted;
}
