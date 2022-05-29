package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.sprint1.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CandidateService  {
    List<Candidate> getAllCandidate() throws Exception;

    void insertCandidate(Candidate candidate);

    void updateCandidate(Candidate candidate, Long id);
    Optional<Candidate> findById(Long id);


}
