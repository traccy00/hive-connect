package fpt.edu.capstone.controller;

import fpt.edu.capstone.entity.sprint1.Admin;
import fpt.edu.capstone.service.AdminService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @GetMapping("/list-admin")
    @Operation(summary = "Get list admin")
    public ResponseData getListAdmin(){
        try {
            List<Admin> admin = adminService.getListAdmin();
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), admin);
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }
}
