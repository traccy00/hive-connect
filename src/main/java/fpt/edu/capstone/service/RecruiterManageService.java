package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.admin.CommonRecruiterInformationResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterProfileResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterUpdateProfileRequest;
import fpt.edu.capstone.utils.ResponseDataPagination;
import org.springframework.web.multipart.MultipartFile;

public interface RecruiterManageService {

    CommonRecruiterInformationResponse getCommonInforOfRecruiter(long recruiterId);

    ResponseDataPagination getRecruitersOfCompany(Integer pageNo, Integer pageSize, long companyId);

    RecruiterProfileResponse updateRecruiterInformation(long recruiterId, RecruiterUpdateProfileRequest request, MultipartFile multipartFile) throws Exception;

    RecruiterProfileResponse getRecruiterProfile(long userId);

}
