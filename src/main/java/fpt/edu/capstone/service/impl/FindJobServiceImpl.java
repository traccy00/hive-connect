package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.job.AppliedJobRequest;
import fpt.edu.capstone.dto.job.CandidateAppliedJobResponse;
import fpt.edu.capstone.dto.job.RecruiterPostResponse;
import fpt.edu.capstone.entity.sprint1.AppliedJob;
import fpt.edu.capstone.entity.sprint1.Candidate;
import fpt.edu.capstone.entity.sprint1.Users;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.AppliedJobRepository;
import fpt.edu.capstone.service.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FindJobServiceImpl implements FindJobService {

    private final ModelMapper modelMapper;

    private final AppliedJobRepository appliedJobRepository;

    private final AppliedJobService appliedJobService;

    private final CandidateService candidateService;

    private final JobService jobService;

    private final UserService userService;

    @Override
    public void appliedJob(AppliedJobRequest request) throws Exception {
        if (!jobService.existsById(request.getJobId())) {
            throw new HiveConnectException("Job doesn't exist.");
        }
        if (!candidateService.existsById(request.getCandidateId())) {
            throw new HiveConnectException("Candidate doesn't exist.");
        }
        Object AppliedJobRequest = request;
        //if exists candidate account, candidate has already applied the job
        AppliedJob appliedJob1 = appliedJobService.getAppliedJobBefore(request.getCandidateId(), request.getJobId());
        if (appliedJob1 != null) {
            if (appliedJob1.isApplied()) {
                appliedJob1.setApplied(false);
            } else {
                appliedJob1.setApplied(true);
            }
            appliedJob1.update();
            appliedJobRepository.save(appliedJob1);
        } else {
            AppliedJob appliedJob = modelMapper.map(AppliedJobRequest, AppliedJob.class);
            appliedJob.setApplied(true);
            appliedJob.create();
            appliedJobRepository.save(appliedJob);
        }
    }

    @Override
    public List<CandidateAppliedJobResponse> getCandidateAppliedJobList(long jobId) {
        List<CandidateAppliedJobResponse> responseList = new ArrayList<>();
        CandidateAppliedJobResponse responseObj = new CandidateAppliedJobResponse();
        List<AppliedJob> appliedJobs = null;//appliedJobService.getListCandidateAppliedJob(jobId);
        responseObj.setJobId(jobId);
        for (AppliedJob appliedJob : appliedJobs) {
            Candidate candidate = candidateService.getById(appliedJob.getCandidateId());
            responseObj.setCandidateId(appliedJob.getCandidateId());
            responseObj.setCandidateName(candidate.getFullName());
            Users user = userService.findById(candidate.getUserId());
            responseObj.setAvatar(user.getAvatar());
//            responseObj.setExperienceYear();
//            responseObj.setCareerGoal();
            responseList.add(responseObj);
        }
        //responseObj.setRowCount();

        return null;
    }

    @Override
    public List<RecruiterPostResponse> getRecruiterPosts(long recruiterId) {
        return null;
    }
}
