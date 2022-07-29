package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.CV.ViewCvResponse;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.entity.CV;
import fpt.edu.capstone.entity.Candidate;
import fpt.edu.capstone.entity.ProfileViewer;
import fpt.edu.capstone.entity.Recruiter;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.ProfileViewerRepository;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfileManageServiceImpl implements ProfileManageService {

    private final CandidateService candidateService;

    private final ProfileViewerService profileViewerService;

    private final ProfileViewerRepository profileViewerRepository;

    private final CVService cvService;

    private final RecruiterService recruiterService;

    @Override
    public ResponseDataPagination getProfileViewer(Integer pageNo, Integer pageSize, long cvId, long candidateId) {
        List<ProfileViewer> responseList = new ArrayList<>();
        Candidate candidate = candidateService.getCandidateById(candidateId);
        if (candidate == null) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);

        Page<ProfileViewer> profileViewersOfCv = profileViewerService.getProfileViewerOfCv(pageable, cvId);
        if (profileViewersOfCv.hasContent()) {
            responseList = profileViewerService.findAll(PageRequest.of(pageReq, pageSize, Sort.by(Sort.Direction.DESC, "id")));
        }
        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(responseList);
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(profileViewersOfCv.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(profileViewersOfCv.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;
    }

    @Override
    public void insertWhoViewCv(ViewCvResponse response) {
        Optional<CV> cv = cvService.findByIdAndCandidateId(response.getCvId(), response.getCandidateId());
        if (!cv.isPresent()) {
            throw new HiveConnectException("CV không tồn tại");
        }
        Recruiter recruiter = recruiterService.getRecruiterById(response.getViewerId());
        if (recruiter == null) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        ProfileViewer profileViewer = profileViewerService.getByCvIdAndViewerId(response.getCvId(), response.getViewerId());
        if (profileViewer == null) {
            ProfileViewer saveProfileViewer = new ProfileViewer();
            saveProfileViewer.setViewerId(response.getViewerId());
            saveProfileViewer.setCvId(response.getCvId());
            saveProfileViewer.setCandidateId(response.getCandidateId());
            saveProfileViewer.create();
            profileViewerRepository.save(saveProfileViewer);
        }
    }
}
