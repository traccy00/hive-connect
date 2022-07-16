package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.admin.CommonRecruiterInformationResponse;
import fpt.edu.capstone.entity.Recruiter;
import fpt.edu.capstone.entity.Users;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.RecruiterRepository;
import fpt.edu.capstone.repository.UserRepository;
import fpt.edu.capstone.service.AppliedJobService;
import fpt.edu.capstone.service.JobService;
import fpt.edu.capstone.service.RecruiterManageService;
import fpt.edu.capstone.utils.Enums;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RecruiterManageServiceImpl implements RecruiterManageService {

    private final RecruiterRepository recruiterRepository;

    private final UserRepository userRepository;

    private final JobService jobService;

    private final AppliedJobService appliedJobService;

    @Override
    public CommonRecruiterInformationResponse getCommonInforOfRecruiter(long recruiterId) {
        CommonRecruiterInformationResponse response = new CommonRecruiterInformationResponse();
        Optional<Recruiter> optionalRecruiter = recruiterRepository.findById(recruiterId);
        if (!optionalRecruiter.isPresent()) {
            throw new HiveConnectException("Người dùng không tồn tại");
        }
        int step = 0;
        int totalStep = 3;
        Users user = userRepository.getById(optionalRecruiter.get().getUserId());
        if (user == null) {
            throw new HiveConnectException("Liên hệ admin");
        }
        if (user.isVerifiedEmail()) {
            step++;
        }
        if (user.isVerifiedPhone()) {
            step++;
        }
        if (optionalRecruiter.get().getBusinessLicenseApprovalStatus().equals(Enums.ApprovalStatus.APPROVED.getStatus())) {
            step++;
        }
        response.setRecruiterFullName(optionalRecruiter.get().getFullName());
        response.setVerifyStep(step + " / " + totalStep);
        response.setTotalCreatedJob(jobService.countTotalCreatedJobOfRecruiter(recruiterId).getTotalCreatedJob());
        response.setCandidateApplyPercentage(appliedJobService.countApplyPercentage(recruiterId).getTotalApplied()
                / appliedJobService.countApplyPercentage(recruiterId).getNumberRecruits());
        return response;
    }
}
