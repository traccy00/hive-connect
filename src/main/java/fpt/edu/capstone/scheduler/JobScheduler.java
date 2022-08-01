package fpt.edu.capstone.scheduler;

import fpt.edu.capstone.entity.Payment;
import fpt.edu.capstone.service.PaymentService;
import fpt.edu.capstone.utils.LocalDateTimeUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
@AllArgsConstructor
public class JobScheduler {
    private final PaymentService paymentService;

    private static final Logger logger = LoggerFactory.getLogger(JobScheduler.class);

    @Scheduled(fixedDelay = 50000)
    public void checkPaymentExpire(){
        logger.info("-------------->START-CHECK_PAYMENT_EXPIRE:" + new Date());
        List <Payment> paymentList = paymentService.findAll();
        for (Payment payment: paymentList){
            if(LocalDateTimeUtils.checkExpireTime(payment.getExpiredDate())){
                payment.setExpiredStatus(true);
            }
            paymentService.save(payment);
        }
        logger.info("-------------->END-CHECK_PAYMENT_EXPIRE:" + new Date());
    }
}
