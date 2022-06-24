package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Education;
import fpt.edu.capstone.repository.EducationReposiroty;
import fpt.edu.capstone.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EducationServiceImpl implements EducationService {

    @Autowired
    EducationReposiroty educationReposiroty;

    @Override
    public List<Education> getListEducationByCvId(Long cvId) {
        return educationReposiroty.getListEducationByCvId(cvId);
    }

    @Override
    public void insertEducation(long cvId, String school, String major, LocalDateTime startDate, LocalDateTime endDate, String description, boolean isStudying) {
        educationReposiroty.insertEducation(cvId, school, major, startDate, endDate, description, isStudying);
    }

    @Override
    public void updateEducation(Education updateEducation) {
        educationReposiroty.updateEducation(updateEducation.getSchool(), updateEducation.getMajor(), updateEducation.getStartDate(), updateEducation.getEndDate(), updateEducation.getDescription(), updateEducation.isStudying(), updateEducation.getId());
    }

    @Override
    public Optional<Education> getEducationById(long educationId) {
        return educationReposiroty.findById(educationId);
    }

    @Override
    public void deleteEducation(long id) {
        educationReposiroty.deleteEducation(id);
    }
}
