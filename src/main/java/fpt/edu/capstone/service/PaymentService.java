package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.vnpay.PaymentDTO;
import fpt.edu.capstone.dto.vnpay.PaymentResponseDTO;

import java.io.UnsupportedEncodingException;

public interface PaymentService {
    PaymentResponseDTO getPaymentVNPay(PaymentDTO paymentDTO) throws UnsupportedEncodingException;

}
