package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.Education;

import java.time.LocalDateTime;
import java.util.List;

public interface EducationService {
    List<Education> getListEducationByCvId(Long cvId);

    void insertEducation(long cvId, String school, String major, LocalDateTime startDate, LocalDateTime endDate, String description, boolean isStudying);
}
