package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.CV;
import fpt.edu.capstone.repository.CVRepository;
import fpt.edu.capstone.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CVServiceImpl implements CVService {

    @Autowired
    CVRepository cvRepository;


    @Override
    public CV findCvByCandidateId(Long candidateId) {
        return cvRepository.findCvByCandidateId(candidateId);
    }

    public CV getCVByCandidateId(long candidateId) {
        return cvRepository.getByCandidateId(candidateId);
    }
}
