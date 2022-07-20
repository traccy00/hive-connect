package fpt.edu.capstone.dto.vnpay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private long recruiterId;
    private long detailPackageId;
    private long bannerId;
    private int amount;
    private String bankCode;
}
