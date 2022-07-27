package fpt.edu.capstone.entity;

import fpt.edu.capstone.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "payment_active")
@AllArgsConstructor
@NoArgsConstructor
//Khi recruiter muốn kích hoạt gói quảng cáo cho một job nào đó thuộc recruiter, sẽ được lưu trong bảng này
public class PaymentActive extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "recruiter_id")
    private long recruiterId;

    @Column(name = "job_id")
    private long jobId;

    @Column(name = "detail_package_id")
    private long detailPackageId;

    @Column(name = "banner_id")
    private long bannerId;
}
