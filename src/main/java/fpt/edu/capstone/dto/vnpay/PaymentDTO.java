package fpt.edu.capstone.dto.vnpay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private long recruiterId;
    private long rentalPackageId;
    private long bannerId;
    private long transactionCode;
    private int amount;
    private String orderType;
    private String description;
    private String bankCode;
}
