package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.sprint1.AppliedJob;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AppliedJobService {

    void appliedJob(AppliedJob appliedJob) throws Exception;

    List<AppliedJob> getListCandidateAppliedJob(long jobId);

}
