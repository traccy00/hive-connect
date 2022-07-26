package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
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
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private final ImageService imageService;

    private final WorkExperienceService workExperienceService;

    private final CVRepository cvRepository;

    private final EducationRepository educationRepository;

    private final CVService cvService;

    @Override
    public void appliedJob(AppliedJobRequest request) throws Exception {
        if (request.getCvUrl() == null || request.getCvUrl().trim().isEmpty()) {
            //profile apply
            CV cv = cvService.getCVByCandidateId(request.getCandidateId());
            if (cv == null) {
                throw new HiveConnectException("Create your profile to apply job!");
            }
        }
        if (!jobService.existsById(request.getJobId())) {
            throw new HiveConnectException(ResponseMessageConstants.JOB_DOES_NOT_EXIST);
        }
        if (!candidateService.existsById(request.getCandidateId())) {
            throw new HiveConnectException("Candidate doesn't exist.");
        }
        //if exists candidate account, candidate has already applied the job
        AppliedJob appliedJob1 = appliedJobService.getAppliedJobBefore(request.getCandidateId(), request.getJobId());
        // từng apply, có record tồn tại
        if (appliedJob1 != null) {
            //đâ apply
            if (appliedJob1.isApplied()) {
                //đã apply và đang ở trạng thái pending, có thể hủy apply
                if (appliedJob1.getApprovalStatus().equals(Enums.ApprovalStatus.PENDING.getStatus())) {
                    appliedJob1.setApplied(false);
                    //đã apply và được approved
                } else if (appliedJob1.getApprovalStatus().equals(Enums.ApprovalStatus.APPROVED.getStatus())) {
                    throw new HiveConnectException("This CV has been approved");
                    //đã từng apply và bị reject, có thể apply lại
                } else if (appliedJob1.getApprovalStatus().equals(Enums.ApprovalStatus.REJECT.getStatus())) {
                    Object AppliedJobRequest = request;
                    AppliedJob appliedJob = modelMapper.map(AppliedJobRequest, AppliedJob.class);
                    appliedJob.setApplied(true);
                    appliedJob.setApprovalStatus(Enums.ApprovalStatus.PENDING.getStatus());
                    if(request.getCvUrl() != null) {
                        appliedJob.setCvUploadUrl(request.getCvUrl());
                        appliedJob.setUploadCv(true);
                    }
                    appliedJob.create();
                    appliedJobRepository.save(appliedJob);
                    return;
                }
                //đã hủy apply, cập nhật lại thành apply
            } else {
                appliedJob1.setApplied(true);
            }
            appliedJob1.setApprovalStatus(Enums.ApprovalStatus.PENDING.getStatus());
            appliedJob1.update();
            appliedJobRepository.save(appliedJob1);
            //chưa từng apply
        } else {
            Object AppliedJobRequest = request;
            AppliedJob appliedJob = modelMapper.map(AppliedJobRequest, AppliedJob.class);
            appliedJob.setApplied(true);
            appliedJob.setApprovalStatus(Enums.ApprovalStatus.PENDING.getStatus());
            if(request.getCvUrl() != null) {
                appliedJob.setCvUploadUrl(request.getCvUrl());
                appliedJob.setUploadCv(true);
            }
            appliedJob.create();
            appliedJobRepository.save(appliedJob);
        }
    }

    @Override
    public ResponseDataPagination getCvListAppliedJob(Integer pageNo, Integer pageSize, long jobId) {
        List<CvAppliedJobResponse> responseList = new ArrayList<>();

        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);

        Optional<Job> job = jobService.findById(jobId);
        if (!job.isPresent()) {
            throw new HiveConnectException(ResponseMessageConstants.JOB_DOES_NOT_EXIST);
        }
        Page<AppliedJob> appliedJobs = appliedJobService.getCvAppliedJob(pageable, jobId, true);
        if (appliedJobs.isEmpty()) {
            throw new HiveConnectException("Không có CV nào ứng tuyển.");
        }
        CvAppliedJobResponse responseObj = new CvAppliedJobResponse();
        responseObj.setJobId(jobId);
        if (appliedJobs.hasContent()) {
            for (AppliedJob appliedJob : appliedJobs) {
                Candidate candidate = candidateService.getById(appliedJob.getCandidateId());
                responseObj.setCandidateId(appliedJob.getCandidateId());
                responseObj.setCandidateName(candidate.getFullName());

                Image image = imageService.getAvatarCandidate(candidate.getId());
                if(image != null) {
                    responseObj.setAvatar(image.getUrl());
                }
                if (appliedJob.isUploadCv()) {
                    //upload CV
                    responseObj.setCvUrl(appliedJob.getCvUploadUrl());
                }
                CV cv = cvRepository.getByCandidateId(appliedJob.getCandidateId());
                if (cv == null) {
                    if (!appliedJob.isUploadCv()) {
                        //Profile không tồn tại mà cũng không upload CV
                        throw new HiveConnectException("Liên hệ admin");
                    }
                }
                List<WorkExperience> workExperiencesOfCv = workExperienceService.getListWorkExperienceByCvId(cv.getId());
                if (!workExperiencesOfCv.isEmpty()) {
                    List<String> experienceDesc = workExperiencesOfCv.stream().map(WorkExperience::getPosition).collect(Collectors.toList());
                    responseObj.setExperienceDesc(experienceDesc);
                }
                List<Education> educations = educationRepository.getListEducationByCvId(cv.getId());
                if (!educations.isEmpty()) {
                    List<String> schools = educations.stream().map(Education::getSchool).collect(Collectors.toList());
                    responseObj.setEducations(schools);
                }
                responseObj.setCareerGoal(candidate.getIntroduction());
                responseObj.setAddress(candidate.getAddress());
//            responseObj.setExperienceYear();

                responseObj.setApprovalStatus(appliedJob.getApprovalStatus());
                responseList.add(responseObj);
            }
        }
        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(responseList);
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(appliedJobs.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(appliedJobs.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;
    }

    @Override
    public List<RecruiterPostResponse> getRecruiterPosts(long recruiterId) {
        return null;
    }
}
