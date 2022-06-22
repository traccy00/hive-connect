package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.WorkExperience;

import java.util.List;

public interface WorkExperienceService {
    List<WorkExperience> getListWorkExperienceByCvId(Long cvId);
}
