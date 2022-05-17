package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.sprint1.Candidate;
import fpt.edu.capstone.repository.AdminRepository;
import fpt.edu.capstone.repository.CandidateRepository;
import fpt.edu.capstone.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements CandidateService {
    @Autowired
    CandidateRepository candidateRepository;
    @Override
    public List<Candidate> getAllCandidate() throws Exception {
        return candidateRepository.findAll();
    }

    @Override
    public void insertCandidate(long userId, String listTechStackId, String fullName, String phoneNumber, boolean gender, Date birthDate, Long socialId, long tapHistoryId, long wishLishId, long searchHistoryId, String cvUrl, long appliedJobId) {
        candidateRepository.insertCandidate(userId,listTechStackId,fullName, phoneNumber, gender,birthDate ,socialId ,tapHistoryId ,wishLishId ,searchHistoryId ,cvUrl ,appliedJobId);
    }

    @Override
    public void updateCandidate(long userId, String listTechStackId, String fullName, String phoneNumber, boolean gender, Date birthDate, Long socialId, long tapHistoryId, long wishLishId, long searchHistoryId, String cvUrl, long appliedJobId, long id) {
        candidateRepository.updateCandidate(userId,listTechStackId,fullName, phoneNumber, gender,birthDate ,socialId ,tapHistoryId ,wishLishId ,searchHistoryId ,cvUrl ,appliedJobId, id);
    }

    @Override
    public Optional<Candidate> findById(Long id){
        return candidateRepository.findById(id);
    }
}
