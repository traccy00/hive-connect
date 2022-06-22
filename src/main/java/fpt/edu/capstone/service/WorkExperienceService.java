package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.WorkExperience;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkExperienceService {
    List<WorkExperience> getListWorkExperienceByCvId(Long cvId);

    void insertWorkExperience(long cvId, String companyName, String position, LocalDateTime startDate, LocalDateTime endDate, String description);
}
