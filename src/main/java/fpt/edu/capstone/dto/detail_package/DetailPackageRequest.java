package fpt.edu.capstone.dto.detail_package;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class DetailPackageRequest {
    private long rentalPackageId;
    private String type;
    private long price;
    private long discount;
    private String timeExpired;
    private String description;
    private String labelName;
    private boolean isRelatedJob;
    private boolean isSuggestJob;
    private boolean goldenHour;
    private boolean normalHour;
    private boolean maxUploadSixImage;
    private boolean isUrgent;
    private boolean isHot;
    private boolean isGoodJob;
    private boolean isDeleted;
    private int maxCvView;
}
