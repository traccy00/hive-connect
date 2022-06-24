package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.CV;
import fpt.edu.capstone.repository.CVRepository;
import fpt.edu.capstone.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CVServiceImpl implements CVService {

    @Autowired
    CVRepository cvRepository;

    public List<CV> findCvByCandidateId(Long candidateId) {
        return cvRepository.findCvByCandidateId(candidateId);
    }

    @Override
    public void insertCv(long candidateId, long isDeleted, String summary, LocalDateTime createAt, LocalDateTime updateAt) {
        cvRepository.insertCv(candidateId, isDeleted, summary, createAt, updateAt);
    }

    @Override
    public Optional<CV> findCvById(long id) {
        return cvRepository.findById(id);
    }

    @Override
    public void updateSummary(long cvId, String newSummary) {
        cvRepository.updateSummary(cvId, newSummary);
    }

    public CV getCVByCandidateId(long candidateId) { //Nam
        return cvRepository.getByCandidateId(candidateId);
    }
}
