package fpt.edu.capstone.scheduler;

import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.entity.Notification;
import fpt.edu.capstone.entity.Payment;
import fpt.edu.capstone.entity.Recruiter;
import fpt.edu.capstone.service.JobService;
import fpt.edu.capstone.service.NotificationService;
import fpt.edu.capstone.service.PaymentService;
import fpt.edu.capstone.service.RecruiterService;
import fpt.edu.capstone.utils.LocalDateTimeUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@EnableScheduling
@AllArgsConstructor
public class JobPaymentCheckExpired {
    private final PaymentService paymentService;

    private final JobService jobService;

    private final NotificationService notificationService;

    private final RecruiterService recruiterService;
    private static final Logger logger = LoggerFactory.getLogger(JobPaymentCheckExpired.class);

    @Scheduled(fixedDelay = 50000)
    public void checkPaymentExpire(){
        logger.info("-------------->START-CHECK_PAYMENT_EXPIRE:" + new Date());
        List <Payment> paymentList = paymentService.findAll();
        for (Payment payment: paymentList){
            if(LocalDateTimeUtils.checkExpireTime(payment.getExpiredDate())){
                payment.setExpiredStatus(true);

                //Add notification
                Job j = jobService.getJobById(payment.getJobId());
                Optional<Recruiter> r = recruiterService.findById(payment.getRecruiterId());
                String content = "Gói nâng cấp bạn đã mua cho công việc "+ j.getJobName()+ " đã hết hạn";
                Notification notification = new Notification(0, r.get().getUserId(), 4, LocalDateTime.now(), content, false, false);
                notificationService.insertNotification(notification);


            }
            paymentService.save(payment);
        }
        logger.info("-------------->END-CHECK_PAYMENT_EXPIRE:" + new Date());
    }
}
