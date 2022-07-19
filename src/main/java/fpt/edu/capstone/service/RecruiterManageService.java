package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.admin.CommonRecruiterInformationResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterProfileResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterUpdateProfileRequest;
import fpt.edu.capstone.utils.ResponseDataPagination;

public interface RecruiterManageService {

    CommonRecruiterInformationResponse getCommonInforOfRecruiter(long recruiterId);

    ResponseDataPagination getRecruitersOfCompany(Integer pageNo, Integer pageSize, long companyId);

    RecruiterProfileResponse updateRecruiterInformation(long recruiterId, RecruiterUpdateProfileRequest request) throws Exception;

    RecruiterProfileResponse getRecruiterProfile(long userId);

}
