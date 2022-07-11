package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.admin.user.ReportedUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReportedService {

    Page<ReportedUserResponse> searchReportedUsers(Pageable pageable, String username, String personReportName,
                                                   List<Long> userId, List<Long> personReportId);
}
