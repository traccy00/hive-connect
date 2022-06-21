package fpt.edu.capstone.service;

import fpt.edu.capstone.utils.ResponseDataPagination;

public interface AdminService {
    ResponseDataPagination getListAdmin(Integer pageNo, Integer pageSize) throws Exception;
}
