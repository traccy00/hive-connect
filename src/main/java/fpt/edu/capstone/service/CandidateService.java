package fpt.edu.capstone.service;

import fpt.edu.capstone.atmpCandidate.Candidate;

import java.util.List;
import java.util.Optional;

public interface CandidateService  {
    List<Candidate> getAllCandidate() throws Exception;

    void insertCandidate(Candidate candidate);

    void updateCandidate(Candidate candidate, long id);

    Optional<Candidate> findById(long id);

    boolean existsById(long id);

    Candidate getById(long id);

}
