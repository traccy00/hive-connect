package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.BannerActive;
import fpt.edu.capstone.repository.BannerActiveRepository;
import fpt.edu.capstone.service.BannerActiveService;
import lombok.AllArgsConstructor;
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
}
