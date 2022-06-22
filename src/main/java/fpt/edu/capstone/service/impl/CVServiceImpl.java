package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.CV;
import fpt.edu.capstone.repository.CVReposiroty;
import fpt.edu.capstone.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CVServiceImpl implements CVService {

    @Autowired
    CVReposiroty cvReposiroty;


    @Override
    public CV findCvByCandidateId(Long candidateId) {
        return cvReposiroty.findCvByCandidateId(candidateId);
    }
}
