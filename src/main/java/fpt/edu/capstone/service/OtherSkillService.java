package fpt.edu.capstone.service;


import fpt.edu.capstone.entity.OtherSkill;

import java.util.List;

public interface OtherSkillService {
    List<OtherSkill> getListOtherSkillByCvId(Long cvId);

    void insertOtherSkill(String skillName, long cvId, String level);
}
