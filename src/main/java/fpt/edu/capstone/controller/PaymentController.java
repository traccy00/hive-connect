package fpt.edu.capstone.controller;

import fpt.edu.capstone.common.payment.PaymentConfig;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.vnpay.PaymentDTO;
import fpt.edu.capstone.dto.vnpay.PaymentResponseDTO;
import fpt.edu.capstone.dto.vnpay.TransactionStatusDTO;
import fpt.edu.capstone.entity.Payment;
import fpt.edu.capstone.service.PaymentService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.ResponseData;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/v1/payment")
@AllArgsConstructor
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService paymentService;

    @PostMapping("/create-payment")
    public ResponseData createPayment(@RequestBody PaymentDTO paymentDTO) throws UnsupportedEncodingException {

        PaymentResponseDTO dto = paymentService.getPaymentVNPay(paymentDTO);
        return new ResponseData(Enums.ResponseStatus.SUCCESS, dto);
    }

//    @GetMapping("/payment-information")
//    public ResponseData transactionHandle(@RequestParam(value = "vnpAmount", required = false) String amount,
//                                          @RequestParam(value = "vnpBankCode", required = false) String bankCode,
//                                          @RequestParam(value = "vnpBankTranNo", required = false) String bankTranNo,
//                                          @RequestParam(value = "vnpCardType", required = false) String cardType,
//                                          @RequestParam(value = "vnpOrderInfo", required = false) String orderInfo,
//                                          @RequestParam(value = "vnpPayDate", required = false) String payDate,
//                                          @RequestParam(value = "vnpResponseCode", required = false) String responseCode,
//                                          @RequestParam(value = "vnpTmnCode", required = false) String tmnCode,
//                                          @RequestParam(value = "vnpTransactionNo", required = false) String transactionNo,
//                                          @RequestParam(value = "vnpTxnRef", required = false) String txnRef,
//                                          @RequestParam(value = "vnpSecureHashType", required = false) String secureHashType,
//                                          @RequestParam(value = "vnpSecureHash", required = false) String secureHash) {
//        TransactionStatusDTO result = new TransactionStatusDTO();
//        if(responseCode.equalsIgnoreCase("00")){
//            result.setStatus("02");
//            result.setMessage("failed");
//            result.setData(null);
//            return new ResponseData(Enums.ResponseStatus.ERROR, result);
//        }
//
//        //Lấy ra user đang đăng nhập hiện tại
//
//        if(findOrderByCustomerIdAndOrderId(param) == null ){
//            result.setStatus("11");
//            result.setMessage("order does not exist");
//            result.setData(null);
//            return new ResponseData(Enums.ResponseStatus.ERROR, result);
//        }
//
//
//        Payment payment = paymentService.findTransactionCodeByRecruiterIdAndOrderId();
//        if(order.isStatus()){
//            result.setStatus("12");
//            result.setMessage("order does not exist");
//            result.setData(null);
//            return new ResponseData(Enums.ResponseStatus.ERROR, result);
//        }
//
//    }
}
