package fpt.edu.capstone.dto.vnpay;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponseDTO {
    private String status;
    private String message;
    private String paymentUrl;
}
