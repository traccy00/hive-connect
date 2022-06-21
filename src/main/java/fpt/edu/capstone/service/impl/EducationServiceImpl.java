package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.atmpCandidate.Education;
import fpt.edu.capstone.repository.EducationReposiroty;
import fpt.edu.capstone.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EducationServiceImpl implements EducationService {

    @Autowired
    EducationReposiroty educationReposiroty;

    @Override
    public List<Education> getListEducationByCvId(Long cvId) {
        return educationReposiroty.getListEducationByCvId(cvId);
    }
}
