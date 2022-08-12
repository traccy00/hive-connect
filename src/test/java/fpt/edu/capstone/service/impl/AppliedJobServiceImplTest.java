package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.company.CompanyResponse;
import fpt.edu.capstone.dto.recruiter.CountCandidateApplyPercentageResponse;
import fpt.edu.capstone.entity.AppliedJob;
import fpt.edu.capstone.repository.AppliedJobRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppliedJobServiceImplTest {

    @Mock
    private ModelMapper mockModelMapper;
    @Mock
    private AppliedJobRepository mockAppliedJobRepository;

    private AppliedJobServiceImpl appliedJobServiceImplUnderTest;

    @Before
    public void setUp() throws Exception {
        appliedJobServiceImplUnderTest = new AppliedJobServiceImpl(mockModelMapper, mockAppliedJobRepository);
    }

    @Test
    public void testGetAppliedJobPendingApproval() {
        final AppliedJob appliedJob = new AppliedJob(0L, 0L, 0L, false, "approvalStatus", false, "cvUploadUrl");
        when(mockAppliedJobRepository.getAppliedJobPendingApproval(0L, 0L, "status", false)).thenReturn(appliedJob);
        final AppliedJob result = appliedJobServiceImplUnderTest.getAppliedJobPendingApproval(0L, 0L);
    }

    @Test
    public void testGetAppliedJobBefore() {
        final AppliedJob appliedJob = new AppliedJob(0L, 0L, 0L, false, "approvalStatus", false, "cvUploadUrl");
        when(mockAppliedJobRepository.getByCandidateIdAndJobId(0L, 0L)).thenReturn(appliedJob);
        final AppliedJob result = appliedJobServiceImplUnderTest.getAppliedJobBefore(0L, 0L);
    }

    @Test
    public void testGetCvAppliedJob() {
        final Page<AppliedJob> appliedJobs = new PageImpl<>(
                Arrays.asList(new AppliedJob(0L, 0L, 0L, false, "approvalStatus", false, "cvUploadUrl")));
        when(mockAppliedJobRepository.getCvAppliedJob(any(Pageable.class), eq(0L), eq(false))).thenReturn(appliedJobs);
        final Page<AppliedJob> result = appliedJobServiceImplUnderTest.getCvAppliedJob(PageRequest.of(0, 1), 0L, false);
    }

    @Test
    public void testGetCvAppliedJob_AppliedJobRepositoryReturnsNoItems() {
        when(mockAppliedJobRepository.getCvAppliedJob(any(Pageable.class), eq(0L), eq(false)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        final Page<AppliedJob> result = appliedJobServiceImplUnderTest.getCvAppliedJob(PageRequest.of(0, 1), 0L, false);
    }

    @Test
    public void testGetTop12Companies() {
        when(mockAppliedJobRepository.getTop12Companies()).thenReturn(Arrays.asList());
        final List<CompanyResponse> result = appliedJobServiceImplUnderTest.getTop12Companies();
    }

    @Test
    public void testGetTop12Companies_AppliedJobRepositoryReturnsNoItems() {
        when(mockAppliedJobRepository.getTop12Companies()).thenReturn(Collections.emptyList());
        final List<CompanyResponse> result = appliedJobServiceImplUnderTest.getTop12Companies();
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testSearchAppliedJobsOfCandidate() {
        final Page<AppliedJob> appliedJobs = new PageImpl<>(
                Arrays.asList(new AppliedJob(0L, 0L, 0L, false, "approvalStatus", false, "cvUploadUrl")));
        when(mockAppliedJobRepository.searchAppliedJobsOfCandidate(any(Pageable.class), eq(0L),
                eq("approvalStatus"))).thenReturn(appliedJobs);
        final Page<AppliedJob> result = appliedJobServiceImplUnderTest.searchAppliedJobsOfCandidate(
                PageRequest.of(0, 1), 0L, "approvalStatus");
    }

    @Test
    public void testSearchAppliedJobsOfCandidate_AppliedJobRepositoryReturnsNoItems() {
        when(mockAppliedJobRepository.searchAppliedJobsOfCandidate(any(Pageable.class), eq(0L),
                eq("approvalStatus"))).thenReturn(new PageImpl<>(Collections.emptyList()));
        final Page<AppliedJob> result = appliedJobServiceImplUnderTest.searchAppliedJobsOfCandidate(
                PageRequest.of(0, 1), 0L, "approvalStatus");
    }

    @Test
    public void testCountApplyPercentage() {
        when(mockAppliedJobRepository.countApplyPercentage(0L)).thenReturn(Arrays.asList());
        final List<CountCandidateApplyPercentageResponse> result = appliedJobServiceImplUnderTest.countApplyPercentage(
                0L);
    }

    @Test
    public void testCountApplyPercentage_AppliedJobRepositoryReturnsNoItems() {
        when(mockAppliedJobRepository.countApplyPercentage(0L)).thenReturn(Collections.emptyList());
        final List<CountCandidateApplyPercentageResponse> result = appliedJobServiceImplUnderTest.countApplyPercentage(
                0L);
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testCountAppliedCVOfJob() {
        when(mockAppliedJobRepository.countAppliedCVOfAJob(0L)).thenReturn(0);
        final int result = appliedJobServiceImplUnderTest.countAppliedCVOfJob(0L);
        assertThat(result).isEqualTo(0);
    }
}
