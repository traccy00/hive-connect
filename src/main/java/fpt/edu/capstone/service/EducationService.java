package fpt.edu.capstone.service;

import fpt.edu.capstone.atmpCandidate.Education;

import java.util.List;

public interface EducationService {
    List<Education> getListEducationByCvId(Long cvId);
}
