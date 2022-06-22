package fpt.edu.capstone.service;

import fpt.edu.capstone.atmpCandidate.CV;

public interface CVService {
    CV findCvByCandidateId(Long candidateId);
}
