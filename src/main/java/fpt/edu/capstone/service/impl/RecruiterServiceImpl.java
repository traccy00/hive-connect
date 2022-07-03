package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.AppliedJobByRecruiterResponse;
import fpt.edu.capstone.dto.admin.user.RecruiterManageResponse;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.recruiter.RecruiterProfileResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterUpdateProfileRequest;
import fpt.edu.capstone.entity.Candidate;
import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.entity.Recruiter;
import fpt.edu.capstone.entity.Users;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.CompanyRepository;
import fpt.edu.capstone.repository.RecruiterRepository;
import fpt.edu.capstone.repository.UserRepository;
import fpt.edu.capstone.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecruiterServiceImpl implements RecruiterService {

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public RecruiterProfileResponse getRecruiterProfile(long userId) {
        RecruiterProfileResponse recruiterProfile = new RecruiterProfileResponse();
        Users user = userRepository.getUserById(userId);
        if(user == null) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        //role recruiter = 2
        Recruiter recruiter = recruiterRepository.getRecruiterProfileByUserId(userId);
        if(recruiter == null) {
            throw new HiveConnectException("Recruiter doesn't exist");
        }
        recruiterProfile.setUserName(user.getUsername());
        recruiterProfile.setAvatar(user.getAvatar());

        if(recruiter.getCompanyId() != 0) {
            long companyId = recruiter.getCompanyId();
            Company company = companyRepository.getCompanyById(companyId);
            recruiterProfile.setCompanyId(companyId);
            recruiterProfile.setCompanyName(company.getName());
            recruiterProfile.setCompanyAddress(company.getAddress());
        }
        recruiterProfile.setId(recruiter.getId());
        recruiterProfile.setEmail(user.getEmail());
        recruiterProfile.setFullName(recruiter.getFullName());
        recruiterProfile.setPhone(recruiter.getPhoneNumber());
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
        recruiter.setCreateAt(nowDate);
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
        recruiterRepository.updateAvatar(img,id);
    }

    @Override
    public void updateCompany(long companyId, long id) {
        recruiterRepository.updateCompany(companyId, id);
    }

    @Override
    public Page<RecruiterManageResponse> searchRecruitersForAdmin(Pageable pageable, String username, String email) {
        return recruiterRepository.searchRecruitersForAdmin(pageable, username, email);
    }
}
