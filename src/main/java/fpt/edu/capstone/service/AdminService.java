package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.admin.user.AdminManageResponse;
import fpt.edu.capstone.entity.Admin;
import fpt.edu.capstone.utils.ResponseDataPagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    ResponseDataPagination getListAdmin(Integer pageNo, Integer pageSize) throws Exception;

    Optional<Admin> findAdminByUserId(long userId);

    void insertAdmin(long userId);

    Page<AdminManageResponse> searchAdmins(Pageable pageable, String username, String email);

    ResponseDataPagination searchUsersForAdmin(String selectTab, Integer pageNo, Integer pageSize, String username, String email);

    ResponseDataPagination searchReportedUsers(Integer pageNo, Integer pageSize, String username, String personReportName,
                                               List<Long> userId, List<Long> personReportId);
}
