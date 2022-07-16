package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.AppliedJobByRecruiterResponse;
import fpt.edu.capstone.dto.UploadFileRequest;
import fpt.edu.capstone.dto.admin.user.RecruiterManageResponse;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.recruiter.ApprovalLicenseRequest;
import fpt.edu.capstone.dto.recruiter.RecruiterProfileResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterUpdateProfileRequest;
import fpt.edu.capstone.dto.recruiter.UploadBusinessLicenseRequest;
import fpt.edu.capstone.entity.*;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.CompanyRepository;
import fpt.edu.capstone.repository.RecruiterRepository;
import fpt.edu.capstone.repository.UserRepository;
import fpt.edu.capstone.service.RecruiterService;
import fpt.edu.capstone.utils.Enums;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RecruiterServiceImpl implements RecruiterService {

    private final RecruiterRepository recruiterRepository;

    private final CompanyRepository companyRepository;

    private final UserRepository userRepository;

    private final AmazonS3ClientService amazonS3ClientService;

    @Override
    public RecruiterProfileResponse getRecruiterProfile(long userId) {
        RecruiterProfileResponse recruiterProfile = new RecruiterProfileResponse();
        Users user = userRepository.getUserById(userId);
        if (user == null) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        //role recruiter = 2
        Recruiter recruiter = recruiterRepository.getRecruiterProfileByUserId(userId);
        if (recruiter == null) {
            throw new HiveConnectException("Recruiter doesn't exist");
        }
        recruiterProfile.setUserName(user.getUsername());
        recruiterProfile.setAvatar(user.getAvatar());

        if (recruiter.getCompanyId() != 0) {
            long companyId = recruiter.getCompanyId();
            Company company = companyRepository.getCompanyById(companyId);
            recruiterProfile.setCompanyId(companyId);
            recruiterProfile.setCompanyName(company.getName());
            recruiterProfile.setCompanyAddress(company.getAddress());
        }
        recruiterProfile.setId(recruiter.getId());
        recruiterProfile.setEmail(user.getEmail());
        recruiterProfile.setFullName(recruiter.getFullName());
        recruiterProfile.setGender(recruiter.isGender());
        recruiterProfile.setPosition(recruiter.getPosition());
        recruiterProfile.setLinkedinAccount(recruiter.getLinkedInAccount());
        recruiterProfile.setBusinessLicense(recruiter.getBusinessLicense());
        recruiterProfile.setAvatar(recruiter.getAvatarUrl());
        return recruiterProfile;
    }

    @Override
    public boolean existById(long recId) {
        return recruiterRepository.existsById(recId);
    }

    @Override
    public Recruiter getRecruiterById(long id) {
        return recruiterRepository.getById(id);
    }

    @Override
    public Optional<Recruiter> findRecruiterByUserId(long userId) {
        return recruiterRepository.findByUserId(userId);
    }

    @Override
    public Optional<Recruiter> findById(long id) {
        return recruiterRepository.findById(id);
    }

    @Override
    public Recruiter insertRecruiter(long userId) {
        Recruiter recruiter = new Recruiter();
        recruiter.setUserId(userId);
        LocalDateTime nowDate = LocalDateTime.now();
        recruiter.setCreatedAt(nowDate);
        return recruiterRepository.save(recruiter);
    }

    @Override
    public void updateRecruiterInformation(RecruiterUpdateProfileRequest recruiterUpdateProfileRequest) {
        LocalDateTime nowDate = LocalDateTime.now();
        recruiterRepository.updateCruiterProfile(recruiterUpdateProfileRequest.getFullName(),
                recruiterUpdateProfileRequest.isGender(),
                recruiterUpdateProfileRequest.getPosition(),
                recruiterUpdateProfileRequest.getLinkedinAccount(),
                recruiterUpdateProfileRequest.getBusinessLicense(),
                nowDate,
                recruiterUpdateProfileRequest.getPhone(),
                recruiterUpdateProfileRequest.getAdditionalLicense(),
                recruiterUpdateProfileRequest.getAvatarUrl(),
                recruiterUpdateProfileRequest.getId());
    }

    @Override
    public List<AppliedJobByRecruiterResponse> getListAppliedByForRecruiter(long recruiterId) {
        return recruiterRepository.getListAppliedByForRecruiter(recruiterId);
    }

    @Override
    public void updateRecruiterAvatar(String img, long id) {
        recruiterRepository.updateAvatar(img, id);
    }

    @Override
    public void updateCompany(long companyId, long id) {
        recruiterRepository.updateCompany(companyId, id);
    }

    @Override
    public Page<Recruiter> getRecruiterByCompanyId(long pageNo, long quantity, long companyId) {
        int pageReq = (int) (pageNo >= 1 ? pageNo - 1 : pageNo);
        Pageable pageable = PageRequest.of(pageReq, (int) quantity);
        return recruiterRepository.getRecruiterByCompanyId(companyId, pageable);
    }

    @Override
    public Page<RecruiterManageResponse> searchRecruitersForAdmin(Pageable pageable, String username, String email) {
        return recruiterRepository.searchRecruitersForAdmin(pageable, username, email);
    }

    //check thêm tên file có hợp lệ không
    @Override
    @Transactional(rollbackOn = Exception.class)
    public Recruiter uploadLicense(UploadBusinessLicenseRequest request) {
        System.out.println("tess:" + Enums.ApprovalStatus.APPROVED);
        Optional<Recruiter> optionalRecruiter = findById(request.getRecruiterId());
        if (!optionalRecruiter.isPresent()) {
            throw new HiveConnectException("Người dùng không tồn tại, không thể đăng tải giấy phép kinh doanh");
        }
        if (optionalRecruiter.get().getCompanyId() == 0) {
            throw new HiveConnectException("Nhà tuyển dụng chưa có thông tin công ty. Vui lòng cập nhật thông tin công ty trước.");
        }
        List<UploadFileRequest> fileRequestList = request.getUploadFileRequests();
        if (fileRequestList == null || fileRequestList.isEmpty()) {
            throw new HiveConnectException("Không có file nào được upload");
        }
        for (UploadFileRequest fileRequest : fileRequestList) {

            //check xem đã save được vào amazon chưa

            //"1": business license, "2": additional license
            if (fileRequest.getType().equals("1")) {
                //check xem license đã từng upload lên chưa, trạng thái như thế nào?
                if (optionalRecruiter.get().getBusinessLicense() != null && optionalRecruiter.get().getBusinessLicenseApprovalStatus() != null) {
                    if (optionalRecruiter.get().getBusinessLicenseApprovalStatus().equals(Enums.ApprovalStatus.APPROVED.getStatus())
                            || optionalRecruiter.get().getBusinessLicenseApprovalStatus().equals(Enums.ApprovalStatus.PENDING.getStatus())) {
                        throw new HiveConnectException("Nhà tuyển dụng đã có giấy phép kinh doanh hoặc giấy phép đang được duyệt, không thể thay đổi");
                    }
                }
                //upload file to amazon
                amazonS3ClientService.uploadFile(fileRequest);
                //save to database
                optionalRecruiter.get().setBusinessLicense(fileRequest.getUploadFileName());
                optionalRecruiter.get().setBusinessLicenseUrl(ResponseMessageConstants.AMAZON_SAVE_URL + fileRequest.getUploadFileName());
                optionalRecruiter.get().setBusinessLicenseApprovalStatus(Enums.ApprovalStatus.PENDING.getStatus());
                optionalRecruiter.get().update();
            } else if (fileRequest.getType().equals("2")) {
                if (optionalRecruiter.get().getAdditionalLicense() != null
                        && optionalRecruiter.get().getAdditionalLicenseApprovalStatus() != null) {
                    if (optionalRecruiter.get().getAdditionalLicenseApprovalStatus().equals(Enums.ApprovalStatus.APPROVED.getStatus())
                            || optionalRecruiter.get().getAdditionalLicenseApprovalStatus().equals(Enums.ApprovalStatus.PENDING.getStatus())) {
                        throw new HiveConnectException("Nhà tuyển dụng đã có giấy phép kinh doanh hoặc giấy phép đang được duyệt, không thể thay đổi");
                    }
                }
                //upload file to amazon
                amazonS3ClientService.uploadFile(fileRequest);
                //save to database
                optionalRecruiter.get().setAdditionalLicense(fileRequest.getUploadFileName());
                optionalRecruiter.get().setAdditionalLicenseUrl(ResponseMessageConstants.AMAZON_SAVE_URL + fileRequest.getUploadFileName());
                optionalRecruiter.get().setAdditionalLicenseApprovalStatus(Enums.ApprovalStatus.PENDING.getStatus());
                optionalRecruiter.get().update();
            }
            recruiterRepository.save(optionalRecruiter.get());
        }
        return optionalRecruiter.get();
    }

    @Override
    public List<Recruiter> searchLicenseApprovalForAdmin(String businessApprovalStatus, String additionalApprovalStatus) {
        return recruiterRepository.searchLicenseApprovalForAdmin(businessApprovalStatus, additionalApprovalStatus);
    }

    @Override
    public Recruiter approveLicense(ApprovalLicenseRequest request) {
        Optional<Recruiter> optionalRecruiter = recruiterRepository.findById(request.getRecruiterId());
        if (!optionalRecruiter.isPresent()) {
            throw new HiveConnectException("Người dùng không tồn tại");
        }
        //accept , reject
        if (request.getType().equals("1")) {
            if (request.getApprovalStatus().equals(Enums.ApprovalStatus.APPROVED.getStatus())) {
                optionalRecruiter.get().setBusinessLicenseApprovalStatus(Enums.ApprovalStatus.APPROVED.getStatus());
            } else if (request.getApprovalStatus().equals(Enums.ApprovalStatus.REJECT.getStatus())) {
                optionalRecruiter.get().setBusinessLicenseApprovalStatus(Enums.ApprovalStatus.REJECT.getStatus());
            } else {
                throw new HiveConnectException("Trạng thái approve không đúng, liên hệ admin");
            }
            optionalRecruiter.get().update();
        } else if (request.getType().equals("2")) {
            if (request.getApprovalStatus().equals(Enums.ApprovalStatus.APPROVED.getStatus())) {
                optionalRecruiter.get().setAdditionalLicenseApprovalStatus(Enums.ApprovalStatus.APPROVED.getStatus());
            } else if (request.getApprovalStatus().equals(Enums.ApprovalStatus.REJECT.getStatus())) {
                optionalRecruiter.get().setAdditionalLicenseApprovalStatus(Enums.ApprovalStatus.REJECT.getStatus());
            } else {
                throw new HiveConnectException("Trạng thái approve không đúng, liên hệ admin");
            }
            optionalRecruiter.get().update();
            recruiterRepository.save(optionalRecruiter.get());
        }
        return optionalRecruiter.get();
    }
}
