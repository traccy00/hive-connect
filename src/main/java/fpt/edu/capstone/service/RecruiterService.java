package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.AppliedJobByRecruiterResponse;
import fpt.edu.capstone.dto.admin.user.RecruiterManageResponse;
import fpt.edu.capstone.dto.recruiter.ApprovalLicenseRequest;
import fpt.edu.capstone.dto.recruiter.RecruiterProfileResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterUpdateProfileRequest;
import fpt.edu.capstone.dto.recruiter.UploadBusinessLicenseRequest;
import fpt.edu.capstone.entity.Recruiter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface RecruiterService {
    RecruiterProfileResponse getRecruiterProfile(long userId);

    boolean existById(long recId);

    Recruiter getRecruiterById(long id);

    Optional <Recruiter> findRecruiterByUserId(long userId);

    Optional<Recruiter> findById(long id);

    Recruiter insertRecruiter(long userId);

    void updateRecruiterInformation(RecruiterUpdateProfileRequest recruiterUpdateProfileRequest);

    List<AppliedJobByRecruiterResponse> getListAppliedByForRecruiter(long recruiterId);

    void updateRecruiterAvatar(String img, long id);

    Page<RecruiterManageResponse> searchRecruitersForAdmin(Pageable pageable, String username, String email);

    void updateCompany(long companyId, long id);

    Page<Recruiter> getRecruiterByCompanyId(long pageNo, long quantity, long companyId);

    Recruiter uploadLicense(long recruiterId,
                            MultipartFile businessMultipartFile,
                            MultipartFile additionalMultipartFile) throws Exception;

    List<Recruiter> searchLicenseApprovalForAdmin(String businessApprovalStatus, String additionalApprovalStatus);

    Recruiter approveLicense(ApprovalLicenseRequest request);
}
