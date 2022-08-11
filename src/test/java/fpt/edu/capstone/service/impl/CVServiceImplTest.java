package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.CV;
import fpt.edu.capstone.repository.CVRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CVServiceImplTest {

    private CVServiceImpl cvServiceImplUnderTest;

    @Before
    public void setUp() throws Exception {
        cvServiceImplUnderTest = new CVServiceImpl();
        cvServiceImplUnderTest.cvRepository = mock(CVRepository.class);
    }

    private CV cv (){
        CV cv = new CV(0L, 0L, 0L, "summary", "totalExperienceYear");
        return cv;
    }

    @Test
    public void testFindCvByCandidateId() {
        final List<CV> cvList = Arrays.asList(cv());
        when(cvServiceImplUnderTest.cvRepository.findCvByCandidateId(0L)).thenReturn(cvList);
        final List<CV> result = cvServiceImplUnderTest.findCvByCandidateId(0L);
    }

    @Test
    public void testFindCvByCandidateId_CVRepositoryReturnsNoItems() {
        when(cvServiceImplUnderTest.cvRepository.findCvByCandidateId(0L)).thenReturn(Collections.emptyList());
        final List<CV> result = cvServiceImplUnderTest.findCvByCandidateId(0L);
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testFindCvById() {
        final Optional<CV> cv = Optional.of(cv());
        when(cvServiceImplUnderTest.cvRepository.findById(0L)).thenReturn(cv);
        final Optional<CV> result = cvServiceImplUnderTest.findCvById(0L);
    }

    @Test
    public void testFindCvById_CVRepositoryReturnsAbsent() {
        when(cvServiceImplUnderTest.cvRepository.findById(0L)).thenReturn(Optional.empty());
        final Optional<CV> result = cvServiceImplUnderTest.findCvById(0L);
        assertThat(result).isEmpty();
    }

    @Test
    public void testUpdateSummary() {
        cvServiceImplUnderTest.updateSummary(0L, "newSummary");
        verify(cvServiceImplUnderTest.cvRepository).updateSummary(0L, "newSummary");
    }

    @Test
    public void testUpdateUpdatedDateOfCV() {
        cvServiceImplUnderTest.updateUpdatedDateOfCV(0L, LocalDateTime.now());
        verify(cvServiceImplUnderTest.cvRepository).updateUpdatedDateOfCV(0L, LocalDateTime.now());
    }

    @Test
    public void testFindByIdAndCandidateId() {
        final Optional<CV> cv = Optional.of(cv());
        when(cvServiceImplUnderTest.cvRepository.findByIdAndCAndCandidateId(0L, 0L)).thenReturn(cv);
        final Optional<CV> result = cvServiceImplUnderTest.findByIdAndCandidateId(0L, 0L);
    }

    @Test
    public void testFindByIdAndCandidateId_CVRepositoryReturnsAbsent() {
        when(cvServiceImplUnderTest.cvRepository.findByIdAndCAndCandidateId(0L, 0L)).thenReturn(Optional.empty());
        final Optional<CV> result = cvServiceImplUnderTest.findByIdAndCandidateId(0L, 0L);
        assertThat(result).isEmpty();
    }

    @Test
    public void testSave() {
        final CV cv = cv();
        final CV cv1 = cv();
        when(cvServiceImplUnderTest.cvRepository.save(any(CV.class))).thenReturn(cv1);
        cvServiceImplUnderTest.save(cv);
        verify(cvServiceImplUnderTest.cvRepository).save(any(CV.class));
    }

    @Test
    public void testFindCVFilter() {
        final Page<CV> cvPage = new PageImpl<>(Arrays.asList(cv()));
        when(cvServiceImplUnderTest.cvRepository.findCVFilter(any(Pageable.class), eq("experience"),
                eq("candidateAddress"), eq("techStack"))).thenReturn(cvPage);
        final Page<CV> result = cvServiceImplUnderTest.findCVFilter(PageRequest.of(1, 10), "experience",
                "candidateAddress", "techStack");
    }

    @Test
    public void testFindCVFilter_CVRepositoryReturnsNoItems() {
        when(cvServiceImplUnderTest.cvRepository.findCVFilter(any(Pageable.class), eq("experience"),
                eq("candidateAddress"), eq("techStack"))).thenReturn(new PageImpl<>(Collections.emptyList()));
        final Page<CV> result = cvServiceImplUnderTest.findCVFilter(PageRequest.of(1, 10), "experience",
                "candidateAddress", "techStack");
    }

    @Test
    public void testGetCVByCandidateId() {
        final CV cv = new CV(0L, 0L, 0L, "summary", "totalExperienceYear");
        when(cvServiceImplUnderTest.cvRepository.getByCandidateId(0L)).thenReturn(cv);
        final CV result = cvServiceImplUnderTest.getCVByCandidateId(0L);
    }
}
