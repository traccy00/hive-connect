package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.admin.UserStatusRequest;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.register.CountRegisterUserResponse;
import fpt.edu.capstone.entity.Banner;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.entity.Reported;
import fpt.edu.capstone.entity.Users;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.LogUtils;
import fpt.edu.capstone.utils.ResponseData;
import fpt.edu.capstone.utils.ResponseDataPagination;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final AdminService adminService;

    private final CandidateService candidateService;

    private final BannerService bannerService;

    private final JobService jobService;

    private final ReportedService reportedService;

    private final PaymentService paymentService;

    private final LocalDateTime minDate = LocalDateTime.MIN;
    private final LocalDateTime maxDate = LocalDateTime.MAX;

    private final UserService userService;

    @GetMapping("/list-admin")
    @Operation(summary = "Get list admin")
    public ResponseData getListAdmin(@RequestParam(defaultValue = "0") Integer pageNo,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        //search by name, email, ...
        try {
            ResponseDataPagination pagination = adminService.getListAdmin(pageNo, pageSize);
            return pagination;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @GetMapping("/search-users")
    @Operation(summary = "search/get list users account (Recruiter, Candidate, Admin) for Admin App")
    public ResponseData searchCandidatesForAdmin(@RequestParam(defaultValue = "0") Integer pageNo,
                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(required = false) String username,
                                                 @RequestParam(required = false) String email,
                                                 @RequestParam String tab) {
        try {
            ResponseDataPagination pagination = adminService.searchUsersForAdmin(tab, pageNo, pageSize, username, email);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, pagination);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @GetMapping("/search-reported-users")
    @Operation(summary = "search/get list reported users for Admin")
    public ResponseData searchReportedUsers(@RequestParam(defaultValue = "0") Integer pageNo,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            @RequestParam(required = false) String username,
                                            @RequestParam(required = false) String personReportName,
                                            @RequestParam(required = false) @Size(max = 1) List<Long> userId,
                                            @RequestParam(required = false) @Size(max = 1) List<Long> personReportId) {
        try {
            ResponseDataPagination pagination = adminService.searchReportedUsers(pageNo, pageSize, username,
                    personReportName, userId, personReportId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, pagination);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/count-users")
    public ResponseData countUsers() {
        try {
            HashMap<String, List<CountRegisterUserResponse>> responseHashMap = userService.countUser();
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, responseHashMap);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/get-all-banner")
    public ResponseData getAllBanner() {
        try {
            List<Banner> banners = bannerService.getAllBanner();
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Successful", banners);
        } catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
    }

    @PutMapping("/update-banner")
    public ResponseData updateBanner(Banner newBanner) {
        try {
            bannerService.updateBanner(newBanner);
            Optional<Banner> banner = bannerService.findById(newBanner.getId());
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Successful", banner.get());
        } catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
    }

    @PostMapping("/insert-banner")
    public ResponseData insertBanner(Banner newBanner) {
        try {
            Banner banner = bannerService.insertBanner(newBanner);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Successful", banner);
        } catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
    }

    //    @GetMapping("/search-banner")
//    public ResponseData searchReportedUsers(
//            @RequestParam(defaultValue = "true", required = false) String screen,
//            @RequestParam(required = false)  @DateTimeFormat(pattern = "dd/MM/yyyy")
//                    LocalDate minDate,
//            @RequestParam(required = false)  @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate maxDate
//
//    ) {
//        try {
//             = screen + "=true";
//            List<Banner> banners = bannerService.searchByFiler(screen, LocalDateTime.of(minDate, LocalTime.MIN), LocalDateTime.of(maxDate, LocalTime.MAX));
//            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, banners);
//        } catch (Exception e) {
//            String msg = LogUtils.printLogStackTrace(e);
//            logger.error(msg);
//            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
//        }
//    }
    @PutMapping("/update-reported")
    public ResponseData updateReportedStatus(@RequestParam String status,
                                             @RequestParam(defaultValue = "-1", required = false) long userId,
                                             @RequestParam(defaultValue = "-1", required = false) long postId,
                                             @RequestParam long reportId) {
        try {
            if (status.toLowerCase().equals("delete")) {
                Optional<Job> jobOptional = jobService.findById(postId);
                if (jobOptional.isPresent()) {
                    jobService.updateIsDeleted(1, jobOptional.get().getId());
                    reportedService.updateReportedStatus("deleted", reportId);
                    return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Delete post successful", jobOptional.get());
                } else {
                    //co can xu ly user khong ?
                    return null;
                }

            } else {
                reportedService.updateReportedStatus("Cancel", reportId);
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Cancel report successful", null);
            }
        } catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
    }

    @GetMapping("/get-all-reported")
    public ResponseData getAllReported() {
        try {
            List<Reported> reporteds = reportedService.getAllReported();
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Get All Successful", reporteds);
        } catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
    }

    @PutMapping("/lock-unlock-user")
    public ResponseData lockUnlockUser(@RequestParam long userId) {
        try {
            Users updateUser = userService.lockUnlockUser(userId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, updateUser);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @PutMapping("/active-deactive-user")
    public ResponseData activeDeactiveUser(@RequestParam long userId) {
        try {
            Users updateUser = userService.activeDeactiveUser(userId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, updateUser);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @GetMapping("/get-list-pending-license")
    @Operation(summary = "Get list pending license for Admin approval")
    public ResponseData getPendingLicenses() {
        //một record sẽ có hai dòng business license và addition license
        try {
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @PutMapping("approve-license")
    @Operation(summary = "Admin approve license")
    public ResponseData approvaLicense() {
        try {
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }
}
