package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.admin.CommonRecruiterInformationResponse;
import fpt.edu.capstone.dto.banner.UploadBannerRequest;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.company.CompanyInformationResponse;
import fpt.edu.capstone.dto.recruiter.DetailPurchasedPackageResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterProfileResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterUpdateProfileRequest;
import fpt.edu.capstone.entity.Banner;
import fpt.edu.capstone.entity.Payment;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.utils.ResponseDataPagination;

import java.util.List;

public interface RecruiterManageService {

    CommonRecruiterInformationResponse getCommonInforOfRecruiter(long recruiterId);

    ResponseDataPagination getRecruitersOfCompany(Integer pageNo, Integer pageSize, long companyId);

    RecruiterProfileResponse updateRecruiterInformation(long recruiterId, RecruiterUpdateProfileRequest request) throws Exception;

    RecruiterProfileResponse getRecruiterProfile(long userId);

    CompanyInformationResponse getCompanyInformation(long companyId, long recruiterId);

//    ResponseDataPagination findCV(Integer pageNo, Integer pageSize, //int experienceOption,
//                                  String candidateAddress,
//                                  String techStack);
//
//    ResponseDataPagination findCVTest(Integer pageNo, Integer pageSize, int experienceOption,
//                                  String candidateAddress, String techStack);

    void uploadBanner(long recruiterId, UploadBannerRequest request);

    DetailPurchasedPackageResponse getDetailPurchasedPackage(long recruiterId, long paymentId);

    ResponseDataPagination getJobOfRecruiter(Integer pageNo, Integer pageSize, long paymentId);

    ResponseDataPagination findCVFilter(Integer pageNo, Integer pageSize, String experience, String candidateAddress, String techStack);

}
