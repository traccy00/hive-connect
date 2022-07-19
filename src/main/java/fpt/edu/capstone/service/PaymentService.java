package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.vnpay.PaymentDTO;
import fpt.edu.capstone.dto.vnpay.PaymentResponseDTO;
import fpt.edu.capstone.entity.Payment;
import org.springframework.security.core.parameters.P;
import fpt.edu.capstone.utils.ResponseDataPagination;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
    PaymentResponseDTO getPaymentVNPay(PaymentDTO paymentDTO) throws UnsupportedEncodingException;

    List <Payment> getListPaymentFilter(long recruiterId,long rentalPackageId,
                                        long bannerId,String transactionCode,String orderType);

    List<Payment> getListPaymentOrderByDate();

    List<Payment> findRecruiterPurchasedPackage(long recruiterId);

    ResponseDataPagination getRevenue(LocalDateTime start, LocalDateTime end, Integer pageNo, Integer pageSize);

    void savePayment(Payment payment, String vnpResponseCode);
}
