package fpt.edu.capstone.twilio_test;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.verify.v1.service.Verification;
import com.twilio.rest.verify.v1.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneVerificationService {

    private final TwilioProperties twilioProperties;

    @Autowired
    public PhoneVerificationService(TwilioProperties twilioProperties){
        this.twilioProperties = twilioProperties;
    }

    //method to send to otp
    public VerificationResult startVerification(String phone) {
        try {
            Verification verification = Verification.creator(twilioProperties.getServiceId(), phone, "sms").create();
            if("approved".equals(verification.getStatus())|| "pending".equals(verification.getStatus())) {
                return new VerificationResult(verification.getSid());
            }
        } catch (ApiException exception) {
            System.out.println(exception);
            return new VerificationResult(new String[] {exception.getMessage()});
        }
        return null;
    }

    //mehtod to verifiy the otp
    public VerificationResult checkverification(String phone, String code) {
        try {
            VerificationCheck verification = VerificationCheck.creator(twilioProperties.getServiceId(), code).setTo(phone).create();
            if("approved".equals(verification.getStatus())) {
                return new VerificationResult(verification.getSid());
            }
            return new VerificationResult(new String[]{"Invalid code."});
        } catch (ApiException exception) {
            return new VerificationResult(new String[]{exception.getMessage()});
        }
    }
}
