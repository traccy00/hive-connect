package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.Education;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EducationService {
    List<Education> getListEducationByCvId(Long cvId);

    void insertEducation(long cvId, String school, String major, LocalDateTime startDate, LocalDateTime endDate, String description, boolean isStudying);

    void updateEducation(Education updateEducation);

    Optional<Education> getEducationById(long educationId);

    void deleteEducation(long id);
}
