package fpt.edu.capstone.twilio_test;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twilio")
@Data
public class TwilioProperties {
    private String accountSid = "AC20c4ce61a75e5760ea35508e73df4469";
    private String authToken = "f2a7632ce2b6d5b364c34a8757de75ec";
    private String serviceId = "VAcf787143dc0d8fe3cea2cbf289f85e7b";

    public TwilioProperties() {
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
