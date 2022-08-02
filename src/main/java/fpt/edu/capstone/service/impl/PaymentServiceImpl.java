package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.common.payment.PaymentConfig;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.payment.PaymentDTO;
import fpt.edu.capstone.dto.payment.PaymentResponse;
import fpt.edu.capstone.dto.payment.PaymentResponseDTO;
import fpt.edu.capstone.entity.DetailPackage;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.entity.Payment;
import fpt.edu.capstone.entity.Recruiter;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.PaymentRepository;
import fpt.edu.capstone.service.DetailPackageService;
import fpt.edu.capstone.service.JobService;
import fpt.edu.capstone.service.PaymentService;
import fpt.edu.capstone.service.RecruiterService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final ModelMapper modelMapper;

    private final PaymentRepository paymentRepository;

    private final RecruiterService recruiterService;

    private final DetailPackageService detailPackageService;

    private final JobService jobService;
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
        String jobId = String.valueOf(paymentDTO.getJobId());
        String description = paymentDTO.getDescription();
        String orderType = paymentDTO.getOrderType();
        String  p =  "recruiterId "+ recruiterId + " jobId "+ jobId + " detailPackageId "+detailPackageId +
                " bannerId "+bannerId + " amount " + String.valueOf(amount)+ " bankCode "+paymentDTO.getBankCode() +
                " description "+description+ " orderType "+ orderType;

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
    public List<PaymentResponse> findRecruiterPurchasedPackage(long recruiterId) {

        List<Payment> payment = paymentRepository.findByRecruiterIdAndExpiredStatusFalse(recruiterId);
        if (payment == null) {
            throw new HiveConnectException("Nhà tuyển dụng chưa mua gói dịch vụ nào");
        }
        //If exist : check expireDate package
//        for (Payment p : payment) {
//            LocalDateTime now = LocalDateTime.now();
//            int a = now.compareTo(p.getExpiredDate());
//            if(a < 0){
//                throw new HiveConnectException("Gói dịch vụ đã hết hạn sử dụng");
//            }
//        }
        List <PaymentResponse> response = new ArrayList<>();
        for (Payment dto : payment){
            PaymentResponse dto1 = new PaymentResponse();
            dto1.setPaymentId(dto.getId());
            dto1.setRecruiterId(dto.getRecruiterId());
            dto1.setDetailPackageId(dto.getDetailPackageId());
            dto1.setDetailPackageName(detailPackageService.findNameById(dto.getDetailPackageId()));
            dto1.setBannerId(dto.getBannerId());
            dto1.setJobId(dto.getJobId());
            dto1.setAmount(dto.getAmount());
            dto1.setOrderType(dto.getOrderType());
            dto1.setDescription(dto.getDescription());
            dto1.setBankCode(dto.getBankCode());

            response.add(dto1);
        }
        return response;
    }

    @Override
    public ResponseDataPagination getRevenue(LocalDateTime start, LocalDateTime end, Integer pageNo, Integer pageSize) {
        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);
        Page<Payment> payments = paymentRepository.getRevenueInMonth(start, end, pageable);
        List <Payment> totalRevenue = payments.getContent();
        long total = 0;
        for (Payment p: totalRevenue){
            total += p.getAmount();
        }
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

        String[] ss = vnpOrderInfo.split(" ");
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < ss.length; i = i + 2) {
            map.put(ss[i], ss[i + 1]);
        }
        map.forEach((key,value)->{
            if(key.equals("recruiterId")) paymentDTO.setRecruiterId(Long.parseLong(value));
            if(key.equals("detailPackageId")) paymentDTO.setDetailPackageId(Long.parseLong(value));
            if(key.equals("bannerId")) paymentDTO.setBannerId(Long.parseLong(value));
            if(key.equals("amount")) paymentDTO.setAmount(Integer.parseInt(value));
            if(key.equals("description")) paymentDTO.setDescription(value);
            if(key.equals("orderType")) paymentDTO.setOrderType(value);
            if(key.equals("bankCode")) paymentDTO.setBankCode(value);
            if(key.equals("jobId")) paymentDTO.setJobId(Long.parseLong(value));
        });
        LocalDateTime now = LocalDateTime.now();
        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        Recruiter recruiter = recruiterService.getRecruiterById(payment.getRecruiterId());
        if (recruiter == null){
            throw new HiveConnectException("Nhà tuyển dụng có id = "+ recruiter.getId()+ "không tồn tại");
        }
        //TODO: CHƯA XỬ LÍ XONG CHECK GÓI ĐÃ MUA TRƯỚC ĐÓ CÒN HẠN HAY KHÔNG
