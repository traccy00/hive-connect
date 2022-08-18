package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.recruiter.BannerPositionDetailResponse;
import fpt.edu.capstone.entity.BannerActive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BannerActiveService {

    List<BannerActive> getBannersByPosition(String displayPosition);

    List<BannerActive> getAllByPaymentId(long paymentId);

    List<BannerPositionDetailResponse> getAllBannerByPaymentId(long paymentId);

    BannerActive findByPaymentIdAndPosition(long paymentId, String position);

    Page<BannerActive> getAllBannerForApproval(Pageable pageable);

    BannerActive findById(long id);
}
