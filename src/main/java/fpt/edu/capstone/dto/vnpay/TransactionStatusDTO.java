package fpt.edu.capstone.dto.vnpay;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionStatusDTO {
    private String status;
    private String message;
    private Object data;

}
