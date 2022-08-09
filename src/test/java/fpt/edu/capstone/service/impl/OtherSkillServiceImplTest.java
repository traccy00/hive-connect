package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.OtherSkill;
import fpt.edu.capstone.repository.OtherSkillRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
public class OtherSkillServiceImplTest {
    @InjectMocks
    OtherSkillServiceImpl otherSkillService;

    @Mock
    OtherSkillRepository otherSkillRepository;

    @Test
    void getListOtherSkillByCvIdTest(){
        List<OtherSkill> otherSkill = new ArrayList<>();
        for (OtherSkill o: otherSkill) {
            o.setId(1L);
            o.setCvId(1L);
            o.setSkillName("Java");
            otherSkill.add(o);
        }
        Mockito.when(otherSkillRepository.getListOtherSkillByCvId(ArgumentMatchers.anyLong())).thenReturn(otherSkill);
        otherSkill = otherSkillService.getListOtherSkillByCvId(1L);
        assertEquals(0, otherSkill.size());
    }

    @Test
    void insertOtherSkill(){
        OtherSkill o = new OtherSkill();
        o.setId(1L);
        o.setCvId(1L);
        o.setSkillName("Java");
        Mockito.when(otherSkillRepository.save(ArgumentMatchers.any(OtherSkill.class))).thenReturn(o);
        OtherSkill otherSkill = otherSkillService.insertOtherSkill(o);
        assertEquals(1L, otherSkill.getId());
    }

    @Test
    void updateOtherSKill(){
        OtherSkill o = new OtherSkill();
        o.setId(1L);
        o.setCvId(1L);
        o.setSkillName("Java");
        otherSkillService.updateOtherSKill(o);
    }

    @Test
    void deleteOtherSkill(){
        OtherSkill o = new OtherSkill();
        o.setId(1L);
        otherSkillService.deleteOtherSkill(o);
    }

    @Test
    void getOtherSkillById(){
        OtherSkill otherSkill = new OtherSkill();
        otherSkill.setId(1L);
       Mockito.when(otherSkillRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(otherSkill));
       Optional<OtherSkill> other = otherSkillService.getOtherSkillById(1L);
       assertEquals(1L, other.get().getId());
    }

}
