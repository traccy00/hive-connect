package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.sprint1.Admin;
import fpt.edu.capstone.utils.ResponseDataPagination;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminService {
    ResponseDataPagination getListAdmin(Integer pageNo, Integer pageSize) throws Exception;
}
