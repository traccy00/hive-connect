package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.AppliedJobByRecruiterResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterProfileResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterUpdateProfileRequest;
import fpt.edu.capstone.entity.Recruiter;
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

    Recruiter insertRecruiter(long userId);

    void updateRecruiterInformation(RecruiterUpdateProfileRequest recruiterUpdateProfileRequest);

    List<AppliedJobByRecruiterResponse> getListAppliedByForRecruiter(long recruiterId);

    void updateRecruiterAvatar(String img, long id);

    void updateCompany(long companyId, long id);
}
