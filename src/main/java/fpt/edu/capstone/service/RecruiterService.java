package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.AppliedJobByRecruiterResponse;
import fpt.edu.capstone.dto.admin.user.RecruiterManageResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterProfileResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterUpdateProfileRequest;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.entity.Recruiter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface RecruiterService {
    RecruiterProfileResponse getRecruiterProfile(long userId);

    boolean existById(long recId);

    Recruiter getRecruiterById(long id);

    Optional <Recruiter> findRecruiterByUserId(long userId);

    Optional<Recruiter> findById(long id);

    Optional<Recruiter> findByPhoneNumber (String phone);

    Recruiter insertRecruiter(long userId);

    void updateRecruiterInformation(RecruiterUpdateProfileRequest recruiterUpdateProfileRequest);

    List<AppliedJobByRecruiterResponse> getListAppliedByForRecruiter(long recruiterId);

    void updateRecruiterAvatar(String img, long id);

    Page<RecruiterManageResponse> searchRecruitersForAdmin(Pageable pageable, String username, String email);

    void updateCompany(long companyId, long id);

    Page<Recruiter> getRecruiterByCompanyId(long pageNo, long quantity, long companyId);
}
