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
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseData;
import fpt.edu.capstone.utils.ResponseDataPagination;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final ModelMapper modelMapper;

    private final PaymentRepository paymentRepository;

    private final RecruiterService recruiterService;
    @Override
    public PaymentResponseDTO getPaymentVNPay(PaymentDTO paymentDTO) throws UnsupportedEncodingException {
        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        String randomTransactionCode = PaymentConfig.getRandomNumber(8);
        payment.setTransactionCode(randomTransactionCode);
        int amount = paymentDTO.getAmount() * 100;

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(date);
        String vnpCreateDate = dateString;

        String recruiterId = String.valueOf(paymentDTO.getRecruiterId());
        String detailPackageId = String.valueOf(paymentDTO.getDetailPackageId());
        String bannerId = String.valueOf(paymentDTO.getBannerId());

        String  p =  "recruiterId "+ recruiterId + " detailPackageId "+detailPackageId +
                " bannerId "+bannerId + " amount " + String.valueOf(amount)+ " bankCode "+paymentDTO.getBankCode();

        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", PaymentConfig.VERSION_VNPAY);
        vnpParams.put("vnp_Command", PaymentConfig.COMMAND);
        vnpParams.put("vnp_TmnCode", PaymentConfig.TMN_CODE);
        vnpParams.put("vnp_Amount", String.valueOf(amount));
        vnpParams.put("vnp_CreateDate", vnpCreateDate);
        vnpParams.put("vnp_CurrCode", PaymentConfig.CURR_CODE);
        vnpParams.put("vnp_IpAddr", PaymentConfig.IP_DEFAULT);

        vnpParams.put("vnp_Locale", PaymentConfig.LOCATE_DEFAULT);
        vnpParams.put("vnp_OrderInfo", p);
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
    public List<Payment> findRecruiterPurchasedPackage(long recruiterId) {
        List<Payment> payment = paymentRepository.findByRecruiterId(recruiterId);
        if(payment == null){
            throw new HiveConnectException("there is no payment to buy any package");
        }
        //If exist : check expireDate package
//        LocalDateTime expiredDate = payment.getExpiredDate();
//        LocalDateTime now = LocalDateTime.now();
//        if(expiredDate.isAfter(now)){
//            throw new HiveConnectException("Package has expired date");
//        }
        return payment;
    }

    @Override
    public ResponseDataPagination getRevenue(LocalDateTime start, LocalDateTime end, Integer pageNo, Integer pageSize) {
        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);
        LocalDateTime startDate = start.truncatedTo(ChronoUnit.DAYS);
        end = startDate.plusDays(1);
        Page<Payment> payments = paymentRepository.getRevenueInMonth(startDate, end, pageable);

        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(payments.toList());
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(payments.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(payments.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;
    }

    @Override
    public void savePayment(String vnpResponseCode, String vnpOrderInfo) {
        PaymentDTO paymentDTO = new PaymentDTO();

        String[] ss = vnpOrderInfo.split("\\+");
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < ss.length; i = i + 2) {
            map.put(ss[i], ss[i + 1]);
        }
        map.forEach((key,value)->{
            if(key.equals("recruiterId")) paymentDTO.setRecruiterId(Long.parseLong(value));
            if(key.equals("detailPackageId")) paymentDTO.setDetailPackageId(Long.parseLong(value));
            if(key.equals("bannerId")) paymentDTO.setBannerId(Long.parseLong(value));
            if(key.equals("amount")) paymentDTO.setAmount(Integer.parseInt(value));
//            if(key.equals("description")) paymentDTO.setDescription(value);
//            if(key.equals("orderType")) paymentDTO.setOrderType(value);
            if(key.equals("bankCode")) paymentDTO.setBankCode(value);
        });

        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        Recruiter recruiter = recruiterService.getRecruiterById(payment.getRecruiterId());
        if (recruiter == null){
            throw new HiveConnectException("Nhà tuyển dụng có id = "+ recruiter.getId()+ "không tồn tại");
        }
        payment.setCommand(PaymentConfig.COMMAND);
        payment.setCurrCode(PaymentConfig.CURR_CODE);
        payment.setLocal(PaymentConfig.LOCATE_DEFAULT);
        String randomTransactionCode = PaymentConfig.getRandomNumber(8);
        payment.setTransactionCode(randomTransactionCode);
        LocalDateTime now = LocalDateTime.now();
        payment.setExpiredDate(now.plusDays(14));
        payment.create();

        if(vnpResponseCode.equals("00")){
            System.out.println("Thanh toán thành công");
            paymentRepository.save(payment);
        }
        if(vnpResponseCode.equals("07")){
            throw new HiveConnectException("Trừ tiền thành công. Giao dịch bị nghi ngờ");
        }
        if(vnpResponseCode.equals("09")){
            throw new HiveConnectException("Giao dịch không thành công do: Thẻ/Tài khoản của khách hàng chưa đăng ký dịch vụ InternetBanking tại ngân hàng.");
        }
        if(vnpResponseCode.equals("10")){
            throw new HiveConnectException("Giao dịch không thành công do: Khách hàng xác thực thông tin thẻ/tài khoản không đúng quá 3 lần");
        }
        if(vnpResponseCode.equals("11")){
            throw new HiveConnectException("Giao dịch không thành công do: Đã hết hạn chờ thanh toán. Xin quý khách vui lòng thực hiện lại giao dịch.");
        }
        if(vnpResponseCode.equals("12")){
            throw new HiveConnectException("Giao dịch không thành công do: Thẻ/Tài khoản của khách hàng bị khóa.");
        }
        if(vnpResponseCode.equals("13")){
            throw new HiveConnectException("Giao dịch không thành công do Quý khách nhập sai mật khẩu xác thực giao dịch (OTP). Xin quý khách vui lòng thực hiện lại giao dịch.");
        }
        if(vnpResponseCode.equals("24")){
            throw new HiveConnectException("Giao dịch không thành công do: Khách hàng hủy giao dịch");
        }
        if(vnpResponseCode.equals("51")){
            throw new HiveConnectException("Giao dịch không thành công do: Tài khoản của quý khách không đủ số dư để thực hiện giao dịch.");
        }
        if(vnpResponseCode.equals("65")){
            throw new HiveConnectException("Giao dịch không thành công do: Tài khoản của Quý khách đã vượt quá hạn mức giao dịch trong ngày.");
        }
        if(vnpResponseCode.equals("79")){
            throw new HiveConnectException("Giao dịch không thành công do: KH nhập sai mật khẩu thanh toán quá số lần quy định. Xin quý khách vui lòng thực hiện lại giao dịch");
        }
        if(vnpResponseCode.equals("99")){
            throw new HiveConnectException("Các lỗi khác");
        }
    }

    @Override
    public List<Payment> getListPaymentOrderByDate() {
        return paymentRepository.getListPaymentOrderByDate();
    }
}
