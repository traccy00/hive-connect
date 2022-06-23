package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.OtherSkill;
import fpt.edu.capstone.repository.OtherSkillReposiroty;
import fpt.edu.capstone.service.OtherSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OtherSkillServiceImpl implements OtherSkillService {

    @Autowired
    OtherSkillReposiroty otherSkillReposiroty;

    @Override
    public List<OtherSkill> getListOtherSkillByCvId(Long cvId) {
        return otherSkillReposiroty.getListOtherSkillByCvId(cvId);
    }

    @Override
    public void insertOtherSkill(String skillName, long cvId, String level) {
        otherSkillReposiroty.insertOtherSkill(skillName, cvId, level);
    }
}
