package fpt.edu.capstone.entity;

import fpt.edu.capstone.utils.BaseEntity;

public class Banner extends BaseEntity {
    private long id;
    private long companyId;
    private String title;
    private String description;
    private String image;
    private boolean isDeleted;
}
