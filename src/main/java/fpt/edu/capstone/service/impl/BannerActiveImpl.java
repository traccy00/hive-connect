package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.recruiter.BannerPositionDetailResponse;
import fpt.edu.capstone.entity.BannerActive;
import fpt.edu.capstone.repository.BannerActiveRepository;
import fpt.edu.capstone.service.BannerActiveService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BannerActiveImpl implements BannerActiveService {

    private final BannerActiveRepository bannerActiveRepository;

    @Override
    public List<BannerActive> getBannersByPosition(String displayPosition) {
        return bannerActiveRepository.getBannerByPosition(displayPosition);
    }

    @Override
    public List<BannerActive> getAllByPaymentId(long paymentId) {
        return bannerActiveRepository.getAllByPaymentId(paymentId);
    }

    @Override
    public List<BannerPositionDetailResponse> getAllBannerByPaymentId(long paymentId) {
        return bannerActiveRepository.getBannerActiveByPaymentId(paymentId);
    }

    @Override
    public BannerActive findByPaymentIdAndPosition(long paymentId, String position) {
        return bannerActiveRepository.findByPaymentIdAndDisplayPosition(paymentId, position);
    }

    @Override
    public Page<BannerActive> getAllBannerForApproval(Pageable pageable) {
        return bannerActiveRepository.getAllBannerForApproval(pageable);
    }


}
