package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.admin.user.CandidateManageResponse;
import fpt.edu.capstone.entity.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CandidateService  {
    List<Candidate> getAllCandidate() throws Exception;

    void insertCandidate( long userId);

    void updateCandidate(Candidate candidate, long id);

    Optional<Candidate> findById(long id);

    boolean existsById(long id);

    Candidate getById(long id);

    Optional<Candidate> findCandidateByUserId(long userId);

    void updateCandidateInformation(Candidate updatedCandidate);

    void updateIsNeedJob(boolean isNeedJob, long id);

    void updateAvatarUrl(String avatarId, long id);

    Page<CandidateManageResponse> searchCandidatesForAdmin(Pageable pageable, String username, String email);
}
