package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.Admin;
import fpt.edu.capstone.utils.ResponseDataPagination;

import java.util.Optional;

public interface AdminService {
    ResponseDataPagination getListAdmin(Integer pageNo, Integer pageSize) throws Exception;

    Optional <Admin> findAdminByUserId(long userId);

    void insertAdmin(long userId);
}
