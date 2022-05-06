package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.sprint1.Admin;
import fpt.edu.capstone.repository.AdminRepository;
import fpt.edu.capstone.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository;
    @Override
    public List<Admin> getListAdmin() throws Exception {
        return adminRepository.findAll();
    }
}
