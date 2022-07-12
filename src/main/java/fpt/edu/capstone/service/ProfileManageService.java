package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.CV.ViewCvResponse;
import fpt.edu.capstone.utils.ResponseDataPagination;

public interface ProfileManageService {
    ResponseDataPagination getProfileViewer(Integer pageNo, Integer pageSize, long cvId, long candidateId);

    void insertWhoViewCv(ViewCvResponse response);
}
