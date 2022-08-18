package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.payment.PaymentDTO;
import fpt.edu.capstone.dto.payment.PaymentResponseDTO;
import fpt.edu.capstone.dto.payment.RevenueResponse;
import fpt.edu.capstone.entity.DetailPackage;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.entity.Payment;
import fpt.edu.capstone.entity.Recruiter;
import fpt.edu.capstone.repository.PaymentRepository;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPaginationRevenue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

	@InjectMocks
	PaymentServiceImpl paymentService;

	@Mock
	ModelMapper modelMapper;

	@Mock
	RecruiterServiceImpl recruiterService;

	@Mock
	DetailPackageServiceImpl detailPackageService;

	@Mock
	PaymentRepository paymentRepository;

	@Mock
	JobServiceImpl jobService;

	@Mock
	RentalPackageServiceImpl rentalPackageService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	//PayController line 64

	@Test
	void givenVnpOrderInfoInvalid_whenCallSavePayment_thenModelMapperMapAreNotCalled() {
		String vnpOrderInfo = "recruiterId a detailPackageId b bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "00";

		Exception exception = assertThrows(Exception.class, () -> paymentService.savePayment(vnpResponseCode, vnpOrderInfo));

		verify(modelMapper, times(0)).map(ArgumentMatchers.any(),any());
	}

	@Test
	void givenModelMapperMapReturnNull_whenCallSavePayment_thenRecruiterServiceGetRecruiterByIdAreNotCalled() {
		String vnpOrderInfo = "recruiterId 1 detailPackageId 1 bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "00";

		PaymentDTO paymentDTO = handlerString(vnpOrderInfo);

		when(modelMapper.map(paymentDTO, Payment.class)).thenReturn(null);

		Exception exception = assertThrows(Exception.class, () -> paymentService.savePayment(vnpResponseCode, vnpOrderInfo));

		verify(recruiterService, times(0)).getRecruiterById(1L);
	}

	@Test
	void givenRecruiterServiceGetRecruiterByIdReturnNull_whenCallSavePayment_thenThrowException() {
		String vnpOrderInfo = "recruiterId 1 detailPackageId 1 bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "00";

		Payment payment = new Payment();
		payment.setRecruiterId(1L);

		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(payment);
		when(recruiterService.getRecruiterById(anyLong())).thenReturn(null);

		Exception exception = assertThrows(Exception.class, () -> paymentService.savePayment(vnpResponseCode, vnpOrderInfo));

		assertEquals("Nhà tuyển dụng có id = " + payment.getRecruiterId() + "không tồn tại", exception.getMessage());
	}


	@Test
	void givenPaymentGetBannerIdGreaterThan0AndPaymentGetDetailPackageIdGreaterThan0_whenCallSavePayment_thenThrowException() {
		String vnpOrderInfo = "recruiterId 1 detailPackageId 1 bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "00";
		Payment payment = new Payment();
		payment.setRecruiterId(1L);
		payment.setBannerId(1);
		payment.setDetailPackageId(1);
		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(payment);

		Recruiter recruiter = new Recruiter();
		when(recruiterService.getRecruiterById(anyLong())).thenReturn(recruiter);

		Exception exception = assertThrows(Exception.class, () -> paymentService.savePayment(vnpResponseCode, vnpOrderInfo));

		assertEquals(ResponseMessageConstants.PLEASE_TRY_TO_CONTACT_ADMIN, exception.getMessage());
	}

	@Test
	void givenPaymentGetBannerIdEquals0AndPaymentGetDetailPackageIdEquals0_whenCallSavePayment_thenThrowException() {
		String vnpOrderInfo = "recruiterId 1 detailPackageId 1 bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "00";
		Payment payment = new Payment();
		payment.setRecruiterId(1L);
		payment.setBannerId(0);
		payment.setDetailPackageId(0);
		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(payment);

		Recruiter recruiter = new Recruiter();
		when(recruiterService.getRecruiterById(anyLong())).thenReturn(recruiter);

		Exception exception = assertThrows(Exception.class, () -> paymentService.savePayment(vnpResponseCode, vnpOrderInfo));

		assertEquals(ResponseMessageConstants.PLEASE_TRY_TO_CONTACT_ADMIN, exception.getMessage());
	}


	//when (payment.getBannerId() == 0 && payment.getDetailPackageId() == 0) ||
	// 	(payment.getBannerId() > 0 && payment.getDetailPackageId() > 0) == false
	@Test
	void givenVnpResponseCodeEquals00AndGetRentalPackageIdEquals1_thenCallRecruiterServiceUpdateTotalCvView() {
		String vnpOrderInfo = "recruiterId 1 detailPackageId 1 bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "00";
		Payment payment = new Payment();
		payment.setRecruiterId(1L);
		payment.setBannerId(0);
		payment.setDetailPackageId(1);
		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(payment);

		Recruiter recruiter = new Recruiter();
		when(recruiterService.getRecruiterById(anyLong())).thenReturn(recruiter);

		when(paymentRepository.save(any())).thenReturn(payment);

		DetailPackage detailPackage = new DetailPackage();
		detailPackage.setRentalPackageId(1L);
		when(detailPackageService.findById(1)).thenReturn(detailPackage);

		when(paymentRepository.countByTotalCvView(1L)).thenReturn(1);

		paymentService.savePayment(vnpResponseCode, vnpOrderInfo);

		verify(recruiterService, times(1)).updateTotalCvView(1, 1L);
	}

	//when (payment.getBannerId() == 0 && payment.getDetailPackageId() == 0) ||
	// 	(payment.getBannerId() > 0 && payment.getDetailPackageId() > 0) == false
	@Test
	void givenVnpResponseCodeEquals00AndGetRentalPackageIdEquals1AndPaymentRepositoryCountByTotalCvViewReturnNullReturnNull_whenCallSavePayment_thenJobServiceSaveJobAreNotCalled() {
		String vnpOrderInfo = "recruiterId 1 detailPackageId 1 bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "00";
		Payment payment = new Payment();
		payment.setRecruiterId(1L);
		payment.setBannerId(0);
		payment.setDetailPackageId(1);
		payment.setJobId(1L);
		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(payment);

		Recruiter recruiter = new Recruiter();
		when(recruiterService.getRecruiterById(anyLong())).thenReturn(recruiter);

		when(paymentRepository.save(any())).thenReturn(payment);

		DetailPackage detailPackage = new DetailPackage();
		detailPackage.setRentalPackageId(2L);
		when(jobService.getJobById(1L)).thenReturn(null);

		Exception exception = assertThrows(Exception.class, () -> paymentService.savePayment(vnpResponseCode, vnpOrderInfo));

		verify(recruiterService, times(0)).updateTotalCvView(1, 1L);
	}

	//when (payment.getBannerId() == 0 && payment.getDetailPackageId() == 0) ||
	// 	(payment.getBannerId() > 0 && payment.getDetailPackageId() > 0) == false
	@Test
	void givenVnpResponseCodeEquals00AndGetRentalPackageIdEquals2_whenCallSavePayment_thenCallJobServiceSaveJob() {
		String vnpOrderInfo = "recruiterId 1 detailPackageId 1 bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "00";
		Payment payment = new Payment();
		payment.setRecruiterId(1L);
		payment.setBannerId(0);
		payment.setDetailPackageId(1);
		payment.setJobId(1L);
		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(payment);

		Recruiter recruiter = new Recruiter();
		when(recruiterService.getRecruiterById(anyLong())).thenReturn(recruiter);

		when(paymentRepository.save(any())).thenReturn(payment);

		DetailPackage detailPackage = new DetailPackage();
		detailPackage.setRentalPackageId(2L);
		when(detailPackageService.findById(1)).thenReturn(detailPackage);

		Job job = new Job();
		when(jobService.getJobById(1L)).thenReturn(job);

		paymentService.savePayment(vnpResponseCode, vnpOrderInfo);

		verify(jobService, times(1)).saveJob(any());
	}

	//when (payment.getBannerId() == 0 && payment.getDetailPackageId() == 0) ||
	// 	(payment.getBannerId() > 0 && payment.getDetailPackageId() > 0) == false
	@Test
	void givenVnpResponseCodeEquals00AndGetRentalPackageIdEquals2AndJobServiceGetJobByIdReturnNull_whenCallSavePayment_thenJobServiceSaveJobAreNotCalled() {
		String vnpOrderInfo = "recruiterId 1 detailPackageId 1 bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "00";
		Payment payment = new Payment();
		payment.setRecruiterId(1L);
		payment.setBannerId(0);
		payment.setDetailPackageId(1);
		payment.setJobId(1L);
		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(payment);

		Recruiter recruiter = new Recruiter();
		when(recruiterService.getRecruiterById(anyLong())).thenReturn(recruiter);

		when(paymentRepository.save(any())).thenReturn(payment);

		DetailPackage detailPackage = new DetailPackage();
		detailPackage.setRentalPackageId(2L);
		when(detailPackageService.findById(1)).thenReturn(detailPackage);

		Exception exception = assertThrows(Exception.class, () -> paymentService.savePayment(vnpResponseCode, vnpOrderInfo));

		verify(jobService, times(0)).saveJob(any());
	}

	@Test
	void givenVnpResponseCodeEquals07_whenCallSavePayment_thenThrowException() {
		String vnpOrderInfo = "recruiterId 1 detailPackageId 1 bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "07";
		Payment payment = new Payment();
		payment.setRecruiterId(1L);
		payment.setBannerId(0);
		payment.setDetailPackageId(0);
		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(payment);

		Recruiter recruiter = new Recruiter();
		when(recruiterService.getRecruiterById(anyLong())).thenReturn(recruiter);

		Exception exception = assertThrows(Exception.class, () -> paymentService.savePayment(vnpResponseCode, vnpOrderInfo));

		assertEquals(ResponseMessageConstants.VNP_RESPONSE_CODE_07, exception.getMessage());
	}

	@Test
	void givenVnpResponseCodeEquals09_whenCallSavePayment_thenThrowException() {
		String vnpOrderInfo = "recruiterId 1 detailPackageId 1 bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "09";
		Payment payment = new Payment();
		payment.setRecruiterId(1L);
		payment.setBannerId(0);
		payment.setDetailPackageId(0);
		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(payment);

		Recruiter recruiter = new Recruiter();
		when(recruiterService.getRecruiterById(anyLong())).thenReturn(recruiter);

		Exception exception = assertThrows(Exception.class, () -> paymentService.savePayment(vnpResponseCode, vnpOrderInfo));

		assertEquals(ResponseMessageConstants.VNP_RESPONSE_CODE_09, exception.getMessage());
	}

	@Test
	void givenVnpResponseCodeEquals10_whenCallSavePayment_thenThrowException() {
		String vnpOrderInfo = "recruiterId 1 detailPackageId 1 bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "10";
		Payment payment = new Payment();
		payment.setRecruiterId(1L);
		payment.setBannerId(0);
		payment.setDetailPackageId(0);
		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(payment);

		Recruiter recruiter = new Recruiter();
		when(recruiterService.getRecruiterById(anyLong())).thenReturn(recruiter);

		Exception exception = assertThrows(Exception.class, () -> paymentService.savePayment(vnpResponseCode, vnpOrderInfo));

		assertEquals(ResponseMessageConstants.VNP_RESPONSE_CODE_10, exception.getMessage());
	}

	@Test
	void givenVnpResponseCodeEquals11_whenCallSavePayment_thenThrowException() {
		String vnpOrderInfo = "recruiterId 1 detailPackageId 1 bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "11";
		Payment payment = new Payment();
		payment.setRecruiterId(1L);
		payment.setBannerId(0);
		payment.setDetailPackageId(0);
		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(payment);

		Recruiter recruiter = new Recruiter();
		when(recruiterService.getRecruiterById(anyLong())).thenReturn(recruiter);

		Exception exception = assertThrows(Exception.class, () -> paymentService.savePayment(vnpResponseCode, vnpOrderInfo));

		assertEquals(ResponseMessageConstants.VNP_RESPONSE_CODE_11, exception.getMessage());
	}

	@Test
	void givenVnpResponseCodeEquals12_whenCallSavePayment_thenThrowException() {
		String vnpOrderInfo = "recruiterId 1 detailPackageId 1 bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "12";
		Payment payment = new Payment();
		payment.setRecruiterId(1L);
		payment.setBannerId(0);
		payment.setDetailPackageId(0);
		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(payment);

		Recruiter recruiter = new Recruiter();
		when(recruiterService.getRecruiterById(anyLong())).thenReturn(recruiter);

		Exception exception = assertThrows(Exception.class, () -> paymentService.savePayment(vnpResponseCode, vnpOrderInfo));

		assertEquals(ResponseMessageConstants.VNP_RESPONSE_CODE_12, exception.getMessage());
	}

	@Test
	void givenVnpResponseCodeEquals13_whenCallSavePayment_thenThrowException() {
		String vnpOrderInfo = "recruiterId 1 detailPackageId 1 bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "13";
		Payment payment = new Payment();
		payment.setRecruiterId(1L);
		payment.setBannerId(0);
		payment.setDetailPackageId(0);
		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(payment);

		Recruiter recruiter = new Recruiter();
		when(recruiterService.getRecruiterById(anyLong())).thenReturn(recruiter);

		Exception exception = assertThrows(Exception.class, () -> paymentService.savePayment(vnpResponseCode, vnpOrderInfo));

		assertEquals(ResponseMessageConstants.VNP_RESPONSE_CODE_13, exception.getMessage());
	}

	@Test
	void givenVnpResponseCodeEquals24_whenCallSavePayment_thenThrowException() {
		String vnpOrderInfo = "recruiterId 1 detailPackageId 1 bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "24";
		Payment payment = new Payment();
		payment.setRecruiterId(1L);
		payment.setBannerId(0);
		payment.setDetailPackageId(0);
		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(payment);

		Recruiter recruiter = new Recruiter();
		when(recruiterService.getRecruiterById(anyLong())).thenReturn(recruiter);

		Exception exception = assertThrows(Exception.class, () -> paymentService.savePayment(vnpResponseCode, vnpOrderInfo));

		assertEquals(ResponseMessageConstants.VNP_RESPONSE_CODE_24, exception.getMessage());
	}

	@Test
	void givenVnpResponseCodeEquals51_whenCallSavePayment_thenThrowException() {
		String vnpOrderInfo = "recruiterId 1 detailPackageId 1 bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "51";
		Payment payment = new Payment();
		payment.setRecruiterId(1L);
		payment.setBannerId(0);
		payment.setDetailPackageId(0);
		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(payment);

		Recruiter recruiter = new Recruiter();
		when(recruiterService.getRecruiterById(anyLong())).thenReturn(recruiter);

		Exception exception = assertThrows(Exception.class, () -> paymentService.savePayment(vnpResponseCode, vnpOrderInfo));

		assertEquals(ResponseMessageConstants.VNP_RESPONSE_CODE_51, exception.getMessage());
	}

	@Test
	void givenVnpResponseCodeEquals65_whenCallSavePayment_thenThrowException() {
		String vnpOrderInfo = "recruiterId 1 detailPackageId 1 bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "65";
		Payment payment = new Payment();
		payment.setRecruiterId(1L);
		payment.setBannerId(0);
		payment.setDetailPackageId(0);
		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(payment);

		Recruiter recruiter = new Recruiter();
		when(recruiterService.getRecruiterById(anyLong())).thenReturn(recruiter);

		Exception exception = assertThrows(Exception.class, () -> paymentService.savePayment(vnpResponseCode, vnpOrderInfo));

		assertEquals(ResponseMessageConstants.VNP_RESPONSE_CODE_65, exception.getMessage());
	}

	@Test
	void givenVnpResponseCodeEquals79_whenCallSavePayment_thenThrowException() {
		String vnpOrderInfo = "recruiterId 1 detailPackageId 1 bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "79";
		Payment payment = new Payment();
		payment.setRecruiterId(1L);
		payment.setBannerId(0);
		payment.setDetailPackageId(0);
		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(payment);

		Recruiter recruiter = new Recruiter();
		when(recruiterService.getRecruiterById(anyLong())).thenReturn(recruiter);

		Exception exception = assertThrows(Exception.class, () -> paymentService.savePayment(vnpResponseCode, vnpOrderInfo));

		assertEquals(ResponseMessageConstants.VNP_RESPONSE_CODE_79,exception.getMessage());
	}

	@Test
	void givenVnpResponseCodeEquals99_whenCallSavePayment_thenThrowException() {
		String vnpOrderInfo = "recruiterId 1 detailPackageId 1 bannerId 1 amount 1 description abc orderType abc bankCode abc jobId 1";
		String vnpResponseCode = "99";
		Payment payment = new Payment();
		payment.setRecruiterId(1L);
		payment.setBannerId(0);
		payment.setDetailPackageId(0);
		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(payment);

		Recruiter recruiter = new Recruiter();
		when(recruiterService.getRecruiterById(anyLong())).thenReturn(recruiter);

		Exception exception = assertThrows(Exception.class, () -> paymentService.savePayment(vnpResponseCode, vnpOrderInfo));

		assertEquals(ResponseMessageConstants.VNP_RESPONSE_CODE_99, exception.getMessage());
	}

	//PayController line 64

	PaymentDTO handlerString(String vnpOrderInfo) {
		PaymentDTO paymentDTO = new PaymentDTO();
		String[] ss = vnpOrderInfo.split(" ");
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < ss.length; i = i + 2) {
			map.put(ss[i], ss[i + 1]);
		}
		map.forEach((key, value) -> {
			if (key.equals("recruiterId")) paymentDTO.setRecruiterId(Long.parseLong(value));
			if (key.equals("detailPackageId")) paymentDTO.setDetailPackageId(Long.parseLong(value));
			if (key.equals("bannerId")) paymentDTO.setBannerId(Long.parseLong(value));
			if (key.equals("amount")) paymentDTO.setAmount(Integer.parseInt(value));
			if (key.equals("description")) paymentDTO.setDescription(value);
			if (key.equals("orderType")) paymentDTO.setOrderType(value);
			if (key.equals("bankCode")) paymentDTO.setBankCode(value);
			if (key.equals("jobId")) paymentDTO.setJobId(Long.parseLong(value));
		});
		return paymentDTO;
	}

	//PayController line 48

	@Test
	void getPaymentVNPayTest() throws UnsupportedEncodingException {
		PaymentDTO paymentDTO = new PaymentDTO();
		paymentDTO.setAmount(1);
		paymentDTO.setRecruiterId(1);
		paymentDTO.setDetailPackageId(1);
		paymentDTO.setJobId(1);
		paymentDTO.setDescription("a");
		paymentDTO.setOrderType("a");

		Payment payment = new Payment();
		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(payment);

		PaymentResponseDTO paymentResponseDTO = paymentService.getPaymentVNPay(paymentDTO);

		assertEquals(false, paymentResponseDTO.getPaymentUrl().isEmpty());
	}

	@Test
	void whenModelMapperMapReturnNull_whenGetPaymentVNPayTest_thenThrowNull() throws UnsupportedEncodingException {
		PaymentDTO paymentDTO = new PaymentDTO();
		paymentDTO.setAmount(1);
		paymentDTO.setRecruiterId(1);
		paymentDTO.setDetailPackageId(1);
		paymentDTO.setJobId(1);
		paymentDTO.setDescription("a");
		paymentDTO.setOrderType("a");

		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(null);

		Exception exception = assertThrows(Exception.class, () -> paymentService.getPaymentVNPay(paymentDTO));

		assertEquals(null, exception.getMessage());
	}

	//PayController line 48

	//PayController line 141

	@Test
	void givenPageNoIsNull_whenCallGetRevenue_thenPaymentRepositoryGetRevenueInMonthAreNotCalled() {
		LocalDateTime start = LocalDateTime.of(2022, Month.AUGUST, 01, 12, 00, 00	);
		LocalDateTime end = LocalDateTime.of(2022, Month.AUGUST, 31, 12, 00, 00	);
		Integer pageNo = null;
		Integer pageSize = 1;

		Exception exception = assertThrows(Exception.class,()->paymentService.getRevenue(start,end,pageNo,pageSize));

		verify(paymentRepository,times(0)).getRevenueInMonth(any(),any(),any());
	}

	@Test
	void givenPaymentRepositoryGetRevenueReturnNull_whenCallGetRevenue_thenPaymentRepositoryGetRevenueInMonthAreNotCalled() {
		LocalDateTime start = LocalDateTime.of(2022, Month.AUGUST, 01, 12, 00, 00	);
		LocalDateTime end = LocalDateTime.of(2022, Month.AUGUST, 31, 12, 00, 00	);
		Integer pageNo = null;
		Integer pageSize = 1;

		Exception exception = assertThrows(Exception.class,()->paymentService.getRevenue(start,end,pageNo,pageSize));

		verify(modelMapper,times(0)).map(ArgumentMatchers.any(),any());
	}

	@Test
	void givenModelMapperMapReturnNull_whenCallGetRevenue_thenRecruiterServiceGetRecruiterByIdAreNotCalled() {
		LocalDateTime start = LocalDateTime.of(2022, Month.AUGUST, 01, 12, 00, 00	);
		LocalDateTime end = LocalDateTime.of(2022, Month.AUGUST, 31, 12, 00, 00	);
		Integer pageNo = 1;
		Integer pageSize = 1;
		int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
		Pageable pageable = PageRequest.of(pageReq, pageSize);
		Payment payment = new Payment();
		Page<Payment> paymentPage = new PageImpl<>(Arrays.asList(payment));
		when(paymentRepository.getRevenueInMonth(start,end,pageable)).thenReturn(paymentPage);
		when(modelMapper.getTypeMap(ArgumentMatchers.any(),any())).thenReturn(null);

		Exception exception = assertThrows(Exception.class,()->paymentService.getRevenue(start,end,pageNo,pageSize));

		verify(recruiterService,times(0)).getRecruiterById(1);
	}

	@Test
	void givenModelMapperMapReturnNull_whenCallGetRevenue_thenRentalPackageServiceGetRentalPackageNameAreNotCalled() {
		LocalDateTime start = LocalDateTime.of(2022, Month.AUGUST, 01, 12, 00, 00	);
		LocalDateTime end = LocalDateTime.of(2022, Month.AUGUST, 31, 12, 00, 00	);
		Integer pageNo = 1;
		Integer pageSize = 1;
		int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
		Pageable pageable = PageRequest.of(pageReq, pageSize);
		Payment payment = new Payment();
		Page<Payment> paymentPage = new PageImpl<>(Arrays.asList(payment));
		when(paymentRepository.getRevenueInMonth(start,end,pageable)).thenReturn(paymentPage);
		when(modelMapper.getTypeMap(ArgumentMatchers.any(),any())).thenReturn(null);

		Exception exception = assertThrows(Exception.class,()->paymentService.getRevenue(start,end,pageNo,pageSize));

		verify(rentalPackageService,times(0)).getRentalPackageName(1);
	}

	@Test
	void givenItemResponseGetBannerIdEquals0AndRentalPackageServiceGetRentalPackageReturnNull_whenGetRevenue_thenRecruiterServiceGetRecruiterByIdAreNotCalled(){
		LocalDateTime start = LocalDateTime.of(2022, Month.AUGUST, 01, 12, 00, 00	);
		LocalDateTime end = LocalDateTime.of(2022, Month.AUGUST, 31, 12, 00, 00	);
		Integer pageNo = 1;
		Integer pageSize = 1;
		int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
		Pageable pageable = PageRequest.of(pageReq, pageSize);
		Payment payment = new Payment();
		Page<Payment> paymentPage = new PageImpl<>(Arrays.asList(payment));
		when(paymentRepository.getRevenueInMonth(start,end,pageable)).thenReturn(paymentPage);
		RevenueResponse revenueResponse = new RevenueResponse();
		revenueResponse.setBannerId(0);
		when(modelMapper.map(payment, RevenueResponse.class)).thenReturn(revenueResponse);
		when(rentalPackageService.getRentalPackageName(1)).thenReturn(null);
		Exception exception = assertThrows(Exception.class,()->paymentService.getRevenue(start,end,pageNo,pageSize));

		verify(recruiterService,times(0)).getRecruiterById(1);
	}

	@Test
	void givenAllParamIsCorrect_whenGetRevenue_thenResponseDataPaginationRevenueGetFirstItem(){
		LocalDateTime start = LocalDateTime.of(2022, Month.AUGUST, 01, 12, 00, 00	);
		LocalDateTime end = LocalDateTime.of(2022, Month.AUGUST, 31, 12, 00, 00	);
		Integer pageNo = 1;
		Integer pageSize = 1;
		int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
		Pageable pageable = PageRequest.of(pageReq, pageSize);
		Payment payment = new Payment();
		Page<Payment> paymentPage = new PageImpl<>(Arrays.asList(payment));
		when(paymentRepository.getRevenueInMonth(start,end,pageable)).thenReturn(paymentPage);
		RevenueResponse revenueResponse = new RevenueResponse();
		revenueResponse.setBannerId(0);
		revenueResponse.setRecruiterId(1);
		when(modelMapper.map(payment, RevenueResponse.class)).thenReturn(revenueResponse);
		when(rentalPackageService.getRentalPackageName(1)).thenReturn("a");
		Recruiter recruiter = new Recruiter();
		recruiter.setFullName("a");
		when(recruiterService.getRecruiterById(1)).thenReturn(recruiter);

		ResponseDataPaginationRevenue responseDataPagination =  paymentService.getRevenue(start,end,pageNo,pageSize);

		List<RevenueResponse> revenueResponseList = (List<RevenueResponse>) responseDataPagination.getData();
		Pagination pagination = (Pagination) responseDataPagination.getPagination();

		assertEquals(0,revenueResponseList.get(0).getBannerId());

	}

	@Test
	void givenAllParamIsCorrect_whenGetRevenue_thenResponseDataPaginationRevenueSize(){
		LocalDateTime start = LocalDateTime.of(2022, Month.AUGUST, 01, 12, 00, 00	);
		LocalDateTime end = LocalDateTime.of(2022, Month.AUGUST, 31, 12, 00, 00	);
		Integer pageNo = 1;
		Integer pageSize = 1;
		int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
		Pageable pageable = PageRequest.of(pageReq, pageSize);
		Payment payment = new Payment();
		Page<Payment> paymentPage = new PageImpl<>(Arrays.asList(payment));
		when(paymentRepository.getRevenueInMonth(start,end,pageable)).thenReturn(paymentPage);
		RevenueResponse revenueResponse = new RevenueResponse();
		revenueResponse.setBannerId(0);
		revenueResponse.setRecruiterId(1);
		when(modelMapper.map(payment, RevenueResponse.class)).thenReturn(revenueResponse);
		when(rentalPackageService.getRentalPackageName(1)).thenReturn("a");
		Recruiter recruiter = new Recruiter();
		recruiter.setFullName("a");
		when(recruiterService.getRecruiterById(1)).thenReturn(recruiter);

		ResponseDataPaginationRevenue responseDataPagination =  paymentService.getRevenue(start,end,pageNo,pageSize);

		List<RevenueResponse> revenueResponseList = (List<RevenueResponse>) responseDataPagination.getData();
			assertEquals(1,revenueResponseList.size());

	}

	@Test
	void givenAllParamIsCorrect_whenGetRevenue_thenResponseDataPaginationRevenueStatus(){
		LocalDateTime start = LocalDateTime.of(2022, Month.AUGUST, 01, 12, 00, 00	);
		LocalDateTime end = LocalDateTime.of(2022, Month.AUGUST, 31, 12, 00, 00	);
		Integer pageNo = 1;
		Integer pageSize = 1;
		int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
		Pageable pageable = PageRequest.of(pageReq, pageSize);
		Payment payment = new Payment();
		Page<Payment> paymentPage = new PageImpl<>(Arrays.asList(payment));
		when(paymentRepository.getRevenueInMonth(start,end,pageable)).thenReturn(paymentPage);
		RevenueResponse revenueResponse = new RevenueResponse();
		revenueResponse.setBannerId(0);
		revenueResponse.setRecruiterId(1);
		when(modelMapper.map(payment, RevenueResponse.class)).thenReturn(revenueResponse);
		when(rentalPackageService.getRentalPackageName(1)).thenReturn("a");
		Recruiter recruiter = new Recruiter();
		recruiter.setFullName("a");
		when(recruiterService.getRecruiterById(1)).thenReturn(recruiter);

		ResponseDataPaginationRevenue responseDataPagination =  paymentService.getRevenue(start,end,pageNo,pageSize);

		assertEquals(Enums.ResponseStatus.SUCCESS.getStatus(),responseDataPagination.getStatus());

	}

	@Test
	void givenAllParamIsCorrect_whenGetRevenue_thenResponseDataPaginationRevenueToTalPage(){
		LocalDateTime start = LocalDateTime.of(2022, Month.AUGUST, 01, 12, 00, 00	);
		LocalDateTime end = LocalDateTime.of(2022, Month.AUGUST, 31, 12, 00, 00	);
		Integer pageNo = 1;
		Integer pageSize = 1;
		int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
		Pageable pageable = PageRequest.of(pageReq, pageSize);
		Payment payment = new Payment();
		Page<Payment> paymentPage = new PageImpl<>(Arrays.asList(payment));
		when(paymentRepository.getRevenueInMonth(start,end,pageable)).thenReturn(paymentPage);
		RevenueResponse revenueResponse = new RevenueResponse();
		revenueResponse.setBannerId(0);
		revenueResponse.setRecruiterId(1);
		when(modelMapper.map(payment, RevenueResponse.class)).thenReturn(revenueResponse);
		when(rentalPackageService.getRentalPackageName(1)).thenReturn("a");
		Recruiter recruiter = new Recruiter();
		recruiter.setFullName("a");
		when(recruiterService.getRecruiterById(1)).thenReturn(recruiter);

		ResponseDataPaginationRevenue responseDataPagination =  paymentService.getRevenue(start,end,pageNo,pageSize);
		Pagination pagination = (Pagination) responseDataPagination.getPagination();

		assertEquals(1,pagination.getTotalPage());
	}

	@Test
	void givenAllParamIsCorrect_whenGetRevenue_thenResponseDataPaginationTotalRevenue(){
		LocalDateTime start = LocalDateTime.of(2022, Month.AUGUST, 01, 12, 00, 00	);
		LocalDateTime end = LocalDateTime.of(2022, Month.AUGUST, 31, 12, 00, 00	);
		Integer pageNo = 1;
		Integer pageSize = 1;
		int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
		Pageable pageable = PageRequest.of(pageReq, pageSize);
		Payment payment = new Payment();
		Page<Payment> paymentPage = new PageImpl<>(Arrays.asList(payment));
		when(paymentRepository.getRevenueInMonth(start,end,pageable)).thenReturn(paymentPage);
		RevenueResponse revenueResponse = new RevenueResponse();
		revenueResponse.setBannerId(0);
		revenueResponse.setRecruiterId(1);
		when(modelMapper.map(payment, RevenueResponse.class)).thenReturn(revenueResponse);
		when(rentalPackageService.getRentalPackageName(1)).thenReturn("a");
		Recruiter recruiter = new Recruiter();
		recruiter.setFullName("a");
		when(recruiterService.getRecruiterById(1)).thenReturn(recruiter);

		ResponseDataPaginationRevenue responseDataPagination =  paymentService.getRevenue(start,end,pageNo,pageSize);
		Pagination pagination = (Pagination) responseDataPagination.getPagination();

		assertEquals(0,responseDataPagination.getTotalRevenue());
	}

	//PayController line 141

}