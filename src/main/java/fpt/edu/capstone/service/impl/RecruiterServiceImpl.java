package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.recruiter.RecruiterProfileResponse;
import fpt.edu.capstone.entity.sprint1.Company;
import fpt.edu.capstone.entity.sprint1.Recruiter;
import fpt.edu.capstone.entity.sprint1.Users;
import fpt.edu.capstone.exception.ResourceNotFoundException;
import fpt.edu.capstone.repository.CompanyRepository;
import fpt.edu.capstone.repository.RecruiterRepository;
import fpt.edu.capstone.repository.UserRepository;
import fpt.edu.capstone.service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        //role recruiter = 2
        long roleId = 2;
        Users user = userRepository.findById(userId);
        if(user == null) {
            throw new ResourceNotFoundException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        RecruiterProfileResponse recruiterProfile = new RecruiterProfileResponse();
        Recruiter recruiter = recruiterRepository.getRecruiterProfile(userId);
        recruiterProfile.setUserName(user.getUsername());
        recruiterProfile.setAvatar(user.getAvatar());
        //already choose one company
        if(recruiter.getCompanyId() != 0) {
            long companyId = recruiter.getCompanyId();
            Company company = companyRepository.getCompanyById(companyId);
            recruiterProfile.setCompanyId(companyId);
            recruiterProfile.setCompanyName(company.getName());
            recruiterProfile.setCompanyAddress(company.getAddress());
        } else {
            recruiterProfile.setCompanyName(recruiter.getCompanyName());
        }
        recruiterProfile.setId(recruiter.getId());
        recruiterProfile.setEmail(user.getEmail());
        recruiterProfile.setFullName(recruiter.getFullName());
        recruiterProfile.setPhone(recruiter.getPhoneNumber());
        recruiterProfile.setGender(recruiter.getGender());
        recruiterProfile.setPosition(recruiter.getPosition());
        recruiterProfile.setLinkedinAccount(recruiter.getLinkedInAccount());
        recruiterProfile.setBusinessLicense(recruiter.getBusinessLicense());
        return recruiterProfile;
    }
}
