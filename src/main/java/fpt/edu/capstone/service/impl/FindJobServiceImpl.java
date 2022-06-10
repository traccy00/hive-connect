package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.sprint1.AppliedJob;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.AppliedJobRepository;
import fpt.edu.capstone.service.CandidateService;
import fpt.edu.capstone.service.FindJobService;
import fpt.edu.capstone.service.JobService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindJobServiceImpl implements FindJobService {

    @Autowired
    private AppliedJobRepository appliedJobRepository;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private JobService jobService;

    @Override
    public void appliedJob(AppliedJob appliedJob) throws Exception {
        if(StringUtils.isEmpty(String.valueOf(appliedJob.getCandidateId()))) {
            throw new HiveConnectException("Data is invalid");
        }
        if(!jobService.existsById(appliedJob.getJobId())) {
            throw new HiveConnectException("Job doesn't exist.");
        }
        if(!candidateService.existsById(appliedJob.getCandidateId())) {
            throw new HiveConnectException("Candidate doesn't exist.");
        }
        //if exists candidate account, candidate has already applied the job
        if(appliedJobRepository.existsByCandidateId(appliedJob.getCandidateId())) {
            appliedJob.setApplied(false);
            appliedJob.update();
        } else {
            appliedJob.setApplied(true);
        }
        appliedJob.create();
        appliedJobRepository.save(appliedJob);
    }
}
