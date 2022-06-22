package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.WorkExperience;
import fpt.edu.capstone.repository.WorkExperienceRepository;
import fpt.edu.capstone.service.WorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class WorkExperienceServiceImpl implements WorkExperienceService {

    @Autowired
    WorkExperienceRepository workExperienceRepository;

    @Override
    public List<WorkExperience> getListWorkExperienceByCvId(Long cvId) {
        return workExperienceRepository.getListWorkExperienceByCvId(cvId);
    }

    @Override
    public void insertWorkExperience(long cvId, String companyName, String position, LocalDateTime startDate, LocalDateTime endDate, String description) {
        workExperienceRepository.insertWorkExperience(cvId, companyName, position, startDate, endDate, description);
    }
}
