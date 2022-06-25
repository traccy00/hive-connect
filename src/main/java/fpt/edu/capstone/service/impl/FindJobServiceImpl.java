package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.job.AppliedJobRequest;
import fpt.edu.capstone.dto.job.CvAppliedJobResponse;
import fpt.edu.capstone.dto.job.RecruiterPostResponse;
import fpt.edu.capstone.entity.*;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.AppliedJobRepository;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.Enums;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FindJobServiceImpl implements FindJobService {

    private final ModelMapper modelMapper;

    private final AppliedJobRepository appliedJobRepository;

    private final AppliedJobService appliedJobService;

    private final CandidateService candidateService;

    private final JobService jobService;

    private final UserService userService;

    private final WorkExperienceService workExperienceService;

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
            appliedJob1.setApprovalStatus(Enums.ApprovalStatus.PENDING.getStatus());
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
    public List<CvAppliedJobResponse> getCvListAppliedJob(long jobId) {
        List<CvAppliedJobResponse> responseList = new ArrayList<>();

        Optional<Job> job = jobService.findById(jobId);
        if(!job.isPresent()) {
            throw new HiveConnectException("Job doesn't exist");
        }
        List<AppliedJob> appliedJobs = appliedJobService.getCvAppliedJob(jobId, true);

        CvAppliedJobResponse responseObj = new CvAppliedJobResponse();
        responseObj.setJobId(jobId);
        for (AppliedJob appliedJob : appliedJobs) {
            Candidate candidate = candidateService.getById(appliedJob.getCandidateId());
            responseObj.setCandidateId(appliedJob.getCandidateId());
            responseObj.setCandidateName(candidate.getFullName());

            Users user = userService.findById(candidate.getUserId());
            responseObj.setAvatar(user.getAvatar());


//            List<WorkExperience> workExperiencesOfCv = workExperienceService.getListWorkExperienceByCvId();
//            List<String> experienceDesc = workExperiencesOfCv.stream().map(WorkExperience::getPosition).collect(Collectors.toList());
//            responseObj.setExperienceDesc();
            responseList.add(responseObj);
        }
        return responseList;
    }

    @Override
    public List<RecruiterPostResponse> getRecruiterPosts(long recruiterId) {
        return null;
    }
}
