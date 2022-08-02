package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.payment.PaymentDTO;
import fpt.edu.capstone.dto.payment.PaymentResponse;
import fpt.edu.capstone.dto.payment.PaymentResponseDTO;
import fpt.edu.capstone.entity.Payment;
import fpt.edu.capstone.utils.ResponseDataPagination;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
    PaymentResponseDTO getPaymentVNPay(PaymentDTO paymentDTO) throws UnsupportedEncodingException;

    List <Payment> getListPaymentFilter(long recruiterId,long rentalPackageId,
                                        long bannerId,String transactionCode,String orderType);

    List<Payment> getListPaymentOrderByDate();

    List<PaymentResponse> findRecruiterPurchasedPackage(long recruiterId);

    ResponseDataPagination getRevenue(LocalDateTime start, LocalDateTime end, Integer pageNo, Integer pageSize);

    void savePayment(String vnpResponseCode, String vnpOrderInfo);

    Payment findById(long id);

    void updatePayment(Payment payment);

    List<Payment> findAll();

    void save(Payment payment);

    Payment findByIdAndRecruiterId(long id, long recruiterId);

}
