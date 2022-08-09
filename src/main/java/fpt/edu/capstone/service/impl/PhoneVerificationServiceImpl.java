//package fpt.edu.capstone.service.impl;
//
//import com.twilio.exception.ApiException;
//import com.twilio.rest.verify.v2.service.Verification;
//import com.twilio.rest.verify.v2.service.VerificationCheck;
//import fpt.edu.capstone.common.Twilio.TwilioProperties;
//import fpt.edu.capstone.common.Twilio.VerificationResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class PhoneVerificationServiceImpl {
//
//    private final TwilioProperties twilioProperties;
//
//    @Autowired
//    public PhoneVerificationServiceImpl(TwilioProperties twilioProperties){
//        this.twilioProperties = twilioProperties;
//    }
//
//    //method to send to otp
//    public VerificationResult startVerification(String phone) {
//        try {
//            Verification verification = Verification.creator(twilioProperties.getServiceId(), phone, "sms").create();
//            if("approved".equals(verification.getStatus())|| "pending".equals(verification.getStatus())) {
//                return new VerificationResult(verification.getSid());
//            }
//        } catch (ApiException exception) {
//            System.out.println(exception);
//            return new VerificationResult(new String[] {exception.getMessage()});
//        }
//        return null;
//    }
//
//    //mehtod to verifiy the otp
//    public VerificationResult checkverification(String phone, String code) {
//        try {
//            VerificationCheck verification = VerificationCheck.creator(twilioProperties.getServiceId(), code).setTo(phone).create();
//            if("approved".equals(verification.getStatus())) {
//                return new VerificationResult(verification.getSid());
//            }
//            return new VerificationResult(new String[]{"Invalid code."});
//        } catch (ApiException exception) {
//            return new VerificationResult(new String[]{exception.getMessage()});
//        }
//    }
//}
