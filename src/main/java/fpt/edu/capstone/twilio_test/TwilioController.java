package fpt.edu.capstone.twilio_test;

import fpt.edu.capstone.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/v1/otp")
public class TwilioController {
    @Autowired
    PhoneVerificationService phonesmsservice;


    @RequestMapping("/")
    public String homepage(ModelAndView model)
    {
        return "index";
    }

    @PostMapping("/sendotp")
    public void sendotp(@RequestParam("phone") String phone)
    {
        VerificationResult result=phonesmsservice.startVerification(phone);
        if(result.isValid())
        {
            System.out.println("Send OTP Success");
            return;
        }
        System.out.println("Send OTP Failed");
    }

    @PostMapping("/verifyotp")
    public void sendotp(@RequestParam("phone") String phone, @RequestParam("otp") String otp)
    {
        VerificationResult result=phonesmsservice.checkverification(phone,otp);
        if(result.isValid())
        {
            System.out.println("Verify Success");
            return;
        }
        System.out.println("Verify failed");
    }

}
