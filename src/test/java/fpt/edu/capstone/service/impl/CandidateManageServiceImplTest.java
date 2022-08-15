package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.job.AppliedJobRequest;
import fpt.edu.capstone.entity.AppliedJob;
import fpt.edu.capstone.entity.CV;
import fpt.edu.capstone.repository.AppliedJobRepository;
import fpt.edu.capstone.utils.Enums;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CandidateManageServiceImplTest {

    @Mock
    CVServiceImpl cvService;

    @Mock
    JobServiceImpl jobService;

    @Mock
    CandidateServiceImpl candidateService;

    @Mock
    AppliedJobServiceImpl appliedJobService;

    @Mock
    AppliedJobRepository appliedJobRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    CandidateManageServiceImpl candidateManageService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    //JobController line 167

    @Test
    void givenRequestCvUrlIsNullAndCvServiceGetCVByCandidateIdReturnNull_whenCallAppliedJob_thenThrowException() {
        when(cvService.getCVByCandidateId(1L)).thenReturn(null);
        AppliedJobRequest request = new AppliedJobRequest();
        request.setCandidateId(1L);
        Exception exception = assertThrows(Exception.class,() -> candidateManageService.appliedJob(request));
        assertEquals("Bạn chưa tạo hồ sơ",exception.getMessage());
    }

    @Test
    void givenJobServiceExistByIdReturnFalse_whenCallAppliedJob_thenThrowException() {
        when(cvService.getCVByCandidateId(1L)).thenReturn(new CV());
        when(jobService.existsById(1L)).thenReturn(false);
        AppliedJobRequest request = new AppliedJobRequest();
        request.setCandidateId(1L);
        request.setCvUrl("abc");
        request.setJobId(1L);
        Exception exception = assertThrows(Exception.class,() -> candidateManageService.appliedJob(request));
        assertEquals(ResponseMessageConstants.JOB_DOES_NOT_EXIST,exception.getMessage());
    }

    @Test
    void givenCandidateServiceExistByIdReturnFalse_whenCallAppliedJob_thenThrowException() {
        when(cvService.getCVByCandidateId(1L)).thenReturn(new CV());
        when(jobService.existsById(1L)).thenReturn(true);
        when(candidateService.existsById(1L)).thenReturn(false);
        AppliedJobRequest request = new AppliedJobRequest();
        request.setCandidateId(1L);
        request.setCvUrl("abc");
        request.setJobId(1L);
        Exception exception = assertThrows(Exception.class,() -> candidateManageService.appliedJob(request));
        assertEquals(ResponseMessageConstants.USER_DOES_NOT_EXIST,exception.getMessage());
    }

    @Test
    void givenAppliedJobServiceGetAppliedJobBeforeReturnNullAndModelMapperMapReturnNull_whenCallAppliedJob_thenAppliedRepositoryAreNotCalled() {
        AppliedJobRequest request = new AppliedJobRequest();
        request.setCandidateId(1L);
        request.setCvUrl("abc");
        request.setJobId(1L);

        when(cvService.getCVByCandidateId(1L)).thenReturn(new CV());
        when(jobService.existsById(1L)).thenReturn(true);
        when(candidateService.existsById(1L)).thenReturn(true);
        when(appliedJobService.getAppliedJobBefore(1L,1L)).thenReturn(null);

        Exception exception = assertThrows(Exception.class,() -> candidateManageService.appliedJob(request));
        verify(appliedJobRepository,times(0)).save(any());
    }

    @Test
    void givenAppliedJobExistAndStatusIsPending_whenCallAppliedJob_thenSaveToDB() throws Exception {
        when(cvService.getCVByCandidateId(1L)).thenReturn(new CV());
        when(jobService.existsById(1L)).thenReturn(true);
        when(candidateService.existsById(1L)).thenReturn(true);
        AppliedJob appliedJob = new AppliedJob();
        appliedJob.setApplied(true);
        appliedJob.setApprovalStatus(Enums.ApprovalStatus.PENDING.getStatus());
        when(appliedJobService.getAppliedJobBefore(1L,1L)).thenReturn(appliedJob);
        AppliedJobRequest request = new AppliedJobRequest();
        request.setCandidateId(1L);
        request.setCvUrl("abc");
        request.setJobId(1L);

        candidateManageService.appliedJob(request);

        verify(appliedJobRepository,times(1)).save(any());
    }

    @Test
    void givenAppliedJobExistAndStatusIsApproved_whenCallAppliedJob_thenThrowException() {
        when(cvService.getCVByCandidateId(1L)).thenReturn(new CV());
        when(jobService.existsById(1L)).thenReturn(true);
        when(candidateService.existsById(1L)).thenReturn(true);
        AppliedJob appliedJob = new AppliedJob();
        appliedJob.setApplied(true);
        appliedJob.setApprovalStatus(Enums.ApprovalStatus.APPROVED.getStatus());
        when(appliedJobService.getAppliedJobBefore(1L,1L)).thenReturn(appliedJob);
        AppliedJobRequest request = new AppliedJobRequest();
        request.setCandidateId(1L);
        request.setCvUrl("abc");
        request.setJobId(1L);

        Exception exception = assertThrows(Exception.class,() -> candidateManageService.appliedJob(request));

        assertEquals("CV này đã được chấp nhận.",exception.getMessage());
    }

    @Test
    void givenAppliedJobExistAndStatusIsReject_whenCallAppliedJob_thenSaveToDB() throws Exception {
        AppliedJobRequest request = new AppliedJobRequest();
        request.setCandidateId(1L);
        request.setCvUrl("abc");
        request.setJobId(1L);

        when(cvService.getCVByCandidateId(1L)).thenReturn(new CV());
        when(jobService.existsById(1L)).thenReturn(true);
        when(candidateService.existsById(1L)).thenReturn(true);
        Object AppliedJobRequest = request;
        when(modelMapper.map(AppliedJobRequest,AppliedJob.class)).thenReturn(new AppliedJob());
        AppliedJob appliedJob = new AppliedJob();
        appliedJob.setApplied(true);
        appliedJob.setApprovalStatus(Enums.ApprovalStatus.REJECT.getStatus());
        when(appliedJobService.getAppliedJobBefore(1L,1L)).thenReturn(appliedJob);

        candidateManageService.appliedJob(request);

        verify(appliedJobRepository,times(1)).save(any());
    }

    @Test
    void givenAppliedJobIsAppliedReturnFalse_whenCallAppliedJob_thenSaveToDB() throws Exception {
        when(cvService.getCVByCandidateId(1L)).thenReturn(new CV());
        when(jobService.existsById(1L)).thenReturn(true);
        when(candidateService.existsById(1L)).thenReturn(true);
        AppliedJob appliedJob = new AppliedJob();
        appliedJob.setApplied(false);
        appliedJob.setApprovalStatus(Enums.ApprovalStatus.APPROVED.getStatus());
        when(appliedJobService.getAppliedJobBefore(1L,1L)).thenReturn(appliedJob);
        AppliedJobRequest request = new AppliedJobRequest();
        request.setCandidateId(1L);
        request.setCvUrl("abc");
        request.setJobId(1L);

        candidateManageService.appliedJob(request);

        verify(appliedJobRepository,times(1)).save(any());
    }

    @Test
    void givenAppliedJobIsNull_whenCallAppliedJob_thenSaveToDB() throws Exception {
        AppliedJobRequest request = new AppliedJobRequest();
        request.setCandidateId(1L);
        request.setCvUrl("abc");
        request.setJobId(1L);
        when(cvService.getCVByCandidateId(1L)).thenReturn(new CV());
        when(jobService.existsById(1L)).thenReturn(true);
        when(candidateService.existsById(1L)).thenReturn(true);
        AppliedJob appliedJob = new AppliedJob();
        appliedJob.setApplied(false);
        appliedJob.setApprovalStatus(Enums.ApprovalStatus.APPROVED.getStatus());
        when(appliedJobService.getAppliedJobBefore(1L,1L)).thenReturn(null);
        Object AppliedJobRequest = request;
        when(modelMapper.map(AppliedJobRequest,AppliedJob.class)).thenReturn(new AppliedJob());

        candidateManageService.appliedJob(request);

        verify(appliedJobRepository,times(1)).save(any());
    }

    //JobController line 167
}