package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.sprint1.Candidate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CandidateService {
    List<Candidate> getAllCandidate() throws Exception;
    public void insertCandidate(long userId, String listTechStackId, String fullName, String phoneNumber, boolean gender, Date birthDate, Long socialId, long tapHistoryId, long wishLishId, long searchHistoryId, String cvUrl, long appliedJobId);
    public void updateCandidate(long userId, String listTechStackId, String fullName, String phoneNumber, boolean gender, Date birthDate, Long socialId, long tapHistoryId, long wishLishId, long searchHistoryId, String cvUrl, long appliedJobId, long id);
    public Optional<Candidate> findById(Long id);
}
