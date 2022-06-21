package fpt.edu.capstone.service;


import fpt.edu.capstone.atmpCandidate.OtherSkill;

import java.util.List;

public interface OtherSkillService {
    List<OtherSkill> getListOtherSkillByCvId(Long cvId);
}
