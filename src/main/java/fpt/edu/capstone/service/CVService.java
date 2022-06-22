package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.CV;

public interface CVService {
    CV findCvByCandidateId(Long candidateId);

    CV getCVByCandidateId(long candidateId);
}
