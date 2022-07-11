package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.ProfileViewer;
import fpt.edu.capstone.utils.ResponseDataPagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfileViewerService {
    Page<ProfileViewer> getProfileViewerOfCv(Pageable pageable, long cvId);

    ProfileViewer getByCvIdAndViewerId(long cvId, long viewerId);
}
