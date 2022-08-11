package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.CV.CVRequest;
import fpt.edu.capstone.dto.CV.CVResponse;
import fpt.edu.capstone.dto.job.AppliedJobRequest;
import fpt.edu.capstone.entity.*;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.AppliedJobRepository;
import fpt.edu.capstone.repository.CVRepository;
import fpt.edu.capstone.repository.EducationRepository;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.ResponseDataPagination;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CandidateManageServiceImplTest {

    @Mock
    private ModelMapper mockModelMapper;
    @Mock
    private AppliedJobService mockAppliedJobService;
    @Mock
    private CandidateService mockCandidateService;
    @Mock
    private JobService mockJobService;
    @Mock
    private CompanyService mockCompanyService;
    @Mock
    private JobHashTagService mockJobHashTagService;
    @Mock
    private CVService mockCvService;
    @Mock
    private WorkExperienceService mockWorkExperienceService;
    @Mock
    private MajorLevelService mockMajorLevelService;
    @Mock
    private OtherSkillService mockOtherSkillService;
    @Mock
    private LanguageService mockLanguageService;
    @Mock
    private CertificateService mockCertificateService;
    @Mock
    private EducationService mockEducationService;
    @Mock
    private ProfileViewerService mockProfileViewerService;
    @Mock
    private AppliedJobRepository mockAppliedJobRepository;
    @Mock
    private ImageService mockImageService;
    @Mock
    private CVRepository mockCvRepository;
    @Mock
    private EducationRepository mockEducationRepository;

    private CandidateManageServiceImpl candidateManageServiceImplUnderTest;

    private Job job(){
        Job job = new Job(0L, 0L, "jobName", "workPlace", "workForm", LocalDateTime.now(),
                LocalDateTime.now(), 0L, 0L, 0L, "rank", "experience", false, "jobDescription",
                "jobRequirement", "benefit", 0L, 0, false, false, false, 0L, "weekday", 0L, "Posted");
        return job;
    }
    @Before
    public void setUp() throws Exception {
        candidateManageServiceImplUnderTest = new CandidateManageServiceImpl(mockModelMapper, mockAppliedJobService,
                mockCandidateService, mockJobService, mockCompanyService, mockJobHashTagService, mockCvService,
                mockWorkExperienceService, mockMajorLevelService, mockOtherSkillService, mockLanguageService,
                mockCertificateService, mockEducationService, mockProfileViewerService, mockAppliedJobRepository,
                mockImageService, mockCvRepository, mockEducationRepository);
    }

    private AppliedJob appliedJob(){
        AppliedJob appliedJob = new AppliedJob(0L, 0L, 0L, false, "approvalStatus", false, "cvUploadUrl");
        return appliedJob;
    }
    @Test
    public void testSearchAppliedJobsOfCandidate() {
        when(mockCandidateService.existsById(0L)).thenReturn(false);
        final Page<AppliedJob> appliedJobs = new PageImpl<>(
                Arrays.asList(appliedJob()));
        when(mockAppliedJobService.searchAppliedJobsOfCandidate(any(Pageable.class), eq(0L),
                eq("approvalStatus"))).thenReturn(appliedJobs);
        final Job job = job();
        when(mockJobService.getJobById(0L)).thenReturn(job);
        final ResponseDataPagination result = candidateManageServiceImplUnderTest.searchAppliedJobsOfCandidate(1, 10, 0L,
                "approvalStatus");
    }

    @Test
    public void testSearchAppliedJobsOfCandidate_AppliedJobServiceReturnsNoItems() {
        when(mockCandidateService.existsById(0L)).thenReturn(false);
        when(mockAppliedJobService.searchAppliedJobsOfCandidate(any(Pageable.class), eq(0L),
                eq("approvalStatus"))).thenReturn(new PageImpl<>(Collections.emptyList()));
        final Job job = job();
        when(mockJobService.getJobById(0L)).thenReturn(job);
        final ResponseDataPagination result = candidateManageServiceImplUnderTest.searchAppliedJobsOfCandidate(0, 0, 0L,
                "approvalStatus");
    }

    private Company company(){
        Company company = new Company();
        company.setId(0L);
        company.setFieldWork("fieldWork");
        company.setName("name");
        company.setEmail("companyEmail");
        company.setPhone("companyPhone");
        company.setDescription("companyDescription");
        company.setWebsite("companyWebsite");
        company.setNumberEmployees("numberEmployees");
        company.setAddress("address");
        company.setTaxCode("taxCode");
        company.setIsDeleted(0);
        company.setMapUrl("mapUrl");
        company.setCreatorId(0L);
        company.setLocked(false);
        return company;
    }
    @Test
    public void testGetJobsOfCompany() {
        final Page<Job> jobs = new PageImpl<>(Arrays.asList(job()));
        when(mockJobService.getJobByCompanyId(0, 0, 0L)).thenReturn(jobs);
        final List<JobHashtag> hashtagList = Arrays.asList(new JobHashtag(0L, 0L, 0L, "hashTagName", "status"));
        when(mockJobHashTagService.getHashTagOfJob(0L)).thenReturn(hashtagList);
        final Company company = company();
        when(mockCompanyService.getCompanyById(0L)).thenReturn(company);
        final ResponseDataPagination result = candidateManageServiceImplUnderTest.getJobsOfCompany(0, 0, 0L);
    }

    @Test
    public void testGetJobsOfCompany_JobServiceReturnsNoItems() {
        when(mockJobService.getJobByCompanyId(0, 0, 0L)).thenReturn(new PageImpl<>(Collections.emptyList()));
        final List<JobHashtag> hashtagList = Arrays.asList(new JobHashtag(0L, 0L, 0L, "hashTagName", "status"));
        when(mockJobHashTagService.getHashTagOfJob(0L)).thenReturn(hashtagList);
        final Company company = company();
        when(mockCompanyService.getCompanyById(0L)).thenReturn(company);
        final ResponseDataPagination result = candidateManageServiceImplUnderTest.getJobsOfCompany(0, 0, 0L);
    }

    @Test
    public void testGetJobsOfCompany_JobHashTagServiceReturnsNull() {
        final Page<Job> jobs = new PageImpl<>(Arrays.asList(job()));
        when(mockJobService.getJobByCompanyId(0, 0, 0L)).thenReturn(jobs);

        when(mockJobHashTagService.getHashTagOfJob(0L)).thenReturn(null);
        final Company company = company();
        when(mockCompanyService.getCompanyById(0L)).thenReturn(company);
        final ResponseDataPagination result = candidateManageServiceImplUnderTest.getJobsOfCompany(0, 0, 0L);
    }

    @Test
    public void testGetJobsOfCompany_JobHashTagServiceReturnsNoItems() {
        final Page<Job> jobs = new PageImpl<>(Arrays.asList(job()));
        when(mockJobService.getJobByCompanyId(0, 0, 0L)).thenReturn(jobs);
        when(mockJobHashTagService.getHashTagOfJob(0L)).thenReturn(Collections.emptyList());
        final Company company = company();
        when(mockCompanyService.getCompanyById(0L)).thenReturn(company);
        final ResponseDataPagination result = candidateManageServiceImplUnderTest.getJobsOfCompany(0, 0, 0L);
    }

    @Test
    public void testGetJobsOfCompany_CompanyServiceReturnsNull() {
        final Page<Job> jobs = new PageImpl<>(Arrays.asList(job()));
        when(mockJobService.getJobByCompanyId(0, 0, 0L)).thenReturn(jobs);
        final List<JobHashtag> hashtagList = Arrays.asList(new JobHashtag(0L, 0L, 0L, "hashTagName", "status"));
        when(mockJobHashTagService.getHashTagOfJob(0L)).thenReturn(hashtagList);
        when(mockCompanyService.getCompanyById(0L)).thenReturn(null);
        final ResponseDataPagination result = candidateManageServiceImplUnderTest.getJobsOfCompany(0, 0, 0L);
    }

    @Test
    public void testFindCvByCandidateId() {
        final CVResponse expectedResult = new CVResponse(0L, 0L, 0L, "summary", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                Arrays.asList(new Certificate(0L, "certificateName", "certificateUrl", 0L, 0L)), Arrays.asList(
                new Education(0L, 0L, "school", "major", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), false, "description")),
                Arrays.asList(new Language(0L, "language", "level", 0L)),
                Arrays.asList(new MajorLevel(0L, 0L, 0L, 0L, "level", false)),
                Arrays.asList(new OtherSkill(0L, "skillName", 0L, "level")), Arrays.asList(
                new WorkExperience(0L, 0L, "companyName", "position", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), "description", false)));
        final List<CV> cvList = Arrays.asList(new CV(0L, 0L, 0L, "summary", "totalExperienceYear"));
        when(mockCvService.findCvByCandidateId(0L)).thenReturn(cvList);
        final List<Certificate> certificates = Arrays.asList(
                new Certificate(0L, "certificateName", "certificateUrl", 0L, 0L));
        when(mockCertificateService.getListCertificateByCvId(0L)).thenReturn(certificates);
        final List<Education> educationList = Arrays.asList(
                new Education(0L, 0L, "school", "major", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), false, "description"));
        when(mockEducationService.getListEducationByCvId(0L)).thenReturn(educationList);
        final List<Language> list = Arrays.asList(new Language(0L, "language", "level", 0L));
        when(mockLanguageService.getListLanguageByCvId(0L)).thenReturn(list);
        final List<MajorLevel> majorLevels = Arrays.asList(new MajorLevel(0L, 0L, 0L, 0L, "level", false));
        when(mockMajorLevelService.getListMajorLevelByCvId(0L)).thenReturn(majorLevels);
        final List<OtherSkill> otherSkills = Arrays.asList(new OtherSkill(0L, "skillName", 0L, "level"));
        when(mockOtherSkillService.getListOtherSkillByCvId(0L)).thenReturn(otherSkills);
        final List<WorkExperience> workExperiences = Arrays.asList(
                new WorkExperience(0L, 0L, "companyName", "position", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), "description", false));
        when(mockWorkExperienceService.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);
        final CVResponse result = candidateManageServiceImplUnderTest.findCvByCandidateId(0L);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFindCvByCandidateId_CVServiceReturnsNull() {
        when(mockCvService.findCvByCandidateId(0L)).thenReturn(null);
        assertThatThrownBy(() -> candidateManageServiceImplUnderTest.findCvByCandidateId(0L))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testFindCvByCandidateId_CVServiceReturnsNoItems() {
        when(mockCvService.findCvByCandidateId(0L)).thenReturn(Collections.emptyList());
                assertThatThrownBy(() -> candidateManageServiceImplUnderTest.findCvByCandidateId(0L))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testFindCvByCandidateId_CertificateServiceReturnsNoItems() {
        final CVResponse expectedResult = new CVResponse(0L, 0L, 0L, "summary", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                Arrays.asList(new Certificate(0L, "certificateName", "certificateUrl", 0L, 0L)), Arrays.asList(
                new Education(0L, 0L, "school", "major", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), false, "description")),
                Arrays.asList(new Language(0L, "language", "level", 0L)),
                Arrays.asList(new MajorLevel(0L, 0L, 0L, 0L, "level", false)),
                Arrays.asList(new OtherSkill(0L, "skillName", 0L, "level")), Arrays.asList(
                new WorkExperience(0L, 0L, "companyName", "position", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), "description", false)));
        final List<CV> cvList = Arrays.asList(new CV(0L, 0L, 0L, "summary", "totalExperienceYear"));
        when(mockCvService.findCvByCandidateId(0L)).thenReturn(cvList);
        when(mockCertificateService.getListCertificateByCvId(0L)).thenReturn(Collections.emptyList());
        final List<Education> educationList = Arrays.asList(
                new Education(0L, 0L, "school", "major", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), false, "description"));
        when(mockEducationService.getListEducationByCvId(0L)).thenReturn(educationList);
        final List<Language> list = Arrays.asList(new Language(0L, "language", "level", 0L));
        when(mockLanguageService.getListLanguageByCvId(0L)).thenReturn(list);
        final List<MajorLevel> majorLevels = Arrays.asList(new MajorLevel(0L, 0L, 0L, 0L, "level", false));
        when(mockMajorLevelService.getListMajorLevelByCvId(0L)).thenReturn(majorLevels);
        final List<OtherSkill> otherSkills = Arrays.asList(new OtherSkill(0L, "skillName", 0L, "level"));
        when(mockOtherSkillService.getListOtherSkillByCvId(0L)).thenReturn(otherSkills);
        final List<WorkExperience> workExperiences = Arrays.asList(
                new WorkExperience(0L, 0L, "companyName", "position", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), "description", false));
        when(mockWorkExperienceService.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);
        final CVResponse result = candidateManageServiceImplUnderTest.findCvByCandidateId(0L);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFindCvByCandidateId_EducationServiceReturnsNoItems() {
        final CVResponse expectedResult = new CVResponse(0L, 0L, 0L, "summary", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                Arrays.asList(new Certificate(0L, "certificateName", "certificateUrl", 0L, 0L)), Arrays.asList(
                new Education(0L, 0L, "school", "major", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), false, "description")),
                Arrays.asList(new Language(0L, "language", "level", 0L)),
                Arrays.asList(new MajorLevel(0L, 0L, 0L, 0L, "level", false)),
                Arrays.asList(new OtherSkill(0L, "skillName", 0L, "level")), Arrays.asList(
                new WorkExperience(0L, 0L, "companyName", "position", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), "description", false)));
        final List<CV> cvList = Arrays.asList(new CV(0L, 0L, 0L, "summary", "totalExperienceYear"));
        when(mockCvService.findCvByCandidateId(0L)).thenReturn(cvList);
        final List<Certificate> certificates = Arrays.asList(
                new Certificate(0L, "certificateName", "certificateUrl", 0L, 0L));
        when(mockCertificateService.getListCertificateByCvId(0L)).thenReturn(certificates);

        when(mockEducationService.getListEducationByCvId(0L)).thenReturn(Collections.emptyList());
        final List<Language> list = Arrays.asList(new Language(0L, "language", "level", 0L));
        when(mockLanguageService.getListLanguageByCvId(0L)).thenReturn(list);
        final List<MajorLevel> majorLevels = Arrays.asList(new MajorLevel(0L, 0L, 0L, 0L, "level", false));
        when(mockMajorLevelService.getListMajorLevelByCvId(0L)).thenReturn(majorLevels);
        final List<OtherSkill> otherSkills = Arrays.asList(new OtherSkill(0L, "skillName", 0L, "level"));
        when(mockOtherSkillService.getListOtherSkillByCvId(0L)).thenReturn(otherSkills);
        final List<WorkExperience> workExperiences = Arrays.asList(
                new WorkExperience(0L, 0L, "companyName", "position", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), "description", false));
        when(mockWorkExperienceService.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);
        final CVResponse result = candidateManageServiceImplUnderTest.findCvByCandidateId(0L);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFindCvByCandidateId_LanguageServiceReturnsNoItems() {
        final CVResponse expectedResult = new CVResponse(0L, 0L, 0L, "summary", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                Arrays.asList(new Certificate(0L, "certificateName", "certificateUrl", 0L, 0L)), Arrays.asList(
                new Education(0L, 0L, "school", "major", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), false, "description")),
                Arrays.asList(new Language(0L, "language", "level", 0L)),
                Arrays.asList(new MajorLevel(0L, 0L, 0L, 0L, "level", false)),
                Arrays.asList(new OtherSkill(0L, "skillName", 0L, "level")), Arrays.asList(
                new WorkExperience(0L, 0L, "companyName", "position", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), "description", false)));

        final List<CV> cvList = Arrays.asList(new CV(0L, 0L, 0L, "summary", "totalExperienceYear"));
        when(mockCvService.findCvByCandidateId(0L)).thenReturn(cvList);

        final List<Certificate> certificates = Arrays.asList(
                new Certificate(0L, "certificateName", "certificateUrl", 0L, 0L));
        when(mockCertificateService.getListCertificateByCvId(0L)).thenReturn(certificates);

        final List<Education> educationList = Arrays.asList(
                new Education(0L, 0L, "school", "major", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), false, "description"));
        when(mockEducationService.getListEducationByCvId(0L)).thenReturn(educationList);

        when(mockLanguageService.getListLanguageByCvId(0L)).thenReturn(Collections.emptyList());

        final List<MajorLevel> majorLevels = Arrays.asList(new MajorLevel(0L, 0L, 0L, 0L, "level", false));
        when(mockMajorLevelService.getListMajorLevelByCvId(0L)).thenReturn(majorLevels);

        final List<OtherSkill> otherSkills = Arrays.asList(new OtherSkill(0L, "skillName", 0L, "level"));
        when(mockOtherSkillService.getListOtherSkillByCvId(0L)).thenReturn(otherSkills);

        final List<WorkExperience> workExperiences = Arrays.asList(
                new WorkExperience(0L, 0L, "companyName", "position", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), "description", false));
        when(mockWorkExperienceService.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);

        final CVResponse result = candidateManageServiceImplUnderTest.findCvByCandidateId(0L);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFindCvByCandidateId_MajorLevelServiceReturnsNoItems() {
        final CVResponse expectedResult = new CVResponse(0L, 0L, 0L, "summary", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                Arrays.asList(new Certificate(0L, "certificateName", "certificateUrl", 0L, 0L)), Arrays.asList(
                new Education(0L, 0L, "school", "major", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), false, "description")),
                Arrays.asList(new Language(0L, "language", "level", 0L)),
                Arrays.asList(new MajorLevel(0L, 0L, 0L, 0L, "level", false)),
                Arrays.asList(new OtherSkill(0L, "skillName", 0L, "level")), Arrays.asList(
                new WorkExperience(0L, 0L, "companyName", "position", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), "description", false)));

        final List<CV> cvList = Arrays.asList(new CV(0L, 0L, 0L, "summary", "totalExperienceYear"));
        when(mockCvService.findCvByCandidateId(0L)).thenReturn(cvList);

        final List<Certificate> certificates = Arrays.asList(
                new Certificate(0L, "certificateName", "certificateUrl", 0L, 0L));
        when(mockCertificateService.getListCertificateByCvId(0L)).thenReturn(certificates);

        final List<Education> educationList = Arrays.asList(
                new Education(0L, 0L, "school", "major", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), false, "description"));
        when(mockEducationService.getListEducationByCvId(0L)).thenReturn(educationList);

        final List<Language> list = Arrays.asList(new Language(0L, "language", "level", 0L));
        when(mockLanguageService.getListLanguageByCvId(0L)).thenReturn(list);

        when(mockMajorLevelService.getListMajorLevelByCvId(0L)).thenReturn(Collections.emptyList());

        final List<OtherSkill> otherSkills = Arrays.asList(new OtherSkill(0L, "skillName", 0L, "level"));
        when(mockOtherSkillService.getListOtherSkillByCvId(0L)).thenReturn(otherSkills);

        final List<WorkExperience> workExperiences = Arrays.asList(
                new WorkExperience(0L, 0L, "companyName", "position", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), "description", false));
        when(mockWorkExperienceService.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);

        final CVResponse result = candidateManageServiceImplUnderTest.findCvByCandidateId(0L);
      assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFindCvByCandidateId_OtherSkillServiceReturnsNoItems() {
        final CVResponse expectedResult = new CVResponse(0L, 0L, 0L, "summary", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                Arrays.asList(new Certificate(0L, "certificateName", "certificateUrl", 0L, 0L)), Arrays.asList(
                new Education(0L, 0L, "school", "major", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), false, "description")),
                Arrays.asList(new Language(0L, "language", "level", 0L)),
                Arrays.asList(new MajorLevel(0L, 0L, 0L, 0L, "level", false)),
                Arrays.asList(new OtherSkill(0L, "skillName", 0L, "level")), Arrays.asList(
                new WorkExperience(0L, 0L, "companyName", "position", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), "description", false)));
        final List<CV> cvList = Arrays.asList(new CV(0L, 0L, 0L, "summary", "totalExperienceYear"));
        when(mockCvService.findCvByCandidateId(0L)).thenReturn(cvList);
        final List<Certificate> certificates = Arrays.asList(
                new Certificate(0L, "certificateName", "certificateUrl", 0L, 0L));
        when(mockCertificateService.getListCertificateByCvId(0L)).thenReturn(certificates);
        final List<Education> educationList = Arrays.asList(
                new Education(0L, 0L, "school", "major", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), false, "description"));
        when(mockEducationService.getListEducationByCvId(0L)).thenReturn(educationList);
        final List<Language> list = Arrays.asList(new Language(0L, "language", "level", 0L));
        when(mockLanguageService.getListLanguageByCvId(0L)).thenReturn(list);
        final List<MajorLevel> majorLevels = Arrays.asList(new MajorLevel(0L, 0L, 0L, 0L, "level", false));
        when(mockMajorLevelService.getListMajorLevelByCvId(0L)).thenReturn(majorLevels);

        when(mockOtherSkillService.getListOtherSkillByCvId(0L)).thenReturn(Collections.emptyList());
        final List<WorkExperience> workExperiences = Arrays.asList(
                new WorkExperience(0L, 0L, "companyName", "position", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), "description", false));
        when(mockWorkExperienceService.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);
        final CVResponse result = candidateManageServiceImplUnderTest.findCvByCandidateId(0L);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFindCvByCandidateId_WorkExperienceServiceReturnsNoItems() {
        final CVResponse expectedResult = new CVResponse(0L, 0L, 0L, "summary", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                Arrays.asList(new Certificate(0L, "certificateName", "certificateUrl", 0L, 0L)), Arrays.asList(
                new Education(0L, 0L, "school", "major", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), false, "description")),
                Arrays.asList(new Language(0L, "language", "level", 0L)),
                Arrays.asList(new MajorLevel(0L, 0L, 0L, 0L, "level", false)),
                Arrays.asList(new OtherSkill(0L, "skillName", 0L, "level")), Arrays.asList(
                new WorkExperience(0L, 0L, "companyName", "position", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), "description", false)));
        final List<CV> cvList = Arrays.asList(new CV(0L, 0L, 0L, "summary", "totalExperienceYear"));
        when(mockCvService.findCvByCandidateId(0L)).thenReturn(cvList);
        final List<Certificate> certificates = Arrays.asList(
                new Certificate(0L, "certificateName", "certificateUrl", 0L, 0L));
        when(mockCertificateService.getListCertificateByCvId(0L)).thenReturn(certificates);
        final List<Education> educationList = Arrays.asList(
                new Education(0L, 0L, "school", "major", LocalDateTime.of(2022, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2022, 1, 1, 0, 0, 0), false, "description"));
        when(mockEducationService.getListEducationByCvId(0L)).thenReturn(educationList);
        final List<Language> list = Arrays.asList(new Language(0L, "language", "level", 0L));
        when(mockLanguageService.getListLanguageByCvId(0L)).thenReturn(list);

        final List<MajorLevel> majorLevels = Arrays.asList(new MajorLevel(0L, 0L, 0L, 0L, "level", false));
        when(mockMajorLevelService.getListMajorLevelByCvId(0L)).thenReturn(majorLevels);

        final List<OtherSkill> otherSkills = Arrays.asList(new OtherSkill(0L, "skillName", 0L, "level"));
        when(mockOtherSkillService.getListOtherSkillByCvId(0L)).thenReturn(otherSkills);

        when(mockWorkExperienceService.getListWorkExperienceByCvId(0L)).thenReturn(Collections.emptyList());
        final CVResponse result = candidateManageServiceImplUnderTest.findCvByCandidateId(0L);

       assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testCreateCV() {
        final CVRequest request = new CVRequest(0L, "summary", "totalExperienceYear");

        final Candidate candidate = new Candidate(0L, 0L, false, LocalDateTime.of(2022, 1, 1, 0, 0, 0), "searchHistory",
                "country", "fullName", "address", "socialLink", "avatarUrl", "wishlistJobIdList", "tapHistoryIdList",
                false, 0L, "introduction");
        when(mockCandidateService.getCandidateById(0L)).thenReturn(candidate);
        final List<CV> cvList = Arrays.asList(new CV(0L, 0L, 0L, "summary", "totalExperienceYear"));
        when(mockCvService.findCvByCandidateId(0L)).thenReturn(cvList);
        final CV cv = new CV(0L, 0L, 0L, "summary", "totalExperienceYear");
        when(mockModelMapper.map(any(Object.class), eq(CV.class))).thenReturn(cv);
        final CV result = candidateManageServiceImplUnderTest.createCV(request);
        verify(mockCvService).save(any(CV.class));
    }

    @Test
    public void testCreateCV_CandidateServiceReturnsNull() {
        final CVRequest request = new CVRequest(0L, "summary", "totalExperienceYear");
        when(mockCandidateService.getCandidateById(0L)).thenReturn(null);
        assertThatThrownBy(() -> candidateManageServiceImplUnderTest.createCV(request))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testCreateCV_CVServiceFindCvByCandidateIdReturnsNoItems() {
        final CVRequest request = new CVRequest(0L, "summary", "totalExperienceYear");
        final Candidate candidate = new Candidate(0L, 0L, false, LocalDateTime.of(2022, 1, 1, 0, 0, 0), "searchHistory",
                "country", "fullName", "address", "socialLink", "avatarUrl", "wishlistJobIdList", "tapHistoryIdList",
                false, 0L, "introduction");
        when(mockCandidateService.getCandidateById(0L)).thenReturn(candidate);
        when(mockCvService.findCvByCandidateId(0L)).thenReturn(Collections.emptyList());
        final CV cv = new CV(0L, 0L, 0L, "summary", "totalExperienceYear");
        when(mockModelMapper.map(any(Object.class), eq(CV.class))).thenReturn(cv);
        final CV result = candidateManageServiceImplUnderTest.createCV(request);
        verify(mockCvService).save(any(CV.class));
    }

    @Test
    public void testGetProfileViewer() {
        final Candidate candidate = new Candidate(0L, 0L, false, LocalDateTime.of(2022, 1, 1, 0, 0, 0), "searchHistory",
                "country", "fullName", "address", "socialLink", "avatarUrl", "wishlistJobIdList", "tapHistoryIdList",
                false, 0L, "introduction");
        when(mockCandidateService.getCandidateById(0L)).thenReturn(candidate);
        final ProfileViewer viewer = new ProfileViewer();
        viewer.setCreatedAt(LocalDateTime.of(2022, 1, 1, 0, 0, 0));
        viewer.setUpdatedAt(LocalDateTime.of(2022, 1, 1, 0, 0, 0));
        viewer.setId(0L);
        viewer.setCvId(0L);
        viewer.setViewerId(0L);
        viewer.setCandidateId(0L);
        final Page<ProfileViewer> viewerPage = new PageImpl<>(Arrays.asList(viewer));
        when(mockProfileViewerService.getProfileViewerOfCv(any(Pageable.class), eq(0L))).thenReturn(viewerPage);
        final ProfileViewer viewer1 = new ProfileViewer();
        viewer1.setCreatedAt(LocalDateTime.of(2022, 1, 1, 0, 0, 0));
        viewer1.setUpdatedAt(LocalDateTime.of(2022, 1, 1, 0, 0, 0));
        viewer1.setId(0L);
        viewer1.setCvId(0L);
        viewer1.setViewerId(0L);
        viewer1.setCandidateId(0L);
        final List<ProfileViewer> profileViewers = Arrays.asList(viewer1);
        when(mockProfileViewerService.findAll(PageRequest.of(0, 1))).thenReturn(profileViewers);
         final ResponseDataPagination result = candidateManageServiceImplUnderTest.getProfileViewer(1, 10, 0L, 0L);

    }

    @Test
    public void testGetProfileViewer_CandidateServiceReturnsNull() {
        when(mockCandidateService.getCandidateById(0L)).thenReturn(null);
        assertThatThrownBy(() -> candidateManageServiceImplUnderTest.getProfileViewer(0, 0, 0L, 0L))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testGetProfileViewer_ProfileViewerServiceGetProfileViewerOfCvReturnsNoItems() {
        final Candidate candidate = new Candidate(0L, 0L, false, LocalDateTime.of(2022, 1, 1, 0, 0, 0), "searchHistory",
                "country", "fullName", "address", "socialLink", "avatarUrl", "wishlistJobIdList", "tapHistoryIdList",
                false, 0L, "introduction");
        when(mockCandidateService.getCandidateById(0L)).thenReturn(candidate);

        when(mockProfileViewerService.getProfileViewerOfCv(any(Pageable.class), eq(0L)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        final ProfileViewer viewer = new ProfileViewer();
        viewer.setCreatedAt(LocalDateTime.of(2022, 1, 1, 0, 0, 0));
        viewer.setUpdatedAt(LocalDateTime.of(2022, 1, 1, 0, 0, 0));
        viewer.setId(0L);
        viewer.setCvId(0L);
        viewer.setViewerId(0L);
        viewer.setCandidateId(0L);
        final List<ProfileViewer> profileViewers = Arrays.asList(viewer);
        when(mockProfileViewerService.findAll(PageRequest.of(0, 1))).thenReturn(profileViewers);
        final ResponseDataPagination result = candidateManageServiceImplUnderTest.getProfileViewer(1, 10, 0L, 0L);
    }

    @Test
    public void testGetProfileViewer_ProfileViewerServiceFindAllReturnsNoItems() {
        final Candidate candidate = new Candidate(0L, 0L, false, LocalDateTime.of(2022, 1, 1, 0, 0, 0), "searchHistory",
                "country", "fullName", "address", "socialLink", "avatarUrl", "wishlistJobIdList", "tapHistoryIdList",
                false, 0L, "introduction");
        when(mockCandidateService.getCandidateById(0L)).thenReturn(candidate);
        final ProfileViewer viewer = new ProfileViewer();
        viewer.setCreatedAt(LocalDateTime.of(2022, 1, 1, 0, 0, 0));
        viewer.setUpdatedAt(LocalDateTime.of(2022, 1, 1, 0, 0, 0));
        viewer.setId(0L);
        viewer.setCvId(0L);
        viewer.setViewerId(0L);
        viewer.setCandidateId(0L);
        final Page<ProfileViewer> viewerPage = new PageImpl<>(Arrays.asList(viewer));
        when(mockProfileViewerService.getProfileViewerOfCv(any(Pageable.class), eq(0L))).thenReturn(viewerPage);

        when(mockProfileViewerService.findAll(PageRequest.of(0, 1))).thenReturn(Collections.emptyList());
        final ResponseDataPagination result = candidateManageServiceImplUnderTest.getProfileViewer(1, 10, 0L, 0L);
    }

    @Test
    public void testAppliedJob() throws Exception {
        final AppliedJobRequest request = new AppliedJobRequest();
        request.setJobId(0L);
        request.setCandidateId(0L);
        request.setCvUrl("cvUploadUrl");
        final CV cv = new CV(0L, 0L, 0L, "summary", "totalExperienceYear");
        when(mockCvService.getCVByCandidateId(0L)).thenReturn(cv);

        when(mockJobService.existsById(0L)).thenReturn(false);
        when(mockCandidateService.existsById(0L)).thenReturn(false);
        final AppliedJob appliedJob = new AppliedJob(0L, 0L, 0L, false, "approvalStatus", false, "cvUploadUrl");
        when(mockAppliedJobService.getAppliedJobBefore(0L, 0L)).thenReturn(appliedJob);
        final AppliedJob appliedJob1 = new AppliedJob(0L, 0L, 0L, false, "approvalStatus", false, "cvUploadUrl");
        when(mockModelMapper.map(any(Object.class), eq(AppliedJob.class))).thenReturn(appliedJob1);
        final AppliedJob appliedJob2 = new AppliedJob(0L, 0L, 0L, false, "approvalStatus", false, "cvUploadUrl");
        when(mockAppliedJobRepository.save(any(AppliedJob.class))).thenReturn(appliedJob2);
        candidateManageServiceImplUnderTest.appliedJob(request);
        verify(mockAppliedJobRepository).save(any(AppliedJob.class));
    }

    @Test
    public void testAppliedJob_CVServiceReturnsNull() {
        final AppliedJobRequest request = new AppliedJobRequest();
        request.setJobId(0L);
        request.setCandidateId(0L);
        request.setCvUrl("cvUploadUrl");

        when(mockCvService.getCVByCandidateId(0L)).thenReturn(null);
        assertThatThrownBy(() -> candidateManageServiceImplUnderTest.appliedJob(request))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testAppliedJob_AppliedJobServiceReturnsNull() throws Exception {
        final AppliedJobRequest request = new AppliedJobRequest();
        request.setJobId(0L);
        request.setCandidateId(0L);
        request.setCvUrl("cvUploadUrl");
        final CV cv = new CV(0L, 0L, 0L, "summary", "totalExperienceYear");
        when(mockCvService.getCVByCandidateId(0L)).thenReturn(cv);
        when(mockJobService.existsById(0L)).thenReturn(false);
        when(mockCandidateService.existsById(0L)).thenReturn(false);
        when(mockAppliedJobService.getAppliedJobBefore(0L, 0L)).thenReturn(null);
        final AppliedJob appliedJob = new AppliedJob(0L, 0L, 0L, false, "approvalStatus", false, "cvUploadUrl");
        when(mockModelMapper.map(any(Object.class), eq(AppliedJob.class))).thenReturn(appliedJob);
        final AppliedJob appliedJob1 = new AppliedJob(0L, 0L, 0L, false, "approvalStatus", false, "cvUploadUrl");
        when(mockAppliedJobRepository.save(any(AppliedJob.class))).thenReturn(appliedJob1);
        candidateManageServiceImplUnderTest.appliedJob(request);
        verify(mockAppliedJobRepository).save(any(AppliedJob.class));
    }

    @Test
    public void testAppliedJob_ThrowsException() {
        final AppliedJobRequest request = new AppliedJobRequest();
        request.setJobId(0L);
        request.setCandidateId(0L);
        request.setCvUrl("cvUploadUrl");
        final CV cv = new CV(0L, 0L, 0L, "summary", "totalExperienceYear");
        when(mockCvService.getCVByCandidateId(0L)).thenReturn(cv);

        when(mockJobService.existsById(0L)).thenReturn(false);
        when(mockCandidateService.existsById(0L)).thenReturn(false);
        final AppliedJob appliedJob = new AppliedJob(0L, 0L, 0L, false, "approvalStatus", false, "cvUploadUrl");
        when(mockAppliedJobService.getAppliedJobBefore(0L, 0L)).thenReturn(appliedJob);
        final AppliedJob appliedJob1 = new AppliedJob(0L, 0L, 0L, false, "approvalStatus", false, "cvUploadUrl");
        when(mockModelMapper.map(any(Object.class), eq(AppliedJob.class))).thenReturn(appliedJob1);
        final AppliedJob appliedJob2 = new AppliedJob(0L, 0L, 0L, false, "approvalStatus", false, "cvUploadUrl");
        when(mockAppliedJobRepository.save(any(AppliedJob.class))).thenReturn(appliedJob2);
        assertThatThrownBy(() -> candidateManageServiceImplUnderTest.appliedJob(request)).isInstanceOf(Exception.class);
        verify(mockAppliedJobRepository).save(any(AppliedJob.class));
    }
}
