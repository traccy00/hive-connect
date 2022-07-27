package fpt.edu.capstone.dto.active_package;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentActiveRequest {
    private long recruiterId;
    private long jobId;
    private long detailPackageId;
    private long bannerId;
}
