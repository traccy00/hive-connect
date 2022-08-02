package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.recruiter.BannerPositionDetailResponse;
import fpt.edu.capstone.entity.BannerActive;

import java.util.List;

public interface BannerActiveService {

    List<BannerActive> getBannersByPosition(String displayPosition);

    List<BannerActive> getAllByPaymentId(long paymentId);

    List<BannerPositionDetailResponse> getAllBannerByPaymentId(long paymentId);

    BannerActive findByPaymentIdAndPosition(long paymentId, String position);
}