//        if(!payment.isExpiredStatus()){
//            throw new HiveConnectException("Gói dịch vụ đang trong thời hạn sử dụng. Hãy mua lại sau khi hết hạn.");
//        }
        payment.setCommand(PaymentConfig.COMMAND);
        payment.setCurrCode(PaymentConfig.CURR_CODE);
        payment.setLocal(PaymentConfig.LOCATE_DEFAULT);
        String randomTransactionCode = PaymentConfig.getRandomNumber(8);
        payment.setTransactionCode(randomTransactionCode);
        payment.setExpiredDate(now.plusDays(14));
        payment.create();

        if(vnpResponseCode.equals("00")){
            System.out.println("Thanh toán thành công");
            paymentRepository.save(payment);

            DetailPackage detailPackage = detailPackageService.findById(payment.getDetailPackageId());

            //Kích hoạt số lượng cv mà recruiter được xem full thông tin
            if(detailPackage.getRentalPackageId() == 1){
                Integer totalCv = paymentRepository.countByTotalCvView(payment.getRecruiterId());
                recruiterService.updateTotalCvView(totalCv, payment.getRecruiterId());
            }

            //Kích hoạt tính năng gói package đó cho Job tương ứng
            Job job = jobService.getJobById(payment.getJobId());
            if(job == null){
                throw new HiveConnectException("Công việc có id = "+ job.getId()+ "không tồn tại");
            }
            if(detailPackage.getRentalPackageId() == 2){
                job.setPopularJob(true);
                job.setUrgentJob(true);
                job.setNewJob(true);
//                jobService.saveJob(job);
            }
        }


        if(vnpResponseCode.equals("07")){
            throw new HiveConnectException(ResponseMessageConstants.VNP_RESPONSE_CODE_07);
        }
        if(vnpResponseCode.equals("09")){
            throw new HiveConnectException(ResponseMessageConstants.VNP_RESPONSE_CODE_09);
        }
        if(vnpResponseCode.equals("10")){
            throw new HiveConnectException(ResponseMessageConstants.VNP_RESPONSE_CODE_10);
        }
        if(vnpResponseCode.equals("11")){
            throw new HiveConnectException(ResponseMessageConstants.VNP_RESPONSE_CODE_11);
        }
        if(vnpResponseCode.equals("12")){
            throw new HiveConnectException(ResponseMessageConstants.VNP_RESPONSE_CODE_12);
        }
        if(vnpResponseCode.equals("13")){
            throw new HiveConnectException(ResponseMessageConstants.VNP_RESPONSE_CODE_13);
        }
        if(vnpResponseCode.equals("24")){
            throw new HiveConnectException(ResponseMessageConstants.VNP_RESPONSE_CODE_24);
        }
        if(vnpResponseCode.equals("51")){
            throw new HiveConnectException(ResponseMessageConstants.VNP_RESPONSE_CODE_51);
        }
        if(vnpResponseCode.equals("65")){
            throw new HiveConnectException(ResponseMessageConstants.VNP_RESPONSE_CODE_65);
        }
        if(vnpResponseCode.equals("79")){
            throw new HiveConnectException(ResponseMessageConstants.VNP_RESPONSE_CODE_79);
        }
        if(vnpResponseCode.equals("99")){
            throw new HiveConnectException(ResponseMessageConstants.VNP_RESPONSE_CODE_99);
        }
    }

    @Override
    public Payment findById(long id) {
        if(!paymentRepository.findById(id).isPresent()) {
            throw new HiveConnectException(ResponseMessageConstants.PAYMENT_DOES_NOT_EXIST);
        }
        return paymentRepository.findById(id).get();
    }

    @Override
    public void updatePayment(Payment payment) {
        paymentRepository.saveAndFlush(payment);
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public Payment findByIdAndRecruiterId(long id, long recruiterId) {
        return paymentRepository.findByIdAndRecruiterId(id, recruiterId);
    }

    @Override
    public List<Payment> getListPaymentOrderByDate() {
        return paymentRepository.getListPaymentOrderByDate();
    }
}
