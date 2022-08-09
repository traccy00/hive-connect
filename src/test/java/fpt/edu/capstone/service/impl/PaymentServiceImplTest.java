package fpt.edu.capstone.service.impl;

import com.twilio.twiml.voice.Pay;
import fpt.edu.capstone.dto.payment.PaymentResponseDTO;
import fpt.edu.capstone.entity.Payment;
import fpt.edu.capstone.entity.RentalPackage;
import fpt.edu.capstone.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
public class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Test
    void getPaymentVNPayTest(){

    }

    @Test
    void getListPaymentFilterTest(){
        List<Payment> list = new ArrayList<>();
        for (Payment payment: list) {
            payment.setId(1L);
            payment.setRecruiterId(1L);
            payment.setDetailPackageId(1L);
            payment.setBannerId(1L);
            payment.setTransactionCode("468912");
            payment.setOrderType("payment");

        }
        Mockito.when(paymentRepository.getListPaymentFilter(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyLong(),ArgumentMatchers.anyString(),ArgumentMatchers.anyString())).thenReturn(list);
        List <Payment>  paymentList = paymentService.
                getListPaymentFilter(1L, 1L, 1L, "468912", "payment");
        assertEquals(0, paymentList.size());
    }


    @Test
    void findRecruiterPurchasedPackageTest(){

    }

    @Test
    void getRevenueTest(){

    }

    @Test
    void findByIdTest(){
        Payment payment = new Payment();
        payment.setId(1L);
        Mockito.when(paymentRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(payment));
        Payment p = paymentService.findById(1L);
        assertEquals(1L,p.getId());
    }

    @Test
    void updatePaymentTest(){
        Payment payment = new Payment();
        payment.setId(1L);
        paymentService.updatePayment(payment);
    }

    @Test
    void findAllTest(){
        List<Payment> list = new ArrayList<>();
        Payment payment = new Payment();
        payment.setId(111L);
        list.add(payment);
        Mockito.when(paymentRepository.findAll()).thenReturn(list);
        List <Payment> paymentList = paymentService.findAll();
        assertEquals(1, paymentList.size());
    }

    @Test
    void savePaymentTest(){
        Payment payment = new Payment();
        payment.setId(1L);
        paymentService.save(payment);
    }

    @Test
    void findByIdAndRecruiterIdTest(){
        Payment payment = new Payment();
        payment.setId(1L);
        Mockito.when(paymentRepository.findByIdAndRecruiterId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(payment);
        Payment p = paymentService.findByIdAndRecruiterId(1L,1L);
        assertEquals(1L, p.getId());
    }

    @Test
    void getListJobIdInPaymentTest(){
        List<Long> list = new ArrayList<>();
        list.add(1L);
        Mockito.when(paymentRepository.getListJobIdInPayment()).thenReturn(list);
        List <Long> l = paymentService.getListJobIdInPayment();
        assertEquals(1, l.size());
    }
}
