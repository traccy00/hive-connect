package fpt.edu.capstone.entity;

import java.time.LocalDateTime;

public class RentalPackage {
    private long id;
    private String packageName;
    private String description;
    private String viewCV; // hiển thị số lượng cv được view
    private boolean isViewAllInformationCV;
    private boolean isAds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
