package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.CV.CVRequest;
import fpt.edu.capstone.dto.CV.CVResponse;
import fpt.edu.capstone.dto.candidate.AppliedJobCandidateResponse;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.job.JobResponse;
import fpt.edu.capstone.entity.*;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseData;
import fpt.edu.capstone.utils.ResponseDataPagination;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CandidateManageServiceImpl implements CandidateManageService {

    private final ModelMapper modelMapper;

    private final AppliedJobService appliedJobService;

    private final CandidateService candidateService;

    private final JobService jobService;

    private final CompanyService companyService;

    private final JobHashTagService jobHashTagService;

    private final CVService cvService;

    private final WorkExperienceService workExperienceService;

    private final MajorLevelService majorLevelService;

    private final OtherSkillService otherSkillService;

    private final LanguageService languageService;

    private final CertificateService certificateService;

    private final EducationService educationService;

    @Override
    public ResponseDataPagination searchAppliedJobsOfCandidate(Integer pageNo, Integer pageSize, long candidateId, String approvalStatus) {
        if (!candidateService.existsById(candidateId)) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        List<AppliedJobCandidateResponse> responseList = new ArrayList<>();

        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);

        Page<AppliedJob> appliedJobs = appliedJobService.searchAppliedJobsOfCandidate(pageable, candidateId, approvalStatus);
        if (appliedJobs.hasContent()) {
            for (AppliedJob appliedJob : appliedJobs) {
                AppliedJobCandidateResponse response = new AppliedJobCandidateResponse();
                response.setAppliedJobId(appliedJob.getId());
                response.setJob(jobService.getJobById(appliedJob.getJobId()));
                response.setApprovalStatus(appliedJob.getApprovalStatus());
                response.setAppliedJobDate(appliedJob.getCreatedAt());
                if (appliedJob.getApprovalStatus().equals(Enums.ApprovalStatus.APPROVED.getStatus())) {
                    response.setApprovalDate(appliedJob.getUpdatedAt());
                }
                responseList.add(response);
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

    public ResponseDataPagination getJobsOfCompany(Integer pageNo, Integer pageSize, long companyId) {
        List<JobResponse> responseList = new ArrayList<>();
        Page<Job> jobs = jobService.getJobByCompanyId(pageNo, pageSize, companyId);
        if (jobs.hasContent()) {
            for (Job job : jobs) {
                JobResponse jobResponse = new JobResponse();
                jobResponse.setJobId(job.getId());
                jobResponse.setCompanyId(job.getCompanyId());
                jobResponse.setRecruiterId(job.getRecruiterId());
                List<JobHashtag> listJobHashTag = jobHashTagService.getHashTagOfJob(job.getId());
                if (!(listJobHashTag.isEmpty() && listJobHashTag == null)) {
                    List<String> hashTagNameList = listJobHashTag.stream().map(JobHashtag::getHashTagName).collect(Collectors.toList());
                    jobResponse.setListHashtag(hashTagNameList);
                }
                Company company = companyService.getCompanyById(job.getCompanyId());
                if (company != null) {
                    jobResponse.setCompanyName(company.getName());
                }
                jobResponse.setJobName(job.getJobName());
                jobResponse.setJobDescription(job.getJobDescription());
                jobResponse.setJobRequirement(job.getJobRequirement());
                jobResponse.setBenefit(job.getBenefit());
                jobResponse.setFromSalary(job.getFromSalary());
                jobResponse.setToSalary(job.getToSalary());
                jobResponse.setNumberRecruits(job.getNumberRecruits());
                jobResponse.setRank(job.getRank());
                jobResponse.setWorkForm(job.getWorkForm());
                jobResponse.setGender(job.isGender());
                jobResponse.setStartDate(job.getStartDate());
                jobResponse.setEndDate(job.getEndDate());
                jobResponse.setWorkPlace(job.getWorkPlace());
                jobResponse.setCreatedAt(job.getCreatedAt());
                jobResponse.setUpdatedAt(job.getUpdatedAt());
                jobResponse.setPopularJob(job.isPopularJob());
                jobResponse.setNewJob(job.isNewJob());
                jobResponse.setUrgentJob(job.isUrgentJob());
                responseList.add(jobResponse);
            }
        }
        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(responseList);
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(jobs.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(jobs.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;
    }

    @Override
    public CVResponse findCvByCandidateId(long candidateId) {
        List<CV> cv = cvService.findCvByCandidateId(candidateId);
        if (cv == null || cv.isEmpty()) {
            throw new HiveConnectException(ResponseMessageConstants.CV_NOT_EXIST);
        }
        List<Certificate> certificates = certificateService.getListCertificateByCvId(cv.get(0).getId());
        List<Education> educations = educationService.getListEducationByCvId(cv.get(0).getId());
        List<Language> languages = languageService.getListLanguageByCvId(cv.get(0).getId());
        List<MajorLevel> majorLevels = majorLevelService.getListMajorLevelByCvId(cv.get(0).getId());
        List<OtherSkill> otherSkills = otherSkillService.getListOtherSkillByCvId(cv.get(0).getId());
        List<WorkExperience> workExperiences = workExperienceService.getListWorkExperienceByCvId(cv.get(0).getId());
        CVResponse cvResponse = new CVResponse();
        cvResponse.setCandidateId(candidateId);
        cvResponse.setCreatedAt(cv.get(0).getCreatedAt());
        cvResponse.setUpdatedAt(cv.get(0).getUpdatedAt());
        cvResponse.setCertificates(certificates);
        cvResponse.setIsDeleted(cv.get(0).getIsDeleted());
        cvResponse.setEducations(educations);
        cvResponse.setId(cv.get(0).getId());
        cvResponse.setLanguages(languages);
        cvResponse.setSummary(cv.get(0).getSummary());
        cvResponse.setMajorLevels(majorLevels);
        cvResponse.setOtherSkills(otherSkills);
        cvResponse.setWorkExperiences(workExperiences);
        return cvResponse;
    }

    @Override
    public CV createCV(CVRequest request) {
        Candidate c = candidateService.getCandidateById(request.getCandidateId());
        if (c == null) {
            throw new HiveConnectException(ResponseMessageConstants.CANDIDATE_DOES_NOT_EXIST);
        }
        List<CV> cvList = cvService.findCvByCandidateId(request.getCandidateId());
        if (!cvList.isEmpty()) {
            throw new HiveConnectException(ResponseMessageConstants.YOUR_CV_EXISTED);
        }
        CV cv = modelMapper.map(request, CV.class);
        cv.create();
        cv.setIsDeleted(0);
        cvService.save(cv);
        return cv;
    }
}
