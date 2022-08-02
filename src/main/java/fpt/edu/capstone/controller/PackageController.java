package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.detail_package.CreateOpenCvPackageRequest;
import fpt.edu.capstone.dto.detail_package.DetailPackageRequest;
import fpt.edu.capstone.dto.rental_package.RentalPackageRequest;
import fpt.edu.capstone.entity.DetailPackage;
import fpt.edu.capstone.entity.RentalPackage;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.service.DetailPackageService;
import fpt.edu.capstone.service.RentalPackageService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.LogUtils;
import fpt.edu.capstone.utils.ResponseData;
import fpt.edu.capstone.utils.ResponseDataPagination;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/package")
@AllArgsConstructor
public class PackageController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private final DetailPackageService detailPackageService;

    private final RentalPackageService rentalPackageService;

    private final ModelMapper modelMapper;

    @GetMapping("/list-package")
    public ResponseData getListDetailPackage(@RequestParam(defaultValue = "1") Integer pageNo,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             @RequestParam(value = "name", defaultValue = "") String name ,
                                             @RequestParam(value = "rentalPackageId", defaultValue = "0", required = false) long rentalPackageId,
                                             @RequestParam(value = "status",required = false) boolean isDeleted){
        try {
            ResponseDataPagination pagination = detailPackageService.getListDetailPackageFilter(pageNo, pageSize, name, rentalPackageId, isDeleted);

            return new ResponseData(Enums.ResponseStatus.SUCCESS, ResponseMessageConstants.SUCCESS,pagination);
        }catch (Exception e){
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @GetMapping("/package/{id}")
    public ResponseData getDetailPackage(@PathVariable(value = "id") long id){
        try {
            DetailPackage dp =  detailPackageService.findById(id);
            return new ResponseData(Enums.ResponseStatus.SUCCESS, ResponseMessageConstants.SUCCESS,dp);
        }catch (Exception e){
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @GetMapping("/list-rental-package")
    @Operation (summary = "api này trả về cặp key-value để khi " +
            "admin tạo 1 gói quảng cáo thì sẽ  phân loại thuộc 1 trong nhóm group package này")
    public ResponseData getListGroupPackage(){
        try {
            List<RentalPackage> dp =  rentalPackageService.findAll();
            return new ResponseData(Enums.ResponseStatus.SUCCESS, ResponseMessageConstants.SUCCESS,dp);
        }catch (Exception e){
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @PostMapping("/create-main-package")
    @Operation(summary = "Admin module - tạo gói DỊCH VỤ CHÍNH")
    public ResponseData createSubPackage(@RequestBody DetailPackageRequest request){
        try {
            if(!rentalPackageService.existById(request.getRentalPackageId())){
                throw new HiveConnectException(ResponseMessageConstants.PAYMENT_DOES_NOT_EXIST);
            }
            DetailPackage p = modelMapper.map(request, DetailPackage.class);
            p.setDeleted(false);
            p.create();
            detailPackageService.saveDetailPackage(p);
            return new ResponseData(Enums.ResponseStatus.SUCCESS, ResponseMessageConstants.SUCCESS);
        }catch (Exception e){
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @PostMapping("/create-open-cv-package")
    @Operation(summary = "Admin module - tạo gói DỊCH VỤ MỞ HỒ SƠ ỨNG VIÊN")
    public ResponseData createOpenCvPackage(@RequestBody CreateOpenCvPackageRequest request){
        try {
            detailPackageService.createOpenCvPackage(request);
            return new ResponseData(Enums.ResponseStatus.SUCCESS, ResponseMessageConstants.SUCCESS);
        }catch (Exception e){
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }


    @PutMapping("/update-new-package")
    @Operation(summary = "chỉnh sửa dịch vụ con")
    public ResponseData updateSubPackage(@RequestBody DetailPackage request){
        try {
            detailPackageService.updateDetailPackage(request);
            return new ResponseData(Enums.ResponseStatus.SUCCESS, ResponseMessageConstants.SUCCESS);
        }catch (Exception e){
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @PostMapping("/create-rental-package")
    @Operation(summary = "tạo nhóm dịch vụ")
    public ResponseData createGroupPackage(@RequestBody RentalPackageRequest request){
        try {
            if(rentalPackageService.existByName(request.getPackageGroup())){
                throw new HiveConnectException("Nhóm dịch vụ đã tồn tại. Vui lòng đặt tên khác");
            }
            RentalPackage p = modelMapper.map(request, RentalPackage.class);
            rentalPackageService.saveRentalPackage(p);
            return new ResponseData(Enums.ResponseStatus.SUCCESS, ResponseMessageConstants.SUCCESS);
        }catch (Exception e){
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }
}
