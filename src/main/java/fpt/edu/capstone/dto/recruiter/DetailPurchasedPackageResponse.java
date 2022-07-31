package fpt.edu.capstone.dto.recruiter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailPurchasedPackageResponse {
    DetailPaymentPackageInforResponse inforPPRes;
    BannerDetailPurchasedResponse bannerPurchasedPPRes;
    JobDetailPurchasedResponse jobPurchasedPPRes;
}
