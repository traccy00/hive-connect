package fpt.edu.capstone.entity;

import fpt.edu.capstone.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends BaseEntity {
    private long id;
    private long recruiterId;
    private long rentalPackageId;
    private long bannerId;
    private String transactionCode; // mã giao dịch
    private int amount;
    private String description;
    private String orderType;
    private String bankCode;
    private String command;
    private String currCode;
    private String local;
    private String expiredDate; // ngày hết hạn gói package đã thuê

}
