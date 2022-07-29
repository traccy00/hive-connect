package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.banner.ConfigBannerRequest;
import fpt.edu.capstone.dto.banner.UpdateBannerRequest;
import fpt.edu.capstone.dto.banner.UploadBannerRequest;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.entity.Banner;
import fpt.edu.capstone.entity.BannerActive;
import fpt.edu.capstone.service.BannerActiveService;
import fpt.edu.capstone.service.BannerService;
import fpt.edu.capstone.service.RecruiterManageService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.LogUtils;
import fpt.edu.capstone.utils.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/banner")
@AllArgsConstructor
public class BannerController {

    private static final Logger logger = LoggerFactory.getLogger(BannerController.class);

    private final BannerService bannerService;

    private final BannerActiveService bannerActiveService;

    private final RecruiterManageService recruiterManageService;

    //config cho các gói banner
    @Operation(summary = "Create new banner package on Manage Banner screen - Admin module")
    @PostMapping("/config-banner")
    public ResponseData insertBanner(@RequestBody ConfigBannerRequest request) {
        try {
            Banner banner = bannerService.insertBanner(request);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, banner);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @Operation(summary = "Get list all banner package on Manage Banner screen - Admin module")
    @GetMapping("/get-all-banner-package")
    public ResponseData getAllBanner() {
        try {
            List<Banner> banners = bannerService.getAllBanner();
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, banners);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @PutMapping("/update-banner-package")
    @Operation(summary = "Update banner package on Manage Banner screen - Admin module")
    public ResponseData updateBanner(UpdateBannerRequest request) {
        try {
            bannerService.updateBanner(request);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @PutMapping("/upload-banner/{recruiterId}")
    @Operation(summary = "Recruiter module - recruiter upload banner image after bought a banner package")
    public ResponseData uploadBannerForPayment(@PathVariable long recruiterId, @RequestBody UploadBannerRequest request) {
        try {
            recruiterManageService.uploadBanner(recruiterId, request);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS);
        }catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
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

    @GetMapping("/get-banner-for-candidate")
    @Operation(summary = "Display banner for candidate")
    public ResponseData displayBannerForCandidate(@RequestParam String displayPosition) {
        try {
            List<BannerActive> bannerActives = bannerActiveService.getBannersByPosition(displayPosition);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, bannerActives);
        }catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }


}
