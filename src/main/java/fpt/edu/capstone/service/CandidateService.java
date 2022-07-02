package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.Candidate;

import java.time.LocalDateTime;
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

}
