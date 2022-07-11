package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.common.payment.PaymentConfig;
import fpt.edu.capstone.dto.vnpay.PaymentDTO;
import fpt.edu.capstone.dto.vnpay.PaymentResponseDTO;
import fpt.edu.capstone.entity.Payment;
import fpt.edu.capstone.entity.Recruiter;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.PaymentRepository;
import fpt.edu.capstone.service.PaymentService;
import fpt.edu.capstone.service.RecruiterService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final ModelMapper modelMapper;

    private final PaymentRepository paymentRepository;

    private final RecruiterService recruiterService;
    @Override
    public PaymentResponseDTO getPaymentVNPay(PaymentDTO paymentDTO) throws UnsupportedEncodingException {
        Recruiter recruiter = recruiterService.getRecruiterById(paymentDTO.getRecruiterId());
        if (recruiter == null){
            throw new HiveConnectException("Recruiter id = "+ recruiter.getId()+ "not exist");
        }
        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        payment.setCommand(PaymentConfig.COMMAND);
        payment.setCurrCode(PaymentConfig.CURR_CODE);
        payment.setLocal(PaymentConfig.LOCATE_DEFAULT);
        String randomTransactionCode = PaymentConfig.getRandomNumber(8);
        payment.setTransactionCode(randomTransactionCode);
        payment.create();

        int amount = paymentDTO.getAmount() * 100;

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(date);
        String vnpCreateDate = dateString;

        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", PaymentConfig.VERSION_VNPAY);
        vnpParams.put("vnp_Command", PaymentConfig.COMMAND);
        vnpParams.put("vnp_TmnCode", PaymentConfig.TMN_CODE);
        vnpParams.put("vnp_Amount", String.valueOf(amount));
        vnpParams.put("vnp_CreateDate", vnpCreateDate);
        vnpParams.put("vnp_CurrCode", PaymentConfig.CURR_CODE);

        vnpParams.put("vnp_IpAddr", PaymentConfig.IP_DEFAULT);

        vnpParams.put("vnp_Locale", PaymentConfig.LOCATE_DEFAULT);
        vnpParams.put("vnp_OrderInfo", paymentDTO.getDescription());
        vnpParams.put("vnp_OrderType", PaymentConfig.ORDER_TYPE);
        vnpParams.put("vnp_ReturnUrl", PaymentConfig.RETURN_URL);
        vnpParams.put("vnp_TxnRef", payment.getTransactionCode());
        vnpParams.put("vnp_BankCode", paymentDTO.getBankCode());


        List fieldNames = new ArrayList(vnpParams.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnpParams.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = PaymentConfig.vnp_PayUrl + "?" + queryUrl;

        PaymentResponseDTO result = new PaymentResponseDTO();
        result.setPaymentUrl(paymentUrl);
        if (result != null){
            paymentRepository.save(payment);
        }
        return result;
    }

    @Override
    public List<Payment> getListPaymentFilter(long recruiterId, long rentalPackageId,
                                              long bannerId, String transactionCode, String orderType) {
        List <Payment> paymentList = paymentRepository.
                getListPaymentFilter(recruiterId,rentalPackageId,bannerId,transactionCode,orderType);
        return paymentList;
    }

    @Override
    public List<Payment> getListPaymentOrderByDate() {
        return paymentRepository.getListPaymentOrderByDate();
    }
}
