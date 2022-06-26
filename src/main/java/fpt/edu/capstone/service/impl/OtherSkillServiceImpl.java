package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Language;
import fpt.edu.capstone.entity.OtherSkill;
import fpt.edu.capstone.repository.OtherSkillReposiroty;
import fpt.edu.capstone.service.OtherSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OtherSkillServiceImpl implements OtherSkillService {

    @Autowired
    OtherSkillReposiroty otherSkillReposiroty;

    @Override
    public List<OtherSkill> getListOtherSkillByCvId(Long cvId) {
        return otherSkillReposiroty.getListOtherSkillByCvId(cvId);
    }

    @Override
    public OtherSkill insertOtherSkill(OtherSkill newOtherSkill) {
        return otherSkillReposiroty.save(newOtherSkill);
    }

    @Override
    public void updateOtherSKill(OtherSkill otherSkill) {
        otherSkillReposiroty.updateOtherSkill(otherSkill.getSkillName(), otherSkill.getLevel(), otherSkill.getId());
    }

    @Override
    public void deleteOtherSkill(OtherSkill otherSkill) {
        otherSkillReposiroty.deleteOtherSkill(otherSkill.getId());
    }

    @Override
    public Optional<OtherSkill> getOtherSkillById(long id) {
        return  otherSkillReposiroty.findById(id);
    }
}
