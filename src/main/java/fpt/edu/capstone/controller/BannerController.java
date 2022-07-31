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
import fpt.edu.capstone.utils.ResponseDataPagination;
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
    @Operation(summary = "Admin module - Create new banner package on Manage Banner screen")
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

    @Operation(summary = "Admin module - Get list all banner package on Manage Banner screen")
    @GetMapping("/get-all-banner-package")
    public ResponseData getAllBanner(@RequestParam(value = "name", required = false) String title,
                                     @RequestParam(value = "status", required = false) boolean isDeleted,
                                     @RequestParam(defaultValue = "1") Integer pageNo,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            ResponseDataPagination pagination = bannerService.getBannerByFilter(pageNo, pageSize, title, isDeleted);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, pagination);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @PutMapping("/update-banner-package")
    @Operation(summary = "Admin module - Update banner package on Manage Banner screen")
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

    @DeleteMapping
    @Operation(summary = "Admin module - Delete a banner package of banner group")
    public ResponseData deleteBanner(@RequestParam long bannerPackageId) {
        try {
            bannerService.deleteBanner(bannerPackageId);
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

    @GetMapping("/get-detail-purchased-package/{recruiterId}")
    public ResponseData getDetailPurchasedPackage(@PathVariable long recruiterId,
                                                  @RequestParam long purchasedPackageId) {
        try {
            recruiterManageService.getDetailPurchasedPackage(recruiterId, purchasedPackageId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS);
        }catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }


    @GetMapping("/get-banner-for-candidate")
    @Operation(summary = "Candidate module - Display banner for candidate")
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
