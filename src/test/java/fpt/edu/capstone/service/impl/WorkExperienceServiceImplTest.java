package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.WorkExperience;
import fpt.edu.capstone.repository.WorkExperienceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
public class WorkExperienceServiceImplTest {
    @InjectMocks
    WorkExperienceServiceImpl workExperienceService;

    @Mock
    WorkExperienceRepository workExperienceRepository;

    @Test
    void getListWorkExperienceByCvIdTest(){
        List<WorkExperience> list = new ArrayList<>();
        WorkExperience workExperience = new WorkExperience();
        workExperience.setCvId(1L);
        list.add(workExperience);
        Mockito.when(workExperienceRepository.getListWorkExperienceByCvId(ArgumentMatchers.anyLong())).thenReturn(list);
        List <WorkExperience> workExperiences = workExperienceService.getListWorkExperienceByCvId(1L);
        assertEquals(1, workExperiences.size());
    }

    @Test
    void insertWorkExperienceTest(){
        WorkExperience workExperience = new WorkExperience();
        workExperience.setId(1L);
        Mockito.when(workExperienceRepository.save(ArgumentMatchers.any(WorkExperience.class))).thenReturn(workExperience);
        WorkExperience result = workExperienceService.insertWorkExperience(workExperience);
        assertEquals(1L, result.getId());
    }

    @Test
    void updateWordExperience(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = LocalDate.parse("2022-06-21", formatter).atStartOfDay();
        LocalDateTime end = LocalDate.parse("2022-06-28", formatter).atStartOfDay();

        WorkExperience workExperience = new WorkExperience();
        workExperience.setId(1L);
        workExperience.setCvId(1L);
        workExperience.setCompanyName("FPT Software");
        workExperience.setPosition("senior");
        workExperience.setStartDate(start);
        workExperience.setEndDate(end);
        workExperience.setDescription("Làm việc tại đây");
        workExperience.setWorking(true);

        workExperienceService.updateWordExperience(workExperience);
    }

    @Test
    void deleteWordExperienceTest(){
        WorkExperience workExperience = new WorkExperience();
        workExperience.setId(1L);
        workExperienceService.deleteWordExperience(workExperience);
    }

    @Test
    void getWorkExperienceByIdTest(){
        WorkExperience workExperience = new WorkExperience();
        workExperience.setId(1L);
        workExperience.setCvId(123L);
        workExperience.setDescription("Nhiều năm kinh nghiệm");
        Mockito.when(workExperienceRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(workExperience));

        Optional<WorkExperience> optional = workExperienceService.getWorkExperienceById(1L);

        assertEquals(1L, optional.get().getId());
    }
}
