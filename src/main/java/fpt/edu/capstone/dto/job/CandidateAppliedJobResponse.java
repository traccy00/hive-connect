package fpt.edu.capstone.dto.job;

import java.util.List;

public class CandidateAppliedJobResponse {
    private long jobId;
//    private long candidateId;
//    private String candidateName;
//    private String avatar;
//    private String experienceYear;
//    private List<String> experienceDescription;
//    private String education;
//    private String careerGoal;
    List<CandidateAppliedInfor>
    private long rowCount;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(long candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getExperienceYear() {
        return experienceYear;
    }

    public void setExperienceYear(String experienceYear) {
        this.experienceYear = experienceYear;
    }

    public List<String> getExperienceDescription() {
        return experienceDescription;
    }

    public void setExperienceDescription(List<String> experienceDescription) {
        this.experienceDescription = experienceDescription;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCareerGoal() {
        return careerGoal;
    }

    public void setCareerGoal(String careerGoal) {
        this.careerGoal = careerGoal;
    }

    public long getRowCount() {
        return rowCount;
    }

    public void setRowCount(long rowCount) {
        this.rowCount = rowCount;
    }
}

