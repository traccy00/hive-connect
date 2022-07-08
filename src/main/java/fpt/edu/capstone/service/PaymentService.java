package fpt.edu.capstone.service;

import com.twilio.twiml.voice.Pay;
import fpt.edu.capstone.dto.vnpay.PaymentDTO;
import fpt.edu.capstone.dto.vnpay.PaymentResponseDTO;
import fpt.edu.capstone.entity.Payment;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface PaymentService {
    PaymentResponseDTO getPaymentVNPay(PaymentDTO paymentDTO) throws UnsupportedEncodingException;

    List <Payment> getListPaymentFilter(long recruiterId,long rentalPackageId,
                                        long bannerId,String transactionCode,String orderType);

    Payment findRecruiterPurchasedPackage(long recruiterId);
}
