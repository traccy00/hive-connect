package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.WorkExperience;
import fpt.edu.capstone.repository.WorkExperienceRepository;
import fpt.edu.capstone.service.WorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WorkExperienceServiceImpl implements WorkExperienceService {

    @Autowired
    WorkExperienceRepository workExperienceRepository;

    @Override
    public List<WorkExperience> getListWorkExperienceByCvId(Long cvId) {
        return workExperienceRepository.getListWorkExperienceByCvId(cvId);
    }
}
