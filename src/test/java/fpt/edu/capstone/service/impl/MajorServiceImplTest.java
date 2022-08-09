package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.CV;
import fpt.edu.capstone.entity.Major;
import fpt.edu.capstone.repository.CVRepository;
import fpt.edu.capstone.repository.MajorRepository;
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
public class MajorServiceImplTest {
    @InjectMocks
    MajorServiceImpl majorService;

    @Mock
    MajorRepository majorRepository;

    @Mock
    CVRepository cvRepository;

    @Test
    void getNameByMajorIdTest(){
        Major major = new Major();
        major.setId(1L);
        major.setMajorName("IT");
        Mockito.when(majorRepository.getNameByMajorId(ArgumentMatchers.anyLong())).thenReturn("IT");
        String res = majorService.getNameByMajorId(1L);
        assertEquals("IT", res);
    }

    @Test
    void getAllMajorByFieldIdTest(){
        List<Major> major = new ArrayList<>();
        for (Major m : major) {
            m.setId(1L);
            m.setFieldId(1L);
            m.setMajorName("IT");
            major.add(m);
        }
        Mockito.when(majorRepository.getAllMajorByFieldId(ArgumentMatchers.anyLong())).thenReturn(major);
        List<Major>  m = majorService.getAllMajorByFieldId(1L);
        assertEquals(0, m.size());
    }

    @Test
    void getMajorNameByCVIdTest(){
        CV cv = new CV();
        cv.setId(1L);
        List <String>  list = new ArrayList<>();
        Mockito.when(majorRepository.getMajorNameByCVId(ArgumentMatchers.anyLong())).thenReturn(list);
        list = majorService.getMajorNameByCVId(1L);
        assertEquals(0, list.size());
    }
}
