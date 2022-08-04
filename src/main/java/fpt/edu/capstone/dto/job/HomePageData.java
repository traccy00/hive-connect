package fpt.edu.capstone.dto.job;

import fpt.edu.capstone.entity.Job;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HomePageData {
    List<Job> remoteJob;
    List<Job> parttimeJob;
    List<Job> fulltimeJob;
    List<Job> popularJob;
    List<Job> urgentJob;
    List<Job> newJob;
    List<Job> jobByFields;
    List<Job> suggestJobs;
}
