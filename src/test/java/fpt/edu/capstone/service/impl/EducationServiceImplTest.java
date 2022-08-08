package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Education;
import fpt.edu.capstone.repository.EducationRepository;
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
public class EducationServiceImplTest {
    @InjectMocks
    EducationServiceImpl educationService;

    @Mock
    EducationRepository educationRepository;

    @Test
    void getListEducationByCvIdTest(){
        //Khởi tạo List education
        List<Education> list = new ArrayList<>();
        Education education = new Education();
        //nhập thông tin
        education.setId(1L);

        //add list
        list.add(education);

        Mockito.when(educationRepository.getListEducationByCvId(ArgumentMatchers.anyLong())).thenReturn(list);

        List<Education> educationList = educationService.getListEducationByCvId(1L);

        //so sánh kết quả
        assertEquals(1, educationList.size());

    }

    @Test
    void insertEducationTest(){
        Education education = new Education();
        education.setId(1L);

        Mockito.when(educationRepository.save(ArgumentMatchers.any(Education.class))).thenReturn(education);

        Education result = educationService.insertEducation(education);

        assertEquals(1L, result.getId());
    }

    @Test
    void updateEducationTest(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime start = LocalDate.parse("2022-06-21", formatter).atStartOfDay();
        LocalDateTime end = LocalDate.parse("2022-06-28", formatter).atStartOfDay();
        Education education = new Education();
        education.setId(1L);
        education.setSchool("FPT");
        education.setMajor("IT");
        education.setStartDate(start);
        education.setEndDate(end);
        education.setDescription("Test Update");
        education.setStudying(true);

        //ĐỐI VỚI HÀM VOID THÌ KHÔNG CẦN WHEN
//        Mockito.when(educationRepository.updateEducation(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
//                ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.anyString(),
//                ArgumentMatchers.anyBoolean(), ArgumentMatchers.anyLong())).thenReturn(education);

        educationService.updateEducation(education);
    }

    @Test
    void getEducationByIdTest(){
        Education education = new Education();
        education.setId(1L);
        education.setDescription("Giáo dục");

        Mockito.when(educationRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(education));

        Optional<Education> optional = educationService.getEducationById(1L);

        assertEquals(1L, optional.get().getId());
    }

    @Test
    void deleteEducation(){
        Education education = new Education();
        education.setId(1L);
        educationService.deleteEducation(education.getId());
    }
}
