package fpt.edu.capstone.controller;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import fpt.edu.capstone.common.Twilio.TwilioProperties;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.entity.Recruiter;
import fpt.edu.capstone.entity.Users;
import fpt.edu.capstone.service.RecruiterService;
import fpt.edu.capstone.service.UserService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.LogUtils;
import fpt.edu.capstone.utils.ResponseData;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.twilio.rest.verify.v2.service.VerificationCheck;

@RestController
@RequestMapping("/api/v1/otp")
public class TwilioController {
    @Autowired
    private UserService userService;

    public TwilioController(TwilioProperties twilioProperties) {
        this.twilioProperties = twilioProperties;
    }

    @RequestMapping("/")
    public String homepage(ModelAndView model)
    {
        return "index";
    }

    @Autowired
    private final TwilioProperties twilioProperties;
    @PostMapping("/send-otp")
    public ResponseData sendOtp(@RequestParam("phone") String phone)
    {
        try {
            Twilio.init(twilioProperties.getAccountSid(), twilioProperties.getAuthToken());
            Verification verification = Verification.creator(
                            twilioProperties.getServiceId(),
                            "+84"+phone,
                            "sms")
                    .create();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.SEND_OTP_SUCCESS);

        }catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage());
        }
    }

    @PostMapping("/verify-otp")
    public ResponseData veirfyotp(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        try {
            Twilio.init(twilioProperties.getAccountSid(), twilioProperties.getAuthToken());
            VerificationCheck verificationCheck = VerificationCheck.creator(
                            twilioProperties.getServiceId(),
                            code)
                    .setTo(phone).create();

            System.out.println(verificationCheck.getStatus());
            if (verificationCheck.getStatus().equals("approval")) {
                //Update verify phone number
                Users re = userService.findByPhoneNumber(phone).get();
                if(re != null){
                    re.setVerifiedPhone(true);
                }
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Approval", verificationCheck.getStatus());
            }
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Denied", verificationCheck.getStatus());
        } catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage());
        }
    }

//    //Create Service
//     Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//    Service service = Service.creator("My Verify Service").create();
//
//        System.out.println(service.getSid());
    //https://console.twilio.com/us1/develop/verify/geopermissions?_ga=2.181488608.638496180.1656602983-36211557.1653802440

}
