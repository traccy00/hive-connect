package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
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
    public ResponseData getListDetailPackage(@RequestParam(value = "name", defaultValue = "") String name){
        try {
            List<DetailPackage> list =  detailPackageService.getListDetailPackageFilter(name);
            return new ResponseData(Enums.ResponseStatus.SUCCESS, ResponseMessageConstants.SUCCESS,list);
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

    @PostMapping("/create-new-package")
    @Operation(summary = "tạo gói dịch vụ con thuộc nhóm dịch vụ. ví dụ nhóm dv : Quảng cáo tin, dv con : tin tuyển dụng")
    public ResponseData createSubPackage(@RequestBody DetailPackageRequest request){
        try {
            if(!rentalPackageService.existById(request.getRentalPackageId())){
                throw new HiveConnectException("Nhóm dịch vụ không tồn tại");
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
