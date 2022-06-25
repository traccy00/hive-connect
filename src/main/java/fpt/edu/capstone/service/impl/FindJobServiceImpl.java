package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.job.AppliedJobRequest;
import fpt.edu.capstone.dto.job.CvAppliedJobResponse;
import fpt.edu.capstone.dto.job.RecruiterPostResponse;
import fpt.edu.capstone.entity.*;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.AppliedJobRepository;
import fpt.edu.capstone.repository.CVRepository;
import fpt.edu.capstone.repository.EducationRepository;
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

    private final CVRepository cvRepository;

    private final EducationRepository educationRepository;

    @Override
    public void appliedJob(AppliedJobRequest request) throws Exception {
        if (!jobService.existsById(request.getJobId())) {
            throw new HiveConnectException("Job doesn't exist.");
        }
        if (!candidateService.existsById(request.getCandidateId())) {
            throw new HiveConnectException("Candidate doesn't exist.");
        }
        //if exists candidate account, candidate has already applied the job
        AppliedJob appliedJob1 = appliedJobService.getAppliedJobBefore(request.getCandidateId(), request.getJobId());
        if (appliedJob1 != null) {
            if (appliedJob1.isApplied()) {
                if(appliedJob1.getApprovalStatus().equals(Enums.ApprovalStatus.PENDING.getStatus())) {
                    appliedJob1.setApplied(false);
                } else if(appliedJob1.getApprovalStatus().equals(Enums.ApprovalStatus.APPROVED.getStatus())) {
                    throw new HiveConnectException("This CV has been approved");
                } else if (appliedJob1.getApprovalStatus().equals(Enums.ApprovalStatus.REJECT.getStatus())) {
                    Object AppliedJobRequest = request;
                    AppliedJob appliedJob = modelMapper.map(AppliedJobRequest, AppliedJob.class);
                    appliedJob.setApplied(true);
                    appliedJob.setApprovalStatus(Enums.ApprovalStatus.PENDING.getStatus());
                    appliedJob.create();
                    appliedJobRepository.save(appliedJob);
                    return;
                }
            } else {
                appliedJob1.setApplied(true);
            }
            appliedJob1.setApprovalStatus(Enums.ApprovalStatus.PENDING.getStatus());
            appliedJob1.update();
            appliedJobRepository.save(appliedJob1);
        } else {
            Object AppliedJobRequest = request;
            AppliedJob appliedJob = modelMapper.map(AppliedJobRequest, AppliedJob.class);
            appliedJob.setApplied(true);
            appliedJob.setApprovalStatus(Enums.ApprovalStatus.PENDING.getStatus());
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
        if(appliedJobs.isEmpty()) {
            throw new HiveConnectException("No CV applies");
        }
        CvAppliedJobResponse responseObj = new CvAppliedJobResponse();
        responseObj.setJobId(jobId);
        for (AppliedJob appliedJob : appliedJobs) {
            Candidate candidate = candidateService.getById(appliedJob.getCandidateId());
            responseObj.setCandidateId(appliedJob.getCandidateId());
            responseObj.setCandidateName(candidate.getFullName());

            Users user = userService.findById(candidate.getUserId());
            responseObj.setAvatar(user.getAvatar());
            if(appliedJob.isUploadCv()) {
                //upload CV
                responseObj.setCvUrl(appliedJob.getCvUploadUrl());
            }
            CV cv = cvRepository.getByCandidateId(appliedJob.getCandidateId());
            if(cv == null) {
                if(!appliedJob.isUploadCv()) {
                    //Profile không tồn tại mà
                    throw new HiveConnectException("Please try to contact administrator");
                }
            }
            List<WorkExperience> workExperiencesOfCv = workExperienceService.getListWorkExperienceByCvId(cv.getId());
            if(!workExperiencesOfCv.isEmpty()) {
                List<String> experienceDesc = workExperiencesOfCv.stream().map(WorkExperience::getPosition).collect(Collectors.toList());
                responseObj.setExperienceDesc(experienceDesc);
            }
            List<Education> educations = educationRepository.getListEducationByCvId(cv.getId());
            if(!educations.isEmpty()) {
                List<String> schools = educations.stream().map(Education::getSchool).collect(Collectors.toList());
                responseObj.setEducations(schools);
            }
            responseObj.setCareerGoal(candidate.getIntroduction());
            responseObj.setAddress(candidate.getAddress());
//            responseObj.setExperienceYear();

            responseObj.setApprovalStatus(appliedJob.getApprovalStatus());
            responseList.add(responseObj);
        }
        return responseList;
    }

    @Override
    public List<RecruiterPostResponse> getRecruiterPosts(long recruiterId) {
        return null;
    }
}
