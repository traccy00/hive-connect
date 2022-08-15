package fpt.edu.capstone.service.impl;

import com.amazonaws.services.importexport.model.UpdateJobResult;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.job.CreateJobRequest;
import fpt.edu.capstone.dto.job.JobResponse;
import fpt.edu.capstone.dto.job.UpdateJobRequest;
import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.repository.JobRepository;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobServiceImplTest {

	@InjectMocks
	JobServiceImpl jobService;

	@Mock
	CompanyServiceImpl companyService;

	@Mock
	RecruiterServiceImpl recruiterService;

	@Mock
	FieldsServiceImpl fieldsService;

	@Mock
	JobRepository jobRepository;

	@Mock
	ModelMapper modelMapper;

	@BeforeEach
	public void init(){
		MockitoAnnotations.openMocks(this);
	}

	// JobController.java line 56

	@Test
	void givenCreateRequestIdNull_whenCallCreateJob_thenCompanyServiceFindByIdAreNotCalled(){
		Exception exception = assertThrows(Exception.class, () -> jobService.createJob(null));
		verify(companyService,times(0)).findById(1L);
	}

	@Test
	void givenCompanyIdIsNotFound_whenCallCreateJob_thenThrowErrorMessage() {
		when(companyService.findById(1L)).thenReturn(Optional.empty());

		CreateJobRequest createJobRequest = new CreateJobRequest();
		createJobRequest.setCompanyId(1L);

		Exception exception = assertThrows(Exception.class, () -> {
			jobService.createJob(createJobRequest);
		});

		assertEquals("Công ty của nhà tuyển dụng không tồn tại.",exception.getMessage());
	}

	@Test
	void givenRecruiterIdIsNotFound_whenCallCreateJob_thenThrowErrorMessage() {
		when(companyService.findById(1L)).thenReturn(Optional.of(new Company()));
		when(recruiterService.existById(1L)).thenReturn(false);

		CreateJobRequest createJobRequest = new CreateJobRequest();
		createJobRequest.setCompanyId(1L);
		createJobRequest.setRecruiterId(1L);

		Exception exception = assertThrows(Exception.class, () -> {
			jobService.createJob(createJobRequest);
		});

		assertEquals("Người dùng không tồn tại",exception.getMessage());
	}

	@Test
	void givenFieldIdIsNotFound_whenCallCreateJob_thenThrowErrorMessage() {
		when(companyService.findById(1L)).thenReturn(Optional.of(new Company()));
		when(recruiterService.existById(1L)).thenReturn(true);
		when(fieldsService.existById(1L)).thenReturn(false);

		CreateJobRequest createJobRequest = new CreateJobRequest();
		createJobRequest.setCompanyId(1L);
		createJobRequest.setRecruiterId(1L);
		createJobRequest.setFieldId(1L);

		Exception exception = assertThrows(Exception.class, () -> {
			jobService.createJob(createJobRequest);
		});

		assertEquals("Lĩnh vực kinh doanh không tồn tại.",exception.getMessage());
	}

	@Test
	void givenModelMapperMapReturnNull_whenCallCreateJob_thenJobRepositorySaveArrNotCalled(){
		when(companyService.findById(1L)).thenReturn(Optional.of(new Company()));
		when(recruiterService.existById(1L)).thenReturn(true);
		when(fieldsService.existById(1L)).thenReturn(true);

		CreateJobRequest request = new CreateJobRequest();
		request.setCompanyId(1L);
		request.setRecruiterId(1L);
		request.setFieldId(1L);
		Object CreateJobRequest = request;
		Job job = new Job();

		when(modelMapper.map(CreateJobRequest, Job.class)).thenReturn(null);

		Exception exception = assertThrows(Exception.class,() -> jobService.createJob(request));

		verify(jobRepository,times(0)).save(job);
	}

	@Test
	void givenCreateJobRequest_whenCallCreateJob_thenCallJobRepositorySave(){
		when(companyService.findById(1L)).thenReturn(Optional.of(new Company()));
		when(recruiterService.existById(1L)).thenReturn(true);
		when(fieldsService.existById(1L)).thenReturn(true);
		when(modelMapper.map(ArgumentMatchers.any(), any())).thenReturn(new Job());

		CreateJobRequest request = new CreateJobRequest();
		request.setCompanyId(1L);
		request.setRecruiterId(1L);
		request.setFieldId(1L);
		Object CreateJobRequest = request;

		when(modelMapper.map(CreateJobRequest, Job.class)).thenReturn(new Job());

		jobService.createJob(request);

		verify(jobRepository,times(1)).save(ArgumentMatchers.any());
	}

	// JobController.java line 56

	// JobController.java line 90

	@Test
	void givenAllParamsAreCorrect_whenCallSearchListJobFilter_thenReturnResponseDataPaginationDataSizeIs2() {
		Job job1 = new Job();
		job1.setId(1L);
		job1.setCompanyId(1L);
		Job job2 = new Job();
		job2.setId(2L);
		job2.setCompanyId(2L);
		List<Job> jobList = Arrays.asList(job1,job2);

		when(jobRepository.searchListJobFilter(any(Pageable.class),eq(1L),eq(1L),
						eq("do some thing"),eq("abc"),eq("abc")))
						.thenReturn(new PageImpl<>(jobList));

		JobResponse jobResponse1 = new JobResponse();
		JobResponse jobResponse2 = new JobResponse();

		when(modelMapper.map(job1, JobResponse.class)).thenReturn(jobResponse1);
		when(modelMapper.map(job2, JobResponse.class)).thenReturn(jobResponse2);

		Company company1 = new Company();
		company1.setName("a");
		Company company2 = new Company();
		company2.setName("b");

		when(companyService.getCompanyById(1L)).thenReturn(company1);
		when(companyService.getCompanyById(2L)).thenReturn(company2);

		 ResponseDataPagination responseDataPagination = jobService.searchListJobFilter(1,1,1L,
						1L,"do some thing","abc","abc");
		List<JobResponse> data = (List<JobResponse>) responseDataPagination.getData();

		assertEquals(2,data.size());
	}

	@Test
	void givenAllParamsAreCorrect_whenCallSearchListJobFilter_thenReturnResponseDataPaginationStatusIsSuccess() {
		Job job1 = new Job();
		job1.setId(1L);
		job1.setCompanyId(1L);
		Job job2 = new Job();
		job2.setId(2L);
		job2.setCompanyId(2L);
		List<Job> jobList = Arrays.asList(job1,job2);

		when(jobRepository.searchListJobFilter(any(Pageable.class),eq(1L),eq(1L),
						eq("do some thing"),eq("abc"),eq("abc")))
						.thenReturn(new PageImpl<>(jobList));

		JobResponse jobResponse1 = new JobResponse();
		JobResponse jobResponse2 = new JobResponse();


		when(modelMapper.map(job1, JobResponse.class)).thenReturn(jobResponse1);
		when(modelMapper.map(job2, JobResponse.class)).thenReturn(jobResponse2);

		Company company1 = new Company();
		company1.setName("a");
		Company company2 = new Company();
		company2.setName("b");

		when(companyService.getCompanyById(1L)).thenReturn(company1);
		when(companyService.getCompanyById(2L)).thenReturn(company2);

		ResponseDataPagination responseDataPagination = jobService.searchListJobFilter(1,10,1L,
						1L,"do some thing","abc","abc");

		assertEquals("Success",responseDataPagination.getStatus());
	}

	@Test
	void givenAllParamsAreCorrect_whenCallSearchListJobFilter_thenReturnResponseDataPaginationPaginationGetCurrentPage() {
		Job job1 = new Job();
		job1.setId(1L);
		job1.setCompanyId(1L);
		Job job2 = new Job();
		job2.setId(2L);
		job2.setCompanyId(2L);
		List<Job> jobList = Arrays.asList(job1,job2);

		when(jobRepository.searchListJobFilter(any(Pageable.class),eq(1L),eq(1L),
						eq("do some thing"),eq("abc"),eq("abc")))
						.thenReturn(new PageImpl<>(jobList));

		JobResponse jobResponse1 = new JobResponse();
		JobResponse jobResponse2 = new JobResponse();


		when(modelMapper.map(job1, JobResponse.class)).thenReturn(jobResponse1);
		when(modelMapper.map(job2, JobResponse.class)).thenReturn(jobResponse2);

		Company company1 = new Company();
		company1.setName("a");
		Company company2 = new Company();
		company2.setName("b");

		when(companyService.getCompanyById(1L)).thenReturn(company1);
		when(companyService.getCompanyById(2L)).thenReturn(company2);

		ResponseDataPagination responseDataPagination = jobService.searchListJobFilter(1,10,1L,
						1L,"do some thing","abc","abc");

		Pagination pagination = (Pagination) responseDataPagination.getPagination();

		assertEquals(1L,pagination.getCurrentPage());
	}

	@Test
	void givenAllParamsAreCorrect_whenCallSearchListJobFilter_thenReturnResponseDataGetPageSize() {
		Job job1 = new Job();
		job1.setId(1L);
		job1.setCompanyId(1L);
		Job job2 = new Job();
		job2.setId(2L);
		job2.setCompanyId(2L);
		List<Job> jobList = Arrays.asList(job1,job2);

		when(jobRepository.searchListJobFilter(any(Pageable.class),eq(1L),eq(1L),
						eq("do some thing"),eq("abc"),eq("abc")))
						.thenReturn(new PageImpl<>(jobList));

		JobResponse jobResponse1 = new JobResponse();
		JobResponse jobResponse2 = new JobResponse();


		when(modelMapper.map(job1, JobResponse.class)).thenReturn(jobResponse1);
		when(modelMapper.map(job2, JobResponse.class)).thenReturn(jobResponse2);

		Company company1 = new Company();
		company1.setName("a");
		Company company2 = new Company();
		company2.setName("b");

		when(companyService.getCompanyById(1L)).thenReturn(company1);
		when(companyService.getCompanyById(2L)).thenReturn(company2);

		ResponseDataPagination responseDataPagination = jobService.searchListJobFilter(1,1,1L,
						1L,"do some thing","abc","abc");

		Pagination pagination = (Pagination) responseDataPagination.getPagination();

		assertEquals(1L,pagination.getPageSize());
	}

	@Test
	void givenAllParamsAreCorrect_whenCallSearchListJobFilter_thenReturnResponseDataGetTotalPage() {
		Job job1 = new Job();
		job1.setId(1L);
		job1.setCompanyId(1L);
		Job job2 = new Job();
		job2.setId(2L);
		job2.setCompanyId(2L);
		List<Job> jobList = Arrays.asList(job1,job2);

		when(jobRepository.searchListJobFilter(any(Pageable.class),eq(1L),eq(1L),
						eq("do some thing"),eq("abc"),eq("abc")))
						.thenReturn(new PageImpl<>(jobList));

		JobResponse jobResponse1 = new JobResponse();
		JobResponse jobResponse2 = new JobResponse();


		when(modelMapper.map(job1, JobResponse.class)).thenReturn(jobResponse1);
		when(modelMapper.map(job2, JobResponse.class)).thenReturn(jobResponse2);

		Company company1 = new Company();
		company1.setName("a");
		Company company2 = new Company();
		company2.setName("b");

		when(companyService.getCompanyById(1L)).thenReturn(company1);
		when(companyService.getCompanyById(2L)).thenReturn(company2);

		ResponseDataPagination responseDataPagination = jobService.searchListJobFilter(1,1,1L,
						1L,"do some thing","abc","abc");

		Pagination pagination = (Pagination) responseDataPagination.getPagination();

		assertEquals(1L,pagination.getTotalPage());
	}

	@Test
	void givenAllParamsAreCorrect_whenCallSearchListJobFilter_thenReturnResponseDataPaginationPaginationGetTotalRecords() {
		Job job1 = new Job();
		job1.setId(1L);
		job1.setCompanyId(1L);
		Job job2 = new Job();
		job2.setId(2L);
		job2.setCompanyId(2L);
		List<Job> jobList = Arrays.asList(job1,job2);

		when(jobRepository.searchListJobFilter(any(Pageable.class),eq(1L),eq(1L),
						eq("do some thing"),eq("abc"),eq("abc")))
						.thenReturn(new PageImpl<>(jobList));

		JobResponse jobResponse1 = new JobResponse();
		JobResponse jobResponse2 = new JobResponse();


		when(modelMapper.map(job1, JobResponse.class)).thenReturn(jobResponse1);
		when(modelMapper.map(job2, JobResponse.class)).thenReturn(jobResponse2);

		Company company1 = new Company();
		company1.setName("a");
		Company company2 = new Company();
		company2.setName("b");

		when(companyService.getCompanyById(1L)).thenReturn(company1);
		when(companyService.getCompanyById(2L)).thenReturn(company2);

		ResponseDataPagination responseDataPagination = jobService.searchListJobFilter(1,1,1L,
						1L,"do some thing","abc","abc");

		Pagination pagination = (Pagination) responseDataPagination.getPagination();

		assertEquals(2L,pagination.getTotalRecords());
	}

	@Test
	void givenPageSizeIsNull_whenCallSearchListJobFilter_thenAssertJobRepositorySearchListJobFilterIsNotCalled() {
		Exception exception = assertThrows(Exception.class, () -> {
			jobService.searchListJobFilter(1, null, 1L,
							1L, "do some thing",  "abc", "abc");
		});
		// if app crash when PageSize is null jobRepository.searchListJobFilter are not called
		verify(jobRepository,times(0)).searchListJobFilter(any(Pageable.class),eq(1L),eq(1L),
						eq("do some thing"),eq("abc"),eq("abc"));
	}

	@Test
	void givenPageNoIsNull_whenCallSearchListJobFilter_thenAssertJobRepositorySearchListJobFilterIsNotCalled() {
		Exception exception = assertThrows(Exception.class, () -> {
			jobService.searchListJobFilter(null, 1, 1L,
							1L, "do some thing",  "abc", "abc");
		});
	// if app crash when PageNo is null jobRepository.searchListJobFilter are not called
		verify(jobRepository,times(0)).searchListJobFilter(any(Pageable.class),eq(1L),eq(1L),
						eq("do some thing"),eq("abc"),eq("abc"));
	}

	@Test
	void givenJobServiceSearchListJobFilterIsEmpty_whenCallSearchListJobFilter_thenReturnResponseDataPaginationDataCurrentPage() {

		when(jobRepository.searchListJobFilter(any(),eq(1L),eq(1L),
						eq("do some thing"),eq("abc"),eq("abc")))
						.thenReturn(Page.empty());
		ResponseDataPagination responseDataPagination = jobService.searchListJobFilter(1, 1, 1L,
							1L, "do some thing", "abc", "abc");

		Pagination pagination = (Pagination) responseDataPagination.getPagination();

		assertEquals(1L,pagination.getCurrentPage());
	}

	@Test
	void givenJobServiceSearchListJobFilterIsEmpty_whenCallSearchListJobFilter_thenReturnResponseDataPaginationDataGetPageSize() {

		when(jobRepository.searchListJobFilter(any(),eq(1L),eq(1L),
						eq("do some thing"),eq("abc"),eq("abc")))
						.thenReturn(Page.empty());
		ResponseDataPagination responseDataPagination = jobService.searchListJobFilter(1, 1, 1L,
						1L, "do some thing", "abc", "abc");

		Pagination pagination = (Pagination) responseDataPagination.getPagination();

		assertEquals(1L,pagination.getPageSize());
	}

	@Test
	void givenJobServiceSearchListJobFilterIsEmpty_whenCallSearchListJobFilter_thenReturnResponseDataPaginationDataGetTotalPage() {

		when(jobRepository.searchListJobFilter(any(),eq(1L),eq(1L),
						eq("do some thing"),eq("abc"),eq("abc")))
						.thenReturn(Page.empty());
		ResponseDataPagination responseDataPagination = jobService.searchListJobFilter(1, 1, 1L,
						1L, "do some thing",  "abc", "abc");

		Pagination pagination = (Pagination) responseDataPagination.getPagination();

		assertEquals(1L,pagination.getTotalPage());
	}

	@Test
	void givenJobServiceSearchListJobFilterIsEmpty_whenCallSearchListJobFilter_thenReturnResponseDataPaginationDataGetTotalRecords() {

		when(jobRepository.searchListJobFilter(any(),eq(1L),eq(1L),
						eq("do some thing"),eq("abc"),eq("abc")))
						.thenReturn(Page.empty());
		ResponseDataPagination responseDataPagination = jobService.searchListJobFilter(1, 1, 1L,
						1L, "do some thing",  "abc", "abc");

		Pagination pagination = (Pagination) responseDataPagination.getPagination();

		assertEquals(0,pagination.getTotalRecords());
	}

	@Test
	void givenJobServiceSearchListJobFilterIsEmpty_whenCallSearchListJobFilter_thenReturn1() {

		when(jobRepository.searchListJobFilter(any(),eq(1L),eq(1L),
						eq("do some thing"),eq("abc"),eq("abc")))
						.thenReturn(Page.empty());
		ResponseDataPagination responseDataPagination = jobService.searchListJobFilter(1, 1, 1L,
						1L, "do some thing", "abc", "abc");

		assertEquals("Success",responseDataPagination.getStatus());
	}

	@Test
	void givenJobServiceSearchListJobFilterIsEmpty_whenCallSearchListJobFilter_thenReturn2() {

		when(jobRepository.searchListJobFilter(any(),eq(1L),eq(1L),
						eq("do some thing"),eq("abc"),eq("abc")))
						.thenReturn(Page.empty());
		ResponseDataPagination responseDataPagination = jobService.searchListJobFilter(1, 1, 1L,
						1L, "do some thing", "abc", "abc");

		List<JobResponse> data = (List<JobResponse>) responseDataPagination.getData();

		assertEquals(0,data.size());
	}

	// JobController.java line 90

	//JobController.java line 128

	@Test
	void givenUpdateJobRequestIsNull_whenCallUpdateJob_thenModelMapperMapAreNotCalled() {
		Exception exception = assertThrows(Exception.class, () -> {
			jobService.updateJob(null);
		});
		verify(modelMapper,times(0)).map(any(),any());
	}

	@Test
	void givenRequestIdIsNull_whenCallUpdateJob_thenModelMapperMapAreNotCalled() {
		UpdateJobRequest updateJobRequest = new UpdateJobRequest();
		Exception exception = assertThrows(Exception.class, () -> {
			jobService.updateJob(updateJobRequest);
		});
		verify(modelMapper,times(0)).map(any(),any());
	}

	@Test
	void givenJobRepositoryGetById_whenCallUpdateJob_thenThrowException() {
		UpdateJobRequest updateJobRequest = new UpdateJobRequest();
		Exception exception = assertThrows(Exception.class, () -> {
			jobService.updateJob(updateJobRequest);
		});
		assertEquals("Tin tuyển dụng không tồn tại",exception.getMessage());
	}

	@Test
	void givenFlagIsPosted_whenCallUpdateJob_thenModelMapperMapAreNotCalled() {
		UpdateJobRequest updateJobRequest = new UpdateJobRequest();
		Job job = new Job();
		job.setFlag("Posted");
		when(jobRepository.getById(1L)).thenReturn(job);
		Exception exception = assertThrows(Exception.class, () -> {
			jobService.updateJob(updateJobRequest);
		});
		verify(modelMapper,times(0)).map(any(),any());
	}

	@Test
	void givenParamIsCorrect_whenCallUpdateJob_thenModelMapperMapAreNotCalled() {
		UpdateJobRequest updateJobRequest = new UpdateJobRequest();
		updateJobRequest.setJobId(1L);
		updateJobRequest.setFlag("Draft");
		Job job = new Job();
		job.setFlag("Draft");
		when(jobRepository.getById(1L)).thenReturn(job);
		when(modelMapper.map(any(),any())).thenReturn(job);
		jobService.updateJob(updateJobRequest);

		verify(jobRepository,times(1)).save(job);
	}

	// JobController.java line 90

	// JobController.java line 169

	@Test
	void givenRepositoryGetByIdReturnNUll_whenCallGetJobById_thenThrowException(){
		when(jobRepository.getById(1L)).thenReturn(null);
		Exception exception = assertThrows(Exception.class,() -> jobService.getJobById(1L));
		assertEquals(ResponseMessageConstants.JOB_DOES_NOT_EXIST,exception.getMessage());
	}

	@Test
	void givenRepositoryGetByIdReturnTrueValue_whenCallGetJobById_thenReturnJob(){
		Job job = new Job();
		job.setId(1L);
		when(jobRepository.getById(1L)).thenReturn(job);
		Job result = jobService.getJobById(1L);
		assertEquals(1L,result.getId());
	}

	@Test
	void givenRepositoryGetByIdReturnFalseValue_whenCallGetJobById_thenFalseJob(){
		Job job = new Job();
		job.setId(2L);
		when(jobRepository.getById(1L)).thenReturn(job);
		Job result = jobService.getJobById(1L);
		assertNotEquals(1L,result.getId());
	}

	@Test
	void givenRepositoryGetByIdReturnJobIdEquals0_whenCallGetJobById_thenThrowException(){
		Job job = new Job();
		job.setId(0);
		when(jobRepository.getById(1L)).thenReturn(job);
		Exception exception = assertThrows(Exception.class,() -> jobService.getJobById(1L));
		assertEquals(ResponseMessageConstants.JOB_DOES_NOT_EXIST,exception.getMessage());
	}
	// JobController.java line 169
}