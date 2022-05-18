package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.admin.AdminResponse;
import fpt.edu.capstone.entity.sprint1.Admin;
import fpt.edu.capstone.entity.sprint1.Users;
import fpt.edu.capstone.repository.AdminRepository;
import fpt.edu.capstone.service.AdminService;
import fpt.edu.capstone.service.UserService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserService userService;

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
}
