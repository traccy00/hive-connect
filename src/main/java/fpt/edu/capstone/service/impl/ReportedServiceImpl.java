package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.admin.user.ReportedUserResponse;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.job.ReportJobRequest;
import fpt.edu.capstone.dto.job.ReportedJobResponse;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.entity.Recruiter;
import fpt.edu.capstone.entity.Report;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.ReportedRepository;
import fpt.edu.capstone.service.JobService;
import fpt.edu.capstone.service.RecruiterService;
import fpt.edu.capstone.service.ReportedService;
import fpt.edu.capstone.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportedServiceImpl implements ReportedService {

    private final ReportedRepository reportedRepository;

    @Override
    public Page<ReportedUserResponse> searchReportedUsers(Pageable pageable, String username, String personReportName,
                                                          List<Long> userId, List<Long> personReportId) {
        return reportedRepository.searchReportedUsers(pageable, username, personReportName, userId, personReportId);
    }

    @Override
    public void updateReportedStatus(String status, long id) {
        reportedRepository.updateReportedStatus(status, id);
    }

    @Override
    public Page<ReportedJobResponse> searchReportedJob(Pageable pageable, LocalDateTime createdAtFrom, LocalDateTime createAtTo, LocalDateTime updatedAtFrom,
                                                       LocalDateTime updatedAtTo, String jobName) {
        return reportedRepository.searchReportedJob(pageable, jobName);
    }
}
