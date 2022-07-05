package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.vnpay.PaymentDTO;
import fpt.edu.capstone.dto.vnpay.PaymentResponseDTO;
import fpt.edu.capstone.entity.Payment;
import fpt.edu.capstone.service.PaymentService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.LogUtils;
import fpt.edu.capstone.utils.ResponseData;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
@AllArgsConstructor
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService paymentService;

    @PostMapping("/create-payment")
    public ResponseData createPayment(@RequestBody PaymentDTO paymentDTO)  {
        try {
            PaymentResponseDTO dto = paymentService.getPaymentVNPay(paymentDTO);
            return new ResponseData(Enums.ResponseStatus.SUCCESS, ResponseMessageConstants.PAYMENT_SUCCESS, dto);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @GetMapping("/payment-information")
    public ResponseData transactionHandle(@RequestParam(defaultValue = "0", value = "recruiterId", required = false) long recruiterId,
                                          @RequestParam(defaultValue = "0", value = "rentalPackageId", required = false) long rentalPackageId,
                                          @RequestParam(defaultValue = "0", value = "bannerId", required = false) long bannerId,
                                          @RequestParam(value = "transactionCode", required = false) String transactionCode,
                                          @RequestParam(value = "orderType", required = false) String orderType) {
        try {
            List<Payment> paymentList = paymentService.
                    getListPaymentFilter(recruiterId, rentalPackageId, bannerId, transactionCode, orderType);
            return new ResponseData(Enums.ResponseStatus.SUCCESS, ResponseMessageConstants.PAYMENT_SUCCESS, paymentList);
        } catch (Exception e){
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    /*
    xử lý get tổng số tiền mà recruiter đã nạp
    xử lý khi mua gói package sẽ truyền vào gì, ngày hết hạn như nào
     */
}
