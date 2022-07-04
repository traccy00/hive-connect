package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.admin.AdminResponse;
import fpt.edu.capstone.dto.admin.user.AdminManageResponse;
import fpt.edu.capstone.dto.admin.user.CandidateManageResponse;
import fpt.edu.capstone.dto.admin.user.RecruiterManageResponse;
import fpt.edu.capstone.dto.admin.user.ReportedUserResponse;
import fpt.edu.capstone.entity.Admin;
import fpt.edu.capstone.entity.Users;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.AdminRepository;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import lombok.AllArgsConstructor;
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
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    private final UserService userService;

    private final CandidateService candidateService;

    private final RecruiterService recruiterService;

    private final ReportedService reportedService;

    @Override
    public ResponseDataPagination getListAdmin(Integer pageNo, Integer pageSize) throws Exception {
        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);
        Page<Admin> admin = adminRepository.getListAdminByFilter(pageable);
        List<AdminResponse> adminResponse = new ArrayList<>();
        if (admin.hasContent()) {
            for (Admin a : admin.getContent()) {
                long userId = a.getUserId();
                Users user = userService.getUserById(userId);
                AdminResponse ar = AdminResponse.fromEntity(a, user);
                adminResponse.add(ar);
            }
        }
        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(adminResponse);
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(admin.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(admin.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);

        return responseDataPagination;
    }

    @Override
    public Optional<Admin> findAdminByUserId(long userId) {
        return adminRepository.findByUserId(userId);
    }

    @Override
    public void insertAdmin(long userId) {
        adminRepository.insertAdmin(userId);
    }

    @Override
    public Page<AdminManageResponse> searchAdmins(Pageable pageable, String username, String email) {
        return adminRepository.searchAdmin(pageable, username, email);
    }

    @Override
    public ResponseDataPagination searchUsersForAdmin(String selectTab, Integer pageNo, Integer pageSize, String username, String email) {
        ResponseDataPagination responseDataPagination = new ResponseDataPagination();

        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);
        Page users;
        if (selectTab.equals("Recruiter")) {
            users = recruiterService.searchRecruitersForAdmin(pageable, username, email);
        } else if (selectTab.equals("Candidate")) {
            users = candidateService.searchCandidatesForAdmin(pageable, username, email);
        } else if (selectTab.equals("Admin")) {
            users = searchAdmins(pageable, username, email);
        } else {
            throw new HiveConnectException("Please select a tab");
        }

        responseDataPagination.setData(users);

        Pagination pagination = new Pagination();
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(users.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(users.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);

        return responseDataPagination;
    }

    @Override
    public ResponseDataPagination searchReportedUsers(Integer pageNo, Integer pageSize, String username,
                                                      String personReportName, List<Long> userIdList, List<Long> personReportId) {
        ResponseDataPagination responseDataPagination = new ResponseDataPagination();

        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);
        List<Users> users = userService.findAll();
        List<Long> userIdListIfEmpty = users.stream().map(Users::getId).collect(Collectors.toList());
        if ( userIdList == null) {
            userIdList = userIdListIfEmpty;
        }
        if (personReportId == null) {
            personReportId = userIdListIfEmpty;
        }
        Page<ReportedUserResponse> reportedUsers = reportedService.searchReportedUsers(pageable, username,
                personReportName, userIdList, personReportId);

        responseDataPagination.setData(reportedUsers);

        Pagination pagination = new Pagination();
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(reportedUsers.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(reportedUsers.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);

        return responseDataPagination;
    }
}
