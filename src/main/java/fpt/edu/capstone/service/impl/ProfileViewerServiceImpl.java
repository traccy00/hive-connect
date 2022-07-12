package fpt.edu.capstone.service.impl;

import com.lowagie.text.PageSize;
import fpt.edu.capstone.entity.Candidate;
import fpt.edu.capstone.entity.ProfileViewer;
import fpt.edu.capstone.repository.ProfileViewerRepository;
import fpt.edu.capstone.service.ProfileViewerService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfileViewerServiceImpl implements ProfileViewerService {

    private final ProfileViewerRepository profileViewerRepository;

    @Override
    public Page<ProfileViewer> getProfileViewerOfCv(Pageable pageable, long cvId) {
        return profileViewerRepository.getAllByCvId(pageable, cvId);
    }

    @Override
    public ProfileViewer getByCvIdAndViewerId(long cvId, long viewerId) {
        return profileViewerRepository.getByCvIdAndViewerId(cvId, viewerId);
    }
}
