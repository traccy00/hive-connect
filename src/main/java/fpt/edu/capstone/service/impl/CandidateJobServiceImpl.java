package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.job.JobResponse;
//import fpt.edu.capstone.entity.Career;
import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.entity.JobHashtag;
import fpt.edu.capstone.repository.JobRepository;
import fpt.edu.capstone.service.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CandidateJobServiceImpl implements CandidateJobService {

    private final ModelMapper modelMapper;

    private final JobService jobService;

    private final JobRepository jobRepository;

//    private final CareerService careerService;

//    private final JobHashTagService jobHashTagService;

    private final CompanyService companyService;

    @Override
    public List<JobResponse> getNewestJob() {
        List<JobResponse> responseList = new ArrayList<>();
        List<Job> jobs = jobRepository.getNewestJob(true, 0);
        JobResponse jobResponse = new JobResponse();
        for (Job job : jobs) {
//            JobResponse jobResponse = modelMapper.map(job, JobResponse.class);
//            responseList.add(jobResponse);
            jobResponse.setJobId(job.getId());
            jobResponse.setCompanyId(job.getCompanyId());
            jobResponse.setRecruiterId(job.getRecruiterId());
            jobResponse.setCareerId(job.getFieldId());
//            Career career = careerService.getCareerById(job.getCareerId());
//            jobResponse.setCareerName(career.getJobType());
//            List<JobHashtag> listJobHashTag = jobHashTagService.getHashTagOfJob(job.getId());
//            List<String> hashTagIdList = listJobHashTag.stream().map(JobHashtag::getHashTagName).collect(Collectors.toList());
//            jobResponse.setListHashtag(hashTagIdList);
            Company company = companyService.getCompanyById(job.getCompanyId());
            if(company != null) {
                jobResponse.setCompanyName(company.getName());
            }
            jobResponse.setJobName(job.getJobName());
            jobResponse.setJobDescription(job.getJobDescription());
            jobResponse.setJobRequirement(job.getJobRequirement());
            jobResponse.setBenefit(job.getBenifit());
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
        return responseList;
    }
}
