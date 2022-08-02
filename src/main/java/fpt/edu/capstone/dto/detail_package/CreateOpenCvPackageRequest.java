package fpt.edu.capstone.dto.detail_package;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOpenCvPackageRequest {
    //group package id
    private long rentalPackageId;
    private String detailName;
    private long price;
    private long discount;
    private String timeExpired;
    private String description;
    private int maxCvView;
}
