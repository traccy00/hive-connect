package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.admin.user.ReportedUserResponse;
import fpt.edu.capstone.entity.Reported;
import fpt.edu.capstone.repository.ReportedRepository;
import fpt.edu.capstone.service.ReportedService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public List<Reported> getAllReported() {
        return reportedRepository.findAll();
    }
}
