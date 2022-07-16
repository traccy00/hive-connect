package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.admin.CommonRecruiterInformationResponse;

public interface RecruiterManageService {

    CommonRecruiterInformationResponse getCommonInforOfRecruiter(long recruiterId);
}
