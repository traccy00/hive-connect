package fpt.edu.capstone.entity;

import fpt.edu.capstone.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "recruiter_id")
    private long recruiterId;

    @Column(name = "rental_package_id")
    private long rentalPackageId;

    @Column(name = "banner_id")
    private long bannerId;

    @Column(name = "transaction_code")
    private String transactionCode; // mã giao dịch

    @Column(name = "amount")
    private int amount;

    @Column(name = "description")
    private String description;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "command")
    private String command;

    @Column(name = "curr_code")
    private String currCode;

    @Column(name = "local")
    private String local;

    @Column(name = "expired_date")
    private String expiredDate; // ngày hết hạn gói package đã thuê

}
