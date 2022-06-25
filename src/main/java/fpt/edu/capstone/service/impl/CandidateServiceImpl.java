package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Candidate;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.CandidateRepository;
import fpt.edu.capstone.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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
    public void insertCandidate(long userId) {
        candidateRepository.insertCandidate(userId);
    }

    @Override
    public void updateCandidate(Candidate newCandidate, long id) {

    }

    @Override
    public Optional<Candidate> findById(long id) {
        return candidateRepository.findById(id);
    }

    @Override
    public boolean existsById(long id) {
        return candidateRepository.existsById(id);
    }

    @Override
    public Candidate getById(long id) {
        return candidateRepository.getById(id);
    }

    @Override
    public Optional<Candidate> findCandidateByUserId(long userId) {
        return candidateRepository.findCandidateByUserId(userId);
    }

    @Override
    public void updateCandidateInformation(Candidate updatedCandidate) {
        candidateRepository.updateCandidateInformation(updatedCandidate.isGender(), updatedCandidate.getBirthDate(), updatedCandidate.getCountry(), updatedCandidate.getFullName(), updatedCandidate.getAddress(), updatedCandidate.getSocialLink(), updatedCandidate.getIntroduction(), updatedCandidate.getId());
    }

}
