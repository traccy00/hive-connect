package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.JobHashtag;
import fpt.edu.capstone.repository.JobHashTagRepository;
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
public class JobHashTagServiceImplTest {
    @InjectMocks
    JobHashTagServiceImpl jobHashTagService;

    @Mock
    JobHashTagRepository jobHashTagRepository;

    @Test
    void getHashTagOfJobTest(){
        List<JobHashtag> list = new ArrayList<>();
        for (JobHashtag jh: list) {
            jh.setId(1L);
            jh.setJobId(1L);
            jh.setHashtagId(1L);
            list.add(jh);
        }
        Mockito.when(jobHashTagRepository.getJobHashTagByJobId(ArgumentMatchers.anyLong())).thenReturn(list);
        List<JobHashtag> hashtagList = jobHashTagService.getHashTagOfJob(1L);
        assertEquals(0, hashtagList.size());
    }
}
