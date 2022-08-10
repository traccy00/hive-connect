package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.MajorLevel;
import fpt.edu.capstone.repository.MajorLevelRepository;
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
public class MajorLevelServiceImplTest {
    @InjectMocks
    MajorLevelServiceImpl majorLevelService;

    @Mock
    MajorLevelRepository majorLevelRepository;

    @Test
    void getListMajorLevelByCvIdTest(){
        List<MajorLevel> m = new ArrayList<>();
        for (MajorLevel majorLevel: m) {
            majorLevel.setId(1L);
            majorLevel.setCvId(1L);
            majorLevel.setMajorId(1L);
            majorLevel.setStatus(true);
            m.add(majorLevel);
        }
        Mockito.when(majorLevelRepository.getListMajorLevelByCvId(ArgumentMatchers.anyLong())).thenReturn(m);
        List<MajorLevel> ml = majorLevelService.getListMajorLevelByCvId(1L);
        assertEquals(0, ml.size());
    }

    @Test
    void insertNewMajorLevelTest(){
        MajorLevel majorLevel = new MajorLevel();
            majorLevel.setId(1L);
            majorLevel.setCvId(1L);
            majorLevel.setMajorId(1L);
            majorLevel.setStatus(true);
            Mockito.when(majorLevelRepository.save(ArgumentMatchers.any(MajorLevel.class))).thenReturn(majorLevel);
            MajorLevel result = majorLevelService.insertNewMajorLevel(majorLevel);
            assertEquals(1L, result.getId());
    }

    @Test
    void deleteMajorLevelTest(){
        MajorLevel majorLevel = new MajorLevel();
        majorLevel.setId(1L);
        majorLevelService.deleteMajorLevel(majorLevel);
    }

    @Test
    void updateMajorLevelTest(){
        MajorLevel majorLevel = new MajorLevel();
        majorLevel.setFieldId(1L);
        majorLevel.setLevel("senior");
        majorLevel.setId(1L);
        majorLevel.setCvId(1L);
        majorLevel.setMajorId(1L);
        majorLevel.setStatus(true);
        majorLevelService.updateMajorLevel(majorLevel);
    }

    @Test
    void getMajorLevelByIdTest(){
        MajorLevel majorLevel = new MajorLevel();
        majorLevel.setFieldId(1L);
        majorLevel.setLevel("senior");
        majorLevel.setId(1L);
        majorLevel.setCvId(1L);
        majorLevel.setMajorId(1L);
        majorLevel.setStatus(true);
        Mockito.when(majorLevelRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(majorLevel));
        Optional<MajorLevel> optional =  majorLevelService.getMajorLevelById(1L);
        assertEquals(1L, optional.get().getId());
    }
}
