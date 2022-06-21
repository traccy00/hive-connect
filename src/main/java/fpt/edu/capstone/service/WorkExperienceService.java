package fpt.edu.capstone.service;

import fpt.edu.capstone.atmpCandidate.WorkExperience;

import java.util.List;

public interface WorkExperienceService {
    List<WorkExperience> getListWorkExperienceByCvId(Long cvId);
}
