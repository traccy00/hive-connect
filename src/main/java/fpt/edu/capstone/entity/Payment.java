package fpt.edu.capstone.entity;

import fpt.edu.capstone.utils.BaseEntity;

public class Payment extends BaseEntity {
    private long id;
    private long recruiterId;
    private long rentalPackageId;
    private long bannerId;
    private String expiredDate;

}
