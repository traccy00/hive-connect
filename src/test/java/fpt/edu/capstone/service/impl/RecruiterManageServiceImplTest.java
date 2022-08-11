package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.CV.FindCVResponse;
import fpt.edu.capstone.dto.CV.ViewCVWithPayResponse;
import fpt.edu.capstone.dto.CV.ViewCvResponse;
import fpt.edu.capstone.dto.admin.CommonRecruiterInformationResponse;
import fpt.edu.capstone.dto.banner.UploadBannerRequest;
import fpt.edu.capstone.dto.company.CompanyImageResponse;
import fpt.edu.capstone.dto.company.CompanyInformationResponse;
import fpt.edu.capstone.dto.recruiter.DetailPurchasedPackageResponse;
import fpt.edu.capstone.dto.recruiter.JobDetailPurchasedResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterProfileResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterUpdateProfileRequest;
import fpt.edu.capstone.entity.*;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.*;
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
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecruiterManageServiceImplTest {

    @Mock
    private ModelMapper mockModelMapper;
    @Mock
    private RecruiterRepository mockRecruiterRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private JobService mockJobService;
    @Mock
    private AppliedJobService mockAppliedJobService;
    @Mock
    private RecruiterService mockRecruiterService;
    @Mock
    private UserService mockUserService;
    @Mock
    private ImageServiceImpl mockImageService;
    @Mock
    private CompanyService mockCompanyService;
    @Mock
    private CVService mockCvService;
    @Mock
    private CandidateService mockCandidateService;
    @Mock
    private WorkExperienceService mockWorkExperienceService;
    @Mock
    private EducationService mockEducationService;
    @Mock
    private PaymentService mockPaymentService;
    @Mock
    private BannerService mockBannerService;
    @Mock
    private BannerActiveRepository mockBannerActiveRepository;
    @Mock
    private DetailPackageService mockDetailPackageService;
    @Mock
    private RentalPackageService mockRentalPackageService;
    @Mock
    private BannerActiveService mockBannerActiveService;
    @Mock
    private FieldsService mockFieldsService;
    @Mock
    private CountryService mockCountryService;
    @Mock
    private CertificateService mockCertificateService;
    @Mock
    private LanguageService mockLanguageService;
    @Mock
    private MajorLevelService mockMajorLevelService;
    @Mock
    private OtherSkillService mockOtherSkillService;
    @Mock
    private ProfileViewerService mockProfileViewerService;
    @Mock
    private ProfileViewerRepository mockProfileViewerRepository;
    @Mock
    private AppliedJobRepository mockAppliedJobRepository;
    @Mock
    private CVRepository mockCvRepository;
    @Mock
    private EducationRepository mockEducationRepository;
    @Mock
    private MajorService mockMajorService;
    @Mock
    private WorkExperienceRepository mockWorkExperienceRepository;

    private RecruiterManageServiceImpl recruiterManageServiceImplUnderTest;

    @Before
    public void setUp() {
        recruiterManageServiceImplUnderTest = new RecruiterManageServiceImpl(mockModelMapper, mockRecruiterRepository,
                mockUserRepository, mockJobService, mockAppliedJobService, mockRecruiterService, mockUserService,
                mockImageService, mockCompanyService, mockCvService, mockCandidateService, mockWorkExperienceService,
                mockEducationService, mockPaymentService, mockBannerService, mockBannerActiveRepository,
                mockDetailPackageService, mockRentalPackageService, mockBannerActiveService, mockFieldsService,
                mockCountryService, mockCertificateService, mockLanguageService, mockMajorLevelService,
                mockOtherSkillService, mockProfileViewerService, mockProfileViewerRepository, mockAppliedJobRepository,
                mockCvRepository, mockEducationRepository, mockMajorService, mockWorkExperienceRepository);
    }
    private Recruiter recruiter(){
        Recruiter recruiter = new Recruiter();
        recruiter.setId(0L);
        recruiter.setCompanyId(0L);
        recruiter.setCompanyName("companyName");
        recruiter.setFullName("fullName");
        recruiter.setVerifyAccount(true);
        recruiter.setGender(false);
        recruiter.setPosition("HR");
        recruiter.setLinkedinAccount("linkedinAccount");
        recruiter.setBusinessLicense("businessLicense");
        recruiter.setAdditionalLicense("additionalLicense");
        recruiter.setBusinessLicenseUrl("businessLicenseUrl");
        recruiter.setAdditionalLicenseUrl("additionalLicenseUrl");
        recruiter.setUserId(0L);
        recruiter.setDeleted(false);
        recruiter.setCompanyAddress("companyAddress");
        recruiter.setBusinessLicenseApprovalStatus("businessLicenseApprovalStatus");
        recruiter.setAvatarUrl("avatarUrl");
        recruiter.setTotalCvView(10);
        return recruiter;
    }

    private Job job(){
        Job job = new Job();
        job.setId(0L);
        job.setCompanyId(0L);
        job.setJobName("jobName");
        job.setWorkPlace("workPlace");
        job.setWorkForm("workForm");
        job.setStartDate(LocalDateTime.now());
        job.setEndDate(LocalDateTime.now().plusDays(10));
        job.setFromSalary(0L);
        job.setToSalary(0L);
        job.setNumberRecruits(0L);
        job.setRank("rank");
        job.setExperience("experience");
        job.setGender(false);
        job.setJobDescription("jobDescription");
        job.setJobRequirement("jobRequirement");
        job.setBenefit("benefit");
        job.setFieldId(0L);
        job.setIsDeleted(0);
        job.setPopularJob(false);
        job.setNewJob(false);
        job.setUrgentJob(false);
        job.setRecruiterId(0L);
        job.setWeekday("weekday");
        job.setCountryId(0L);
        job.setFlag("Posted");
        return job;
    }

    private CV cv(){
        CV cv = new CV();
        cv.setId(0L);
        cv.setCandidateId(0L);
        cv.setIsDeleted(0);
        cv.setSummary("");
        cv.setTotalExperienceYear("1 năm");
        return cv;
    }

    private Candidate candidate(){
        Candidate candidate = new Candidate();
        candidate.setId(0L);
        candidate.setUserId(0L);
        candidate.setGender(false);
        candidate.setBirthDate(LocalDateTime.now());
        candidate.setSearchHistory("");
        candidate.setCountry("Việt Nam");
        candidate.setFullName("fullName");
        candidate.setAddress("address");
        return candidate;
    }

    private Users users(){
        Users users = new Users();
        users.setId(0L);
        users.setUsername("username");
        users.setPassword("password");
        users.setEmail("email");
        users.setPhone("0967445450");
        users.setRoleId(0L);
        users.setIsDeleted(0);
        users.setLastLoginTime(LocalDateTime.now());
        users.setVerifiedEmail(false);
        users.setVerifiedPhone(false);
        users.setActive(true);
        users.setLocked(false);
        users.setAvatar("avatar");
        users.setResetPasswordToken("setResetPasswordToken");
        users.setGoogle(false);
        return users;
    }

    private Banner banner(){
        Banner banner = new Banner();
        banner.setId(0L);
        banner.setRentalPackageId(0L);
        banner.setPrice(0L);
        banner.setDiscount(0L);
        banner.setDeleted(false);
        return banner;
    }

    private BannerActive bannerActive(){
        BannerActive bannerActive = new BannerActive();
        bannerActive.setId(0L);
        bannerActive.setPaymentId(0L);
        bannerActive.setDeleted(false);
        bannerActive.setApprovalStatus("APPROVED");
        bannerActive.setApprovalDate(LocalDateTime.now());
        return bannerActive;
    }

    private Payment payment(){
        Payment payment = new Payment();
        payment.setId(0L);
        payment.setRecruiterId(0L);
        payment.setJobId(0L);
        payment.setDetailPackageId(0L);
        payment.setBannerId(0L);
        payment.setTransactionCode("132451");
        payment.setAmount(10000000);
        payment.setDescription("des");
        payment.setOrderType("pay");
        payment.setBankCode("NCB");
        payment.setCommand("");
        payment.setCurrCode("vn");
        payment.setLocal("localhost");
        payment.setExpiredDate(LocalDateTime.now().plusDays(10));
        payment.setExpiredStatus(false);
        return payment;
    }

    private Image image(){
        Image image = new Image();
        image.setId(0L);
        image.setName("name");
        image.setUrl("avatar");
        image.setCompanyId(0L);
        image.setIsDeleted(0);
        image.setAvatar(false);
        image.setBanner(false);
        image.setEventId(0L);
        image.setContentType("contentType");
        image.setRecruiterId(0L);
        image.setCandidateId(0L);
        image.setCover(false);
        return image;
    }

    private Company company(){
        Company company1 = new Company();
        company1.setId(0L);
        company1.setFieldWork("fieldWork");
        company1.setName("name");
        company1.setEmail("email");
        company1.setPhone("phone");
        company1.setDescription("description");
        company1.setWebsite("website");
        company1.setNumberEmployees("numberEmployees");
        company1.setAddress("address");
        company1.setTaxCode("taxCode");
        company1.setIsDeleted(0);
        company1.setMapUrl("mapUrl");
        company1.setCreatorId(0L);
        company1.setLocked(false);
        return company1;
    }
    
    @Test
    public void testGetCommonInforOfRecruiter() {
        final Optional<Recruiter> recruiter = Optional.of(recruiter());
        when(mockRecruiterRepository.findById(0L)).thenReturn(recruiter);
        final Users users = users();
        when(mockUserRepository.getById(0L)).thenReturn(users);
        when(mockJobService.countTotalCreatedJobOfRecruiter(0L)).thenReturn(null);
        when(mockAppliedJobService.countApplyPercentage(0L)).thenReturn(Arrays.asList());
        final CommonRecruiterInformationResponse result = recruiterManageServiceImplUnderTest.getCommonInforOfRecruiter(0L);
    }

    @Test
    public void testGetCommonInforOfRecruiter_RecruiterRepositoryReturnsAbsent() {
        when(mockRecruiterRepository.findById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> recruiterManageServiceImplUnderTest.getCommonInforOfRecruiter(0L))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testGetCommonInforOfRecruiter_UserRepositoryReturnsNull() {
        final Optional<Recruiter> recruiter = Optional.of(recruiter());
        when(mockRecruiterRepository.findById(0L)).thenReturn(recruiter);
        when(mockUserRepository.getById(0L)).thenReturn(null);
        assertThatThrownBy(() -> recruiterManageServiceImplUnderTest.getCommonInforOfRecruiter(0L))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testGetCommonInforOfRecruiter_JobServiceReturnsNull() {
        final Optional<Recruiter> recruiter = Optional.of(recruiter());
        when(mockRecruiterRepository.findById(0L)).thenReturn(recruiter);
        final Users users = users();
        when(mockUserRepository.getById(0L)).thenReturn(users);
        when(mockJobService.countTotalCreatedJobOfRecruiter(0L)).thenReturn(null);
        when(mockAppliedJobService.countApplyPercentage(0L)).thenReturn(Arrays.asList());
        final CommonRecruiterInformationResponse result = recruiterManageServiceImplUnderTest.getCommonInforOfRecruiter(
                0L);
    }

    @Test
    public void testGetCommonInforOfRecruiter_AppliedJobServiceReturnsNull() {
        final Optional<Recruiter> recruiter = Optional.of(recruiter());
        when(mockRecruiterRepository.findById(0L)).thenReturn(recruiter);
        final Users users = users();
        when(mockUserRepository.getById(0L)).thenReturn(users);
        when(mockJobService.countTotalCreatedJobOfRecruiter(0L)).thenReturn(null);
        when(mockAppliedJobService.countApplyPercentage(0L)).thenReturn(null);
        final CommonRecruiterInformationResponse result = recruiterManageServiceImplUnderTest.getCommonInforOfRecruiter(0L);
    }

    @Test
    public void testGetCommonInforOfRecruiter_AppliedJobServiceReturnsNoItems() {
        final Optional<Recruiter> recruiter = Optional.of(recruiter());
        when(mockRecruiterRepository.findById(0L)).thenReturn(recruiter);
        final Users users = users();
        when(mockUserRepository.getById(0L)).thenReturn(users);
        when(mockJobService.countTotalCreatedJobOfRecruiter(0L)).thenReturn(null);
        when(mockAppliedJobService.countApplyPercentage(0L)).thenReturn(Collections.emptyList());
        final CommonRecruiterInformationResponse result = recruiterManageServiceImplUnderTest.getCommonInforOfRecruiter(0L);
    }

    @Test
    public void testGetRecruitersOfCompany() {
        final Page<Recruiter> recruiterPage = new PageImpl<>(Arrays.asList(recruiter()));
        when(mockRecruiterService.getRecruiterByCompanyId(0, 0, 0L)).thenReturn(recruiterPage);
        final Users users = users();
        when(mockUserService.getUserById(0L)).thenReturn(users);
        final Image image = image();
        when(mockImageService.getAvatarRecruiter(0L)).thenReturn(image);
        final ResponseDataPagination result = recruiterManageServiceImplUnderTest.getRecruitersOfCompany(1, 10, 0L);
    }

    @Test
    public void testGetRecruitersOfCompany_RecruiterServiceReturnsNoItems() {
        when(mockRecruiterService.getRecruiterByCompanyId(0, 0, 0L))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        final Users users = users();
        when(mockUserService.getUserById(0L)).thenReturn(users);
        final Image image = image();
        when(mockImageService.getAvatarRecruiter(0L)).thenReturn(image);
        final ResponseDataPagination result = recruiterManageServiceImplUnderTest.getRecruitersOfCompany(1, 10, 0L);
    }

    @Test
    public void testGetRecruitersOfCompany_UserServiceReturnsNull() {
        final Page<Recruiter> recruiterPage = new PageImpl<>(Arrays.asList(recruiter()));
        when(mockRecruiterService.getRecruiterByCompanyId(0, 0, 0L)).thenReturn(recruiterPage);
        when(mockUserService.getUserById(0L)).thenReturn(null);
        assertThatThrownBy(() -> recruiterManageServiceImplUnderTest.getRecruitersOfCompany(1, 10, 0L))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testGetRecruitersOfCompany_ImageServiceImplReturnsNull() {
        final Page<Recruiter> recruiterPage = new PageImpl<>(Arrays.asList(recruiter()));
        when(mockRecruiterService.getRecruiterByCompanyId(0, 0, 0L)).thenReturn(recruiterPage);
        final Users users = users();
        when(mockUserService.getUserById(0L)).thenReturn(users);
        when(mockImageService.getAvatarRecruiter(0L)).thenReturn(null);
        final ResponseDataPagination result = recruiterManageServiceImplUnderTest.getRecruitersOfCompany(0, 0, 0L);
    }

    @Test
    public void testUpdateRecruiterInformation() throws Exception {
        final RecruiterUpdateProfileRequest request = new RecruiterUpdateProfileRequest();
        request.setFullName("fullName");
        request.setPhone("phone");
        request.setGender(false);
        request.setPosition("phone");
        request.setLinkedinAccount("linkedinAccount");
        request.setAvatarUrl("avatarUrl");
        final Recruiter recruiter = recruiter();
        when(mockRecruiterService.getById(0L)).thenReturn(recruiter);
        final Recruiter recruiter1 = recruiter();
        when(mockRecruiterRepository.save(any(Recruiter.class))).thenReturn(recruiter1);
        final Users users = users();
        when(mockUserRepository.getUserById(0L)).thenReturn(users);
        final Users users1 = users();
        when(mockUserService.findByPhoneAndIdIsNotIn("phone", 0L)).thenReturn(users1);
        final Users users2 = users();
        when(mockUserService.findById(0L)).thenReturn(users2);
        final Optional<Users> optional = Optional.of(users());
        when(mockUserService.findByPhoneNumber("phone")).thenReturn(optional);
        final Users users3 = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users3);
        final Users users4 = users();
        when(mockUserService.getUserById(0L)).thenReturn(users4);
        final Recruiter recruiter2 = recruiter();
        when(mockRecruiterService.getRecruiterByUserId(0L)).thenReturn(recruiter2);
        final Company company = company();
        when(mockCompanyService.getCompanyById(0L)).thenReturn(company);
        final RecruiterProfileResponse result = recruiterManageServiceImplUnderTest.updateRecruiterInformation(0L,
                request);
        verify(mockRecruiterRepository).save(any(Recruiter.class));
        verify(mockUserRepository).save(any(Users.class));
    }

    @Test
    public void testUpdateRecruiterInformation_RecruiterServiceGetByIdReturnsNull() {
        final RecruiterUpdateProfileRequest request = new RecruiterUpdateProfileRequest();
        request.setFullName("fullName");
        request.setPhone("phone");
        request.setGender(false);
        request.setPosition("phone");
        request.setLinkedinAccount("linkedinAccount");
        request.setAvatarUrl("avatarUrl");
        when(mockRecruiterService.getById(0L)).thenReturn(null);
        assertThatThrownBy(
                () -> recruiterManageServiceImplUnderTest.updateRecruiterInformation(0L, request))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testUpdateRecruiterInformation_UserRepositoryGetUserByIdReturnsNull() {
        final RecruiterUpdateProfileRequest request = new RecruiterUpdateProfileRequest();
        request.setFullName("fullName");
        request.setPhone("phone");
        request.setGender(false);
        request.setPosition("phone");
        request.setLinkedinAccount("linkedinAccount");
        request.setAvatarUrl("avatarUrl");

        final Recruiter recruiter = recruiter();
        when(mockRecruiterService.getById(0L)).thenReturn(recruiter);
        final Recruiter recruiter1 = recruiter();
        when(mockRecruiterRepository.save(any(Recruiter.class))).thenReturn(recruiter1);

        when(mockUserRepository.getUserById(0L)).thenReturn(null);
        assertThatThrownBy(
                () -> recruiterManageServiceImplUnderTest.updateRecruiterInformation(0L, request))
                .isInstanceOf(HiveConnectException.class);
        verify(mockRecruiterRepository).save(any(Recruiter.class));
    }

    @Test
    public void testUpdateRecruiterInformation_UserServiceFindByPhoneAndIdIsNotInReturnsNull() throws Exception {
        final RecruiterUpdateProfileRequest request = new RecruiterUpdateProfileRequest();
        request.setFullName("fullName");
        request.setPhone("phone");
        request.setGender(false);
        request.setPosition("phone");
        request.setLinkedinAccount("linkedinAccount");
        request.setAvatarUrl("avatarUrl");
        final Recruiter recruiter = recruiter();
        when(mockRecruiterService.getById(0L)).thenReturn(recruiter);
        final Recruiter recruiter1 = recruiter();
        when(mockRecruiterRepository.save(any(Recruiter.class))).thenReturn(recruiter1);
        final Users users = users();
        when(mockUserRepository.getUserById(0L)).thenReturn(users);
        when(mockUserService.findByPhoneAndIdIsNotIn("phone", 0L)).thenReturn(null);

        final Users users1 = users();
        when(mockUserService.findById(0L)).thenReturn(users1);
        
        final Optional<Users> optional = Optional.of(users());
        when(mockUserService.findByPhoneNumber("phone")).thenReturn(optional);
        
        final Users users2 = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users2);

        final Users users3 = users();
        when(mockUserService.getUserById(0L)).thenReturn(users3);
        
        final Recruiter recruiter2 = recruiter();
        when(mockRecruiterService.getRecruiterByUserId(0L)).thenReturn(recruiter2);
        
        final Company company = company();
        when(mockCompanyService.getCompanyById(0L)).thenReturn(company);
        
        final RecruiterProfileResponse result = recruiterManageServiceImplUnderTest.updateRecruiterInformation(0L,
                request);
        verify(mockRecruiterRepository).save(any(Recruiter.class));
        verify(mockUserRepository).save(any(Users.class));
    }

    @Test
    public void testUpdateRecruiterInformation_UserServiceFindByPhoneNumberReturnsAbsent() throws Exception {
        final RecruiterUpdateProfileRequest request = new RecruiterUpdateProfileRequest();
        request.setFullName("fullName");
        request.setPhone("phone");
        request.setGender(false);
        request.setPosition("phone");
        request.setLinkedinAccount("linkedinAccount");
        request.setAvatarUrl("avatarUrl");
        final Recruiter recruiter = recruiter();
        when(mockRecruiterService.getById(0L)).thenReturn(recruiter);
        final Recruiter recruiter1 =   recruiter();
        when(mockRecruiterRepository.save(any(Recruiter.class))).thenReturn(recruiter1);
        final Users users = users();
        when(mockUserRepository.getUserById(0L)).thenReturn(users);
        final Users users1 = users();
        when(mockUserService.findByPhoneAndIdIsNotIn("phone", 0L)).thenReturn(users1);
        final Users users2 = users();
        when(mockUserService.findById(0L)).thenReturn(users2);
        when(mockUserService.findByPhoneNumber("phone")).thenReturn(Optional.empty());
        final Users users3 = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users3);
        final Users users4 = users();
        when(mockUserService.getUserById(0L)).thenReturn(users4);
        final Recruiter recruiter2 =   recruiter();
        when(mockRecruiterService.getRecruiterByUserId(0L)).thenReturn(recruiter2);
        final Company company = company();
        when(mockCompanyService.getCompanyById(0L)).thenReturn(company);
        
        final RecruiterProfileResponse result = recruiterManageServiceImplUnderTest.updateRecruiterInformation(0L,
                request);
        verify(mockRecruiterRepository).save(any(Recruiter.class));
        verify(mockUserRepository).save(any(Users.class));
    }

    @Test
    public void testUpdateRecruiterInformation_UserServiceGetUserByIdReturnsNull() {
        final RecruiterUpdateProfileRequest request = new RecruiterUpdateProfileRequest();
        request.setFullName("fullName");
        request.setPhone("phone");
        request.setGender(false);
        request.setPosition("phone");
        request.setLinkedinAccount("linkedinAccount");
        request.setAvatarUrl("avatarUrl");
        final Recruiter recruiter =   recruiter();
        when(mockRecruiterService.getById(0L)).thenReturn(recruiter);
        final Recruiter recruiter1 =   recruiter();
        when(mockRecruiterRepository.save(any(Recruiter.class))).thenReturn(recruiter1);
        final Users users = users();
        when(mockUserRepository.getUserById(0L)).thenReturn(users);
        final Users users1 = users();
        when(mockUserService.findByPhoneAndIdIsNotIn("phone", 0L)).thenReturn(users1);
        final Users users2 = users();
        when(mockUserService.findById(0L)).thenReturn(users2);
        final Optional<Users> optional = Optional.of(users());
        when(mockUserService.findByPhoneNumber("phone")).thenReturn(optional);
        final Users users3 = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users3);
        when(mockUserService.getUserById(0L)).thenReturn(null);

        assertThatThrownBy(
                () -> recruiterManageServiceImplUnderTest.updateRecruiterInformation(0L, request))
                .isInstanceOf(HiveConnectException.class);
        verify(mockRecruiterRepository).save(any(Recruiter.class));
        verify(mockUserRepository).save(any(Users.class));
    }

    @Test
    public void testUpdateRecruiterInformation_RecruiterServiceGetRecruiterByUserIdReturnsNull() {
        final RecruiterUpdateProfileRequest request = new RecruiterUpdateProfileRequest();
        request.setFullName("fullName");
        request.setPhone("phone");
        request.setGender(false);
        request.setPosition("phone");
        request.setLinkedinAccount("linkedinAccount");
        request.setAvatarUrl("avatarUrl");
        final Recruiter recruiter =   recruiter();
        when(mockRecruiterService.getById(0L)).thenReturn(recruiter);
        
        final Recruiter recruiter1 =   recruiter();
        when(mockRecruiterRepository.save(any(Recruiter.class))).thenReturn(recruiter1);
        
        final Users users = users();
        when(mockUserRepository.getUserById(0L)).thenReturn(users);
        
        final Users users1 = users();
        when(mockUserService.findByPhoneAndIdIsNotIn("phone", 0L)).thenReturn(users1);
        
        final Users users2 = users();
        when(mockUserService.findById(0L)).thenReturn(users2);
        
        final Optional<Users> optional = Optional.of(users());
        when(mockUserService.findByPhoneNumber("phone")).thenReturn(optional);
        
        final Users users3 = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users3);
        
        final Users users4 = users();
        when(mockUserService.getUserById(0L)).thenReturn(users4);

        when(mockRecruiterService.getRecruiterByUserId(0L)).thenReturn(null);
        
        assertThatThrownBy(
                () -> recruiterManageServiceImplUnderTest.updateRecruiterInformation(0L, request))
                .isInstanceOf(HiveConnectException.class);
        verify(mockRecruiterRepository).save(any(Recruiter.class));
        verify(mockUserRepository).save(any(Users.class));
    }

    @Test
    public void testUpdateRecruiterInformation_CompanyServiceReturnsNull() throws Exception {
        final RecruiterUpdateProfileRequest request = new RecruiterUpdateProfileRequest();
        request.setFullName("fullName");
        request.setPhone("phone");
        request.setGender(false);
        request.setPosition("phone");
        request.setLinkedinAccount("linkedinAccount");
        request.setAvatarUrl("avatarUrl");
        
        final Recruiter recruiter =   recruiter();
        when(mockRecruiterService.getById(0L)).thenReturn(recruiter);
        
        final Recruiter recruiter1 =   recruiter();
        when(mockRecruiterRepository.save(any(Recruiter.class))).thenReturn(recruiter1);
        
        final Users users = users();
        when(mockUserRepository.getUserById(0L)).thenReturn(users);
        
        final Users users1 = users();
        when(mockUserService.findByPhoneAndIdIsNotIn("phone", 0L)).thenReturn(users1);
        
        final Users users2 = users();
        when(mockUserService.findById(0L)).thenReturn(users2);
        
        final Optional<Users> optional = Optional.of(users());
        when(mockUserService.findByPhoneNumber("phone")).thenReturn(optional);
        
        final Users users3 = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users3);
        
        final Users users4 = users();
        when(mockUserService.getUserById(0L)).thenReturn(users4);
        
        final Recruiter recruiter2 =   recruiter();
        when(mockRecruiterService.getRecruiterByUserId(0L)).thenReturn(recruiter2);

        when(mockCompanyService.getCompanyById(0L)).thenReturn(null);
        
        final RecruiterProfileResponse result = recruiterManageServiceImplUnderTest.updateRecruiterInformation(0L,
                request);
        verify(mockRecruiterRepository).save(any(Recruiter.class));
        verify(mockUserRepository).save(any(Users.class));
    }

    @Test
    public void testUpdateRecruiterInformation_ThrowsException() {
        final RecruiterUpdateProfileRequest request = new RecruiterUpdateProfileRequest();
        request.setFullName("fullName");
        request.setPhone("phone");
        request.setGender(false);
        request.setPosition("phone");
        request.setLinkedinAccount("linkedinAccount");
        request.setAvatarUrl("avatarUrl");
        final Recruiter recruiter =   recruiter();
        when(mockRecruiterService.getById(0L)).thenReturn(recruiter);
        final Recruiter recruiter1 =   recruiter();
        when(mockRecruiterRepository.save(any(Recruiter.class))).thenReturn(recruiter1);
        
        final Users users = users();
        when(mockUserRepository.getUserById(0L)).thenReturn(users);
        
        final Users users1 = users();
        when(mockUserService.findByPhoneAndIdIsNotIn("phone", 0L)).thenReturn(users1);
        
        final Users users2 = users();
        when(mockUserService.findById(0L)).thenReturn(users2);
        
        final Optional<Users> optional = Optional.of(users());
        when(mockUserService.findByPhoneNumber("phone")).thenReturn(optional);
        
        final Users users3 = users();
        when(mockUserRepository.save(any(Users.class))).thenReturn(users3);
        
        final Users users4 = users();
        when(mockUserService.getUserById(0L)).thenReturn(users4);
        
        final Recruiter recruiter2 =   recruiter();
        when(mockRecruiterService.getRecruiterByUserId(0L)).thenReturn(recruiter2);
        
        final Company company = company();
        when(mockCompanyService.getCompanyById(0L)).thenReturn(company);
        
        assertThatThrownBy(
                () -> recruiterManageServiceImplUnderTest.updateRecruiterInformation(0L, request))
                .isInstanceOf(Exception.class);
        verify(mockRecruiterRepository).save(any(Recruiter.class));
        verify(mockUserRepository).save(any(Users.class));
    }

    @Test
    public void testGetRecruiterProfile() {
        final Users users = users();
        when(mockUserService.getUserById(0L)).thenReturn(users);
        final Recruiter recruiter =   recruiter();
        when(mockRecruiterService.getRecruiterByUserId(0L)).thenReturn(recruiter);
        final Company company = company();
        when(mockCompanyService.getCompanyById(0L)).thenReturn(company);
        final RecruiterProfileResponse result = recruiterManageServiceImplUnderTest.getRecruiterProfile(0L);
    }

    @Test
    public void testGetRecruiterProfile_UserServiceReturnsNull() {
        when(mockUserService.getUserById(0L)).thenReturn(null);
        assertThatThrownBy(() -> recruiterManageServiceImplUnderTest.getRecruiterProfile(0L))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testGetRecruiterProfile_RecruiterServiceReturnsNull() {
        final Users users = users();
        when(mockUserService.getUserById(0L)).thenReturn(users);
        when(mockRecruiterService.getRecruiterByUserId(0L)).thenReturn(null);
        assertThatThrownBy(() -> recruiterManageServiceImplUnderTest.getRecruiterProfile(0L))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testGetRecruiterProfile_CompanyServiceReturnsNull() {
        final Users users = users();
        when(mockUserService.getUserById(0L)).thenReturn(users);
        final Recruiter recruiter =   recruiter();
        when(mockRecruiterService.getRecruiterByUserId(0L)).thenReturn(recruiter);
        when(mockCompanyService.getCompanyById(0L)).thenReturn(null);
        final RecruiterProfileResponse result = recruiterManageServiceImplUnderTest.getRecruiterProfile(0L);
    }

    @Test
    public void testGetCompanyInformation() {
        final Company company1 = company();
        final Optional<Company> company = Optional.of(company1);
        when(mockCompanyService.findById(0L)).thenReturn(company);
        final Image image = image();
        when(mockImageService.getImageCompany(0L, true)).thenReturn(image);
        
        final List<Image> images = Arrays.asList(image());
        when(mockImageService.getCompanyImageList(0L, false, false)).thenReturn(images);

        when(mockModelMapper.map(any(Object.class), eq(CompanyImageResponse.class)))
                .thenReturn(new CompanyImageResponse(0L, "url"));
        final CompanyInformationResponse result = recruiterManageServiceImplUnderTest.getCompanyInformation(0L, 0L);
    }

    @Test
    public void testGetCompanyInformation_CompanyServiceReturnsAbsent() {
        when(mockCompanyService.findById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> recruiterManageServiceImplUnderTest.getCompanyInformation(0L, 0L))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testGetCompanyInformation_ImageServiceImplGetImageCompanyReturnsNull() {
        final Company company1 = company();
        final Optional<Company> company = Optional.of(company1);
        when(mockCompanyService.findById(0L)).thenReturn(company);
        when(mockImageService.getImageCompany(0L, true)).thenReturn(null);
        final List<Image> images = Arrays.asList(image());
        when(mockImageService.getCompanyImageList(0L, false, false)).thenReturn(images);

        when(mockModelMapper.map(any(Object.class), eq(CompanyImageResponse.class)))
                .thenReturn(new CompanyImageResponse(0L, "url"));
        
        final CompanyInformationResponse result = recruiterManageServiceImplUnderTest.getCompanyInformation(0L, 0L);
    }

    @Test
    public void testGetCompanyInformation_ImageServiceImplGetCompanyImageListReturnsNoItems() {
        final Company company1 = company();
        final Optional<Company> company = Optional.of(company1);
        when(mockCompanyService.findById(0L)).thenReturn(company);
        final Image image = image();
        when(mockImageService.getImageCompany(0L, true)).thenReturn(image);
        when(mockImageService.getCompanyImageList(0L, false, false)).thenReturn(Collections.emptyList());
        when(mockModelMapper.map(any(Object.class), eq(CompanyImageResponse.class)))
                .thenReturn(new CompanyImageResponse(0L, "url"));
        final CompanyInformationResponse result = recruiterManageServiceImplUnderTest.getCompanyInformation(0L, 0L);
    }

    @Test
    public void testUploadBanner() {
        final UploadBannerRequest request = new UploadBannerRequest();
        request.setPaymentId(0L);
        request.setSpotLightImage("item");
        request.setHomepageBannerAImage("item");
        request.setHomepageBannerBImage("item");
        request.setHomepageBannerCImage("item");
        request.setJobBannerAImage("item");
        request.setJobBannerBImage("item");
        request.setJobBannerCImage("item");

        final Recruiter recruiter =   recruiter();
        when(mockRecruiterService.getRecruiterById(0L)).thenReturn(recruiter);
        
        final Payment payment = payment();
        when(mockPaymentService.findByIdAndRecruiterId(0L, 0L)).thenReturn(payment);
        final Banner banner = banner();
        when(mockBannerService.findById(0L)).thenReturn(banner);
        final BannerActive active = bannerActive();
        when(mockBannerActiveService.findByPaymentIdAndPosition(0L, "bannerDisplayPosition")).thenReturn(active);
        final BannerActive active1 = bannerActive();
        when(mockBannerActiveRepository.save(any(BannerActive.class))).thenReturn(active1);

        recruiterManageServiceImplUnderTest.uploadBanner(0L, request);
        verify(mockBannerActiveRepository).save(any(BannerActive.class));
    }

    @Test
    public void testUploadBanner_RecruiterServiceReturnsNull() {
        final UploadBannerRequest request = new UploadBannerRequest();
        request.setPaymentId(0L);
        request.setSpotLightImage("item");
        request.setHomepageBannerAImage("item");
        request.setHomepageBannerBImage("item");
        request.setHomepageBannerCImage("item");
        request.setJobBannerAImage("item");
        request.setJobBannerBImage("item");
        request.setJobBannerCImage("item");

        when(mockRecruiterService.getRecruiterById(0L)).thenReturn(null);
        assertThatThrownBy(() -> recruiterManageServiceImplUnderTest.uploadBanner(0L, request))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testUploadBanner_PaymentServiceReturnsNull() {
        final UploadBannerRequest request = new UploadBannerRequest();
        request.setPaymentId(0L);
        request.setSpotLightImage("item");
        request.setHomepageBannerAImage("item");
        request.setHomepageBannerBImage("item");
        request.setHomepageBannerCImage("item");
        request.setJobBannerAImage("item");
        request.setJobBannerBImage("item");
        request.setJobBannerCImage("item");
        final Recruiter recruiter =   recruiter();
        when(mockRecruiterService.getRecruiterById(0L)).thenReturn(recruiter);

        when(mockPaymentService.findByIdAndRecruiterId(0L, 0L)).thenReturn(null);

        assertThatThrownBy(() -> recruiterManageServiceImplUnderTest.uploadBanner(0L, request))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testGetJobOfRecruiter() {
        when(mockRecruiterService.existById(0L)).thenReturn(false);
        final Page<Job> jobs = new PageImpl<>(Arrays.asList(job()));
        when(mockJobService.getJobOfRecruiter(any(Pageable.class), eq(0L))).thenReturn(jobs);

        final Company company = company();
        when(mockCompanyService.getCompanyById(0L)).thenReturn(company);
        when(mockAppliedJobService.countAppliedCVOfJob(0L)).thenReturn(0);
        final ResponseDataPagination result = recruiterManageServiceImplUnderTest.getJobOfRecruiter(0, 0, 0L);
    }

    @Test
    public void testGetJobOfRecruiter_JobServiceReturnsNoItems() {
        when(mockRecruiterService.existById(0L)).thenReturn(false);
        when(mockJobService.getJobOfRecruiter(any(Pageable.class), eq(0L)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        final Company company = company();
        when(mockCompanyService.getCompanyById(0L)).thenReturn(company);
        when(mockAppliedJobService.countAppliedCVOfJob(0L)).thenReturn(0);
        final ResponseDataPagination result = recruiterManageServiceImplUnderTest.getJobOfRecruiter(0, 0, 0L);
    }

    @Test
    public void testGetJobOfRecruiter_CompanyServiceReturnsNull() {
        when(mockRecruiterService.existById(0L)).thenReturn(false);
        final Page<Job> jobs = new PageImpl<>(Arrays.asList(job()));
        when(mockJobService.getJobOfRecruiter(any(Pageable.class), eq(0L))).thenReturn(jobs);
        when(mockCompanyService.getCompanyById(0L)).thenReturn(null);

        assertThatThrownBy(() -> recruiterManageServiceImplUnderTest.getJobOfRecruiter(0, 0, 0L))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testFindCVFilter() {
        final Page<CV> cvPage = new PageImpl<>(Arrays.asList(cv()));
        when(mockCvService.findCVFilter(any(Pageable.class), eq("experience"), eq("candidateAddress"),
                eq("techStack"))).thenReturn(cvPage);

        final FindCVResponse findCVResponse = new FindCVResponse(0L, 0L, 0L, "summary", "totalExperienceYear",
                Arrays.asList("value"), "address", "fullName", "avatarUrl");
        when(mockModelMapper.map(any(Object.class), eq(FindCVResponse.class))).thenReturn(findCVResponse);
        final Candidate candidate = candidate();
        when(mockCandidateService.getCandidateById(0L)).thenReturn(candidate);
        final Users users = users();
        when(mockUserService.getUserById(0L)).thenReturn(users);

        when(mockMajorService.getMajorNameByCVId(0L)).thenReturn(Arrays.asList("value"));
        final ResponseDataPagination result = recruiterManageServiceImplUnderTest.findCVFilter(0, 0, "experience",
                "candidateAddress", "techStack");
    }

    @Test
    public void testFindCVFilter_CVServiceReturnsNoItems() {
        when(mockCvService.findCVFilter(any(Pageable.class), eq("experience"), eq("candidateAddress"),
                eq("techStack"))).thenReturn(new PageImpl<>(Collections.emptyList()));
        final FindCVResponse findCVResponse = new FindCVResponse(0L, 0L, 0L, "summary", "totalExperienceYear",
                Arrays.asList("value"), "address", "fullName", "avatarUrl");
        when(mockModelMapper.map(any(Object.class), eq(FindCVResponse.class))).thenReturn(findCVResponse);
        final Candidate candidate = candidate();
        when(mockCandidateService.getCandidateById(0L)).thenReturn(candidate);
        final Users users = users();
        when(mockUserService.getUserById(0L)).thenReturn(users);
        when(mockMajorService.getMajorNameByCVId(0L)).thenReturn(Arrays.asList("value"));
        final ResponseDataPagination result = recruiterManageServiceImplUnderTest.findCVFilter(0, 0, "experience",
                "candidateAddress", "techStack");
    }

    @Test
    public void testFindCVFilter_MajorServiceReturnsNoItems() {
        final Page<CV> cvPage = new PageImpl<>(Arrays.asList(cv()));
        when(mockCvService.findCVFilter(any(Pageable.class), eq("experience"), eq("candidateAddress"),
                eq("techStack"))).thenReturn(cvPage);
        final FindCVResponse findCVResponse = new FindCVResponse(0L, 0L, 0L, "summary", "totalExperienceYear",
                Arrays.asList("value"), "address", "fullName", "avatarUrl");
        when(mockModelMapper.map(any(Object.class), eq(FindCVResponse.class))).thenReturn(findCVResponse);
        final Candidate candidate =candidate();
        when(mockCandidateService.getCandidateById(0L)).thenReturn(candidate);
        final Users users = users();
        when(mockUserService.getUserById(0L)).thenReturn(users);

        when(mockMajorService.getMajorNameByCVId(0L)).thenReturn(Collections.emptyList());
        final ResponseDataPagination result = recruiterManageServiceImplUnderTest.findCVFilter(0, 0, "experience",
                "candidateAddress", "techStack");
    }

    @Test
    public void testGetDetailPurchasedPackage() {
        final Payment payment = payment();
        when(mockPaymentService.findById(0L)).thenReturn(payment);
        final Banner banner = banner();
        when(mockBannerService.findById(0L)).thenReturn(banner);
        final DetailPackage detailPackage = new DetailPackage(0L, 0L, "detailName", 0L, 0L, "timeExpired",
                "description", false, false, false, 0);
        when(mockDetailPackageService.findById(0L)).thenReturn(detailPackage);

        final Optional<RentalPackage> optional = Optional.of(rentalPackage());
        when(mockRentalPackageService.findById(0L)).thenReturn(optional);

        when(mockBannerActiveService.getAllBannerByPaymentId(0L)).thenReturn(Arrays.asList());
        final Job job = job();
        when(mockJobService.getJobById(0L)).thenReturn(job);
        final JobDetailPurchasedResponse jobDetailPurchasedResponse = new JobDetailPurchasedResponse(0L, "companyName",
                "jobName", "workPlace", "workForm", LocalDateTime.now(),
              LocalDateTime.now(), 0L, 0L, 0L, "rank", "experience", false, "jobDescription",
                "jobRequirement", "benefit", "fieldName", false, false, false, 0L, "weekday", "country", "flag");
        when(mockModelMapper.map(any(Object.class), eq(JobDetailPurchasedResponse.class)))
                .thenReturn(jobDetailPurchasedResponse);

        final Company company1 = company();
        final Optional<Company> company = Optional.of(company1);
        when(mockCompanyService.findById(0L)).thenReturn(company);

        when(mockFieldsService.getById(0L)).thenReturn(new Fields(0L, "fieldName", "status"));
        final Optional<VietnamCountry> vietnamCountry = Optional.of(new VietnamCountry(0L, "country"));
        when(mockCountryService.findById(0L)).thenReturn(vietnamCountry);
        final DetailPurchasedPackageResponse result = recruiterManageServiceImplUnderTest.getDetailPurchasedPackage(0L,
                0L);
    }

    @Test
    public void testGetDetailPurchasedPackage_RentalPackageServiceReturnsAbsent() {
        final Payment payment = payment();
        when(mockPaymentService.findById(0L)).thenReturn(payment);
        final Banner banner =banner();
        when(mockBannerService.findById(0L)).thenReturn(banner);
        final DetailPackage detailPackage = new DetailPackage(0L, 0L, "detailName", 0L, 0L, "timeExpired",
                "description", false, false, false, 0);
        when(mockDetailPackageService.findById(0L)).thenReturn(detailPackage);

        when(mockRentalPackageService.findById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> recruiterManageServiceImplUnderTest.getDetailPurchasedPackage(0L, 0L))
                .isInstanceOf(HiveConnectException.class);
    }

    private RentalPackage rentalPackage(){
        RentalPackage rentalPackage = new RentalPackage();
        rentalPackage.setId(0L);
        rentalPackage.setPackageGroup("packageGroup");
        rentalPackage.setDescription("description");
        rentalPackage.setStatus(false);
        return rentalPackage;
    }
    
    @Test
    public void testGetDetailPurchasedPackage_BannerActiveServiceReturnsNull() {
        final Payment payment = payment();
        when(mockPaymentService.findById(0L)).thenReturn(payment);

        // Configure BannerService.findById(...).
        final Banner banner = banner();
        when(mockBannerService.findById(0L)).thenReturn(banner);
        final DetailPackage detailPackage = new DetailPackage(0L, 0L, "detailName", 0L, 0L, "timeExpired",
                "description", false, false, false, 0);
        when(mockDetailPackageService.findById(0L)).thenReturn(detailPackage);

        final Optional<RentalPackage> optional = Optional.of(rentalPackage());
        when(mockRentalPackageService.findById(0L)).thenReturn(optional);

        when(mockBannerActiveService.getAllBannerByPaymentId(0L)).thenReturn(null);
        final Job job =job();
        when(mockJobService.getJobById(0L)).thenReturn(job);
        final JobDetailPurchasedResponse jobDetailPurchasedResponse = new JobDetailPurchasedResponse(0L, "companyName",
                "jobName", "workPlace", "workForm", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
             LocalDateTime.now(), 0L, 0L, 0L, "rank", "experience", false, "jobDescription",
                "jobRequirement", "benefit", "fieldName", false, false, false, 0L, "weekday", "country", "flag");
        when(mockModelMapper.map(any(Object.class), eq(JobDetailPurchasedResponse.class)))
                .thenReturn(jobDetailPurchasedResponse);
        final Company company1 = company();
        final Optional<Company> company = Optional.of(company1);
        when(mockCompanyService.findById(0L)).thenReturn(company);
        when(mockFieldsService.getById(0L)).thenReturn(new Fields(0L, "fieldName", "status"));
        final Optional<VietnamCountry> vietnamCountry = Optional.of(new VietnamCountry(0L, "country"));
        when(mockCountryService.findById(0L)).thenReturn(vietnamCountry);
        final DetailPurchasedPackageResponse result = recruiterManageServiceImplUnderTest.getDetailPurchasedPackage(0L,
                0L);
    }

    @Test
    public void testGetDetailPurchasedPackage_BannerActiveServiceReturnsNoItems() {
        final Payment payment = payment();
        when(mockPaymentService.findById(0L)).thenReturn(payment);
        
        final Banner banner = banner();
        when(mockBannerService.findById(0L)).thenReturn(banner);
        final DetailPackage detailPackage = new DetailPackage(0L, 0L, "detailName", 0L, 0L, "timeExpired",
                "description", false, false, false, 0);
        when(mockDetailPackageService.findById(0L)).thenReturn(detailPackage);
        final Optional<RentalPackage> optional = Optional.of(rentalPackage());
        when(mockRentalPackageService.findById(0L)).thenReturn(optional);

        when(mockBannerActiveService.getAllBannerByPaymentId(0L)).thenReturn(Collections.emptyList());
        final Job job = job();
        when(mockJobService.getJobById(0L)).thenReturn(job);
        
        final JobDetailPurchasedResponse jobDetailPurchasedResponse = new JobDetailPurchasedResponse(0L, "companyName",
                "jobName", "workPlace", "workForm", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.now(), 0L, 0L, 0L, "rank", "experience", false, "jobDescription",
                "jobRequirement", "benefit", "fieldName", false, false, false, 0L, "weekday", "country", "flag");
        when(mockModelMapper.map(any(Object.class), eq(JobDetailPurchasedResponse.class)))
                .thenReturn(jobDetailPurchasedResponse);
        final Company company1 = company();
        final Optional<Company> company = Optional.of(company1);
        when(mockCompanyService.findById(0L)).thenReturn(company);
        when(mockFieldsService.getById(0L)).thenReturn(new Fields(0L, "fieldName", "status"));
        final Optional<VietnamCountry> vietnamCountry = Optional.of(new VietnamCountry(0L, "country"));
        when(mockCountryService.findById(0L)).thenReturn(vietnamCountry);
        final DetailPurchasedPackageResponse result = recruiterManageServiceImplUnderTest.getDetailPurchasedPackage(0L,
                0L);
    }

    @Test
    public void testGetDetailPurchasedPackage_CompanyServiceReturnsAbsent() {
        final Payment payment = payment();
        when(mockPaymentService.findById(0L)).thenReturn(payment);
        final Banner banner = banner();
        when(mockBannerService.findById(0L)).thenReturn(banner);
        final DetailPackage detailPackage = new DetailPackage(0L, 0L, "detailName", 0L, 0L, "timeExpired",
                "description", false, false, false, 0);
        when(mockDetailPackageService.findById(0L)).thenReturn(detailPackage);
        final Optional<RentalPackage> optional = Optional.of(rentalPackage());
        when(mockRentalPackageService.findById(0L)).thenReturn(optional);
        when(mockBannerActiveService.getAllBannerByPaymentId(0L)).thenReturn(Arrays.asList());
        final Job job = job();
        when(mockJobService.getJobById(0L)).thenReturn(job);
        
        final JobDetailPurchasedResponse jobDetailPurchasedResponse = new JobDetailPurchasedResponse(0L, "companyName",
                "jobName", "workPlace", "workForm", LocalDateTime.now(),
                LocalDateTime.now(), 0L, 0L, 0L, "rank", "experience", false, "jobDescription",
                "jobRequirement", "benefit", "fieldName", false, false, false, 0L, "weekday", "country", "flag");
        when(mockModelMapper.map(any(Object.class), eq(JobDetailPurchasedResponse.class)))
                .thenReturn(jobDetailPurchasedResponse);

        when(mockCompanyService.findById(0L)).thenReturn(Optional.empty());
        when(mockFieldsService.getById(0L)).thenReturn(new Fields(0L, "fieldName", "status"));
        
        final Optional<VietnamCountry> vietnamCountry = Optional.of(new VietnamCountry(0L, "country"));
        when(mockCountryService.findById(0L)).thenReturn(vietnamCountry);
        
        final DetailPurchasedPackageResponse result = recruiterManageServiceImplUnderTest.getDetailPurchasedPackage(0L,
                0L);
    }

    @Test
    public void testGetDetailPurchasedPackage_CountryServiceReturnsAbsent() {
        final Payment payment = payment();
        when(mockPaymentService.findById(0L)).thenReturn(payment);
        final Banner banner = banner();
        when(mockBannerService.findById(0L)).thenReturn(banner);
        final DetailPackage detailPackage = new DetailPackage(0L, 0L, "detailName", 0L, 0L, "timeExpired",
                "description", false, false, false, 0);
        when(mockDetailPackageService.findById(0L)).thenReturn(detailPackage);
        final Optional<RentalPackage> optional = Optional.of(rentalPackage());
        when(mockRentalPackageService.findById(0L)).thenReturn(optional);

        when(mockBannerActiveService.getAllBannerByPaymentId(0L)).thenReturn(Arrays.asList());
        final Job job = job();
        when(mockJobService.getJobById(0L)).thenReturn(job);
        final JobDetailPurchasedResponse jobDetailPurchasedResponse = new JobDetailPurchasedResponse(0L, "companyName",
                "jobName", "workPlace", "workForm", LocalDateTime.now(),
                LocalDateTime.now(), 0L, 0L, 0L, "rank", "experience", false, "jobDescription",
                "jobRequirement", "benefit", "fieldName", false, false, false, 0L, "weekday", "country", "flag");
        when(mockModelMapper.map(any(Object.class), eq(JobDetailPurchasedResponse.class)))
                .thenReturn(jobDetailPurchasedResponse);
        
        final Company company1 = company();
        final Optional<Company> company = Optional.of(company1);
        when(mockCompanyService.findById(0L)).thenReturn(company);

        when(mockFieldsService.getById(0L)).thenReturn(new Fields(0L, "fieldName", "status"));
        when(mockCountryService.findById(0L)).thenReturn(Optional.empty());

        final DetailPurchasedPackageResponse result = recruiterManageServiceImplUnderTest.getDetailPurchasedPackage(0L,
                0L);
    }
    private ProfileViewer profileViewer(){
        ProfileViewer viewer = new ProfileViewer();
        viewer.setId(0L);
        viewer.setCvId(0L);
        viewer.setViewerId(0L);
        viewer.setCandidateId(0L);
        return viewer;
    }
    @Test
    public void testGetCvWithPay() {
        final Optional<Recruiter> recruiter = Optional.of(recruiter());
        when(mockRecruiterService.findById(0L)).thenReturn(recruiter);
        final Optional<CV> cv = Optional.of(cv());
        when(mockCvService.findCvById(0L)).thenReturn(cv);
        final Optional<Candidate> candidate = Optional.of(candidate());
        when(mockCandidateService.findById(0L)).thenReturn(candidate);
        final List<Certificate> certificates = Arrays.asList(certificate);
        when(mockCertificateService.getListCertificateByCvId(0L)).thenReturn(certificates);
        final List<Education> educationList = Arrays.asList(education);
        when(mockEducationService.getListEducationByCvId(0L)).thenReturn(educationList);
        final List<Language> list = Arrays.asList(language);
        when(mockLanguageService.getListLanguageByCvId(0L)).thenReturn(list);
        final List<MajorLevel> majorLevels = Arrays.asList(majorLevel);
        when(mockMajorLevelService.getListMajorLevelByCvId(0L)).thenReturn(majorLevels);
        final List<OtherSkill> otherSkills = Arrays.asList(otherSkill);
        when(mockOtherSkillService.getListOtherSkillByCvId(0L)).thenReturn(otherSkills);
        final List<WorkExperience> workExperiences = Arrays.asList(workExperience);
        when(mockWorkExperienceService.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);
        final Optional<Users> optional = Optional.of(users());
        when(mockUserService.findByIdOp(0L)).thenReturn(optional);

        final ProfileViewer viewer = profileViewer();
        final Optional<ProfileViewer> optional1 = Optional.of(viewer);
        when(mockProfileViewerService.getByCvIdAndViewerIdOptional(0L, 0L)).thenReturn(optional1);

        final Optional<CV> cv1 = Optional.of(cv());
        when(mockCvService.findByIdAndCandidateId(0L, 0L)).thenReturn(cv1);

        final Recruiter recruiter1 =   recruiter();
        when(mockRecruiterService.getRecruiterById(0L)).thenReturn(recruiter1);
        final ProfileViewer viewer1 = new ProfileViewer();
        viewer1.setId(0L);
        viewer1.setCvId(0L);
        viewer1.setViewerId(0L);
        viewer1.setCandidateId(0L);
        when(mockProfileViewerService.getByCvIdAndViewerId(0L, 0L)).thenReturn(viewer1);
        
        final ProfileViewer viewer2 = new ProfileViewer();
        viewer2.setId(0L);
        viewer2.setCvId(0L);
        viewer2.setViewerId(0L);
        viewer2.setCandidateId(0L);
        when(mockProfileViewerRepository.save(any(ProfileViewer.class))).thenReturn(viewer2);
        
        final ViewCVWithPayResponse result = recruiterManageServiceImplUnderTest.getCvWithPay(0L, 0L);
        verify(mockRecruiterService).updateTotalCvView(0L, 0L);
        verify(mockProfileViewerRepository).save(any(ProfileViewer.class));
    }

    @Test
    public void testGetCvWithPay_RecruiterServiceFindByIdReturnsAbsent() {
        when(mockRecruiterService.findById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> recruiterManageServiceImplUnderTest.getCvWithPay(0L, 0L))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testGetCvWithPay_CVServiceFindCvByIdReturnsAbsent() {
        final Optional<Recruiter> recruiter = Optional.of(recruiter());
        when(mockRecruiterService.findById(0L)).thenReturn(recruiter);

        when(mockCvService.findCvById(0L)).thenReturn(Optional.empty());
        
        assertThatThrownBy(() -> recruiterManageServiceImplUnderTest.getCvWithPay(0L, 0L))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testGetCvWithPay_CandidateServiceReturnsAbsent() {
        final Optional<Recruiter> recruiter = Optional.of(recruiter());
        when(mockRecruiterService.findById(0L)).thenReturn(recruiter);
        
        final Optional<CV> cv = Optional.of(cv());
        when(mockCvService.findCvById(0L)).thenReturn(cv);

        when(mockCandidateService.findById(0L)).thenReturn(Optional.empty());
        
        assertThatThrownBy(() -> recruiterManageServiceImplUnderTest.getCvWithPay(0L, 0L))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testGetCvWithPay_CertificateServiceReturnsNoItems() {
        final Optional<Recruiter> recruiter = Optional.of(recruiter());
        when(mockRecruiterService.findById(0L)).thenReturn(recruiter);
        
        final Optional<CV> cv = Optional.of(cv());
        when(mockCvService.findCvById(0L)).thenReturn(cv);
        
        final Optional<Candidate> candidate = Optional.of(candidate());
        when(mockCandidateService.findById(0L)).thenReturn(candidate);

        when(mockCertificateService.getListCertificateByCvId(0L)).thenReturn(Collections.emptyList());
        
        final List<Education> educationList = Arrays.asList(education);
        when(mockEducationService.getListEducationByCvId(0L)).thenReturn(educationList);
        final List<Language> list = Arrays.asList(language);
        when(mockLanguageService.getListLanguageByCvId(0L)).thenReturn(list);
        final List<MajorLevel> majorLevels = Arrays.asList(majorLevel);
        when(mockMajorLevelService.getListMajorLevelByCvId(0L)).thenReturn(majorLevels);

        final List<OtherSkill> otherSkills = Arrays.asList(otherSkill);
        when(mockOtherSkillService.getListOtherSkillByCvId(0L)).thenReturn(otherSkills);
        final List<WorkExperience> workExperiences = Arrays.asList(workExperience);
        when(mockWorkExperienceService.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);

        final Optional<Users> optional = Optional.of(users());
        when(mockUserService.findByIdOp(0L)).thenReturn(optional);

        final ProfileViewer viewer = profileViewer();
        final Optional<ProfileViewer> optional1 = Optional.of(viewer);
        when(mockProfileViewerService.getByCvIdAndViewerIdOptional(0L, 0L)).thenReturn(optional1);

        final Optional<CV> cv1 = Optional.of(cv());
        when(mockCvService.findByIdAndCandidateId(0L, 0L)).thenReturn(cv1);

        final Recruiter recruiter1 =   recruiter();
        when(mockRecruiterService.getRecruiterById(0L)).thenReturn(recruiter1);

        final ProfileViewer viewer1 = new ProfileViewer();
        viewer1.setId(0L);
        viewer1.setCvId(0L);
        viewer1.setViewerId(0L);
        viewer1.setCandidateId(0L);
        when(mockProfileViewerService.getByCvIdAndViewerId(0L, 0L)).thenReturn(viewer1);

        final ProfileViewer viewer2 = new ProfileViewer();
        viewer2.setId(0L);
        viewer2.setCvId(0L);
        viewer2.setViewerId(0L);
        viewer2.setCandidateId(0L);
        when(mockProfileViewerRepository.save(any(ProfileViewer.class))).thenReturn(viewer2);
        
        final ViewCVWithPayResponse result = recruiterManageServiceImplUnderTest.getCvWithPay(0L, 0L);
        
        verify(mockRecruiterService).updateTotalCvView(0L, 0L);
        verify(mockProfileViewerRepository).save(any(ProfileViewer.class));
    }

    @Test
    public void testGetCvWithPay_EducationServiceReturnsNoItems() {
        final Optional<Recruiter> recruiter = Optional.of(recruiter());
        when(mockRecruiterService.findById(0L)).thenReturn(recruiter);
        
        final Optional<CV> cv = Optional.of(cv());
        when(mockCvService.findCvById(0L)).thenReturn(cv);
        
        final Optional<Candidate> candidate = Optional.of(candidate());
        when(mockCandidateService.findById(0L)).thenReturn(candidate);

        final List<Certificate> certificates = Arrays.asList(certificate);
        when(mockCertificateService.getListCertificateByCvId(0L)).thenReturn(certificates);

        when(mockEducationService.getListEducationByCvId(0L)).thenReturn(Collections.emptyList());

        final List<Language> list = Arrays.asList(language);
        when(mockLanguageService.getListLanguageByCvId(0L)).thenReturn(list);

        final List<MajorLevel> majorLevels = Arrays.asList(majorLevel);
        when(mockMajorLevelService.getListMajorLevelByCvId(0L)).thenReturn(majorLevels);

        final List<OtherSkill> otherSkills = Arrays.asList(otherSkill);
        when(mockOtherSkillService.getListOtherSkillByCvId(0L)).thenReturn(otherSkills);

        final List<WorkExperience> workExperiences = Arrays.asList(workExperience);
        when(mockWorkExperienceService.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);

        final Optional<Users> optional = Optional.of(users());
        when(mockUserService.findByIdOp(0L)).thenReturn(optional);

        final ProfileViewer viewer = profileViewer();
        final Optional<ProfileViewer> optional1 = Optional.of(viewer);
        when(mockProfileViewerService.getByCvIdAndViewerIdOptional(0L, 0L)).thenReturn(optional1);

        final Optional<CV> cv1 = Optional.of(cv());
        when(mockCvService.findByIdAndCandidateId(0L, 0L)).thenReturn(cv1);

        final Recruiter recruiter1 =   recruiter();
        when(mockRecruiterService.getRecruiterById(0L)).thenReturn(recruiter1);

        final ProfileViewer viewer1 = new ProfileViewer();
        viewer1.setId(0L);
        viewer1.setCvId(0L);
        viewer1.setViewerId(0L);
        viewer1.setCandidateId(0L);
        when(mockProfileViewerService.getByCvIdAndViewerId(0L, 0L)).thenReturn(viewer1);

        final ProfileViewer viewer2 = new ProfileViewer();
        viewer2.setId(0L);
        viewer2.setCvId(0L);
        viewer2.setViewerId(0L);
        viewer2.setCandidateId(0L);
        when(mockProfileViewerRepository.save(any(ProfileViewer.class))).thenReturn(viewer2);

        final ViewCVWithPayResponse result = recruiterManageServiceImplUnderTest.getCvWithPay(0L, 0L);

        verify(mockRecruiterService).updateTotalCvView(0L, 0L);
        verify(mockProfileViewerRepository).save(any(ProfileViewer.class));
    }

    @Test
    public void testGetCvWithPay_LanguageServiceReturnsNoItems() {

        final Optional<Recruiter> recruiter = Optional.of(recruiter());
        when(mockRecruiterService.findById(0L)).thenReturn(recruiter);

        final Optional<CV> cv = Optional.of(cv());
        when(mockCvService.findCvById(0L)).thenReturn(cv);

        final Optional<Candidate> candidate = Optional.of(candidate());
        when(mockCandidateService.findById(0L)).thenReturn(candidate);

        final List<Certificate> certificates = Arrays.asList(certificate);
        when(mockCertificateService.getListCertificateByCvId(0L)).thenReturn(certificates);

        final List<Education> educationList = Arrays.asList(education);
        when(mockEducationService.getListEducationByCvId(0L)).thenReturn(educationList);

        when(mockLanguageService.getListLanguageByCvId(0L)).thenReturn(Collections.emptyList());
        final List<MajorLevel> majorLevels = Arrays.asList(majorLevel);
        when(mockMajorLevelService.getListMajorLevelByCvId(0L)).thenReturn(majorLevels);
        final List<OtherSkill> otherSkills = Arrays.asList(otherSkill);
        when(mockOtherSkillService.getListOtherSkillByCvId(0L)).thenReturn(otherSkills);
        final List<WorkExperience> workExperiences = Arrays.asList(workExperience);
        when(mockWorkExperienceService.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);
        
        final Optional<Users> optional = Optional.of(users());
        when(mockUserService.findByIdOp(0L)).thenReturn(optional);
        
        final ProfileViewer viewer = profileViewer();
        final Optional<ProfileViewer> optional1 = Optional.of(viewer);
        when(mockProfileViewerService.getByCvIdAndViewerIdOptional(0L, 0L)).thenReturn(optional1);
        
        final Optional<CV> cv1 = Optional.of(cv());
        when(mockCvService.findByIdAndCandidateId(0L, 0L)).thenReturn(cv1);
        
        final Recruiter recruiter1 =   recruiter();
        when(mockRecruiterService.getRecruiterById(0L)).thenReturn(recruiter1);

        final ProfileViewer viewer1 = new ProfileViewer();
        viewer1.setId(0L);
        viewer1.setCvId(0L);
        viewer1.setViewerId(0L);
        viewer1.setCandidateId(0L);
        when(mockProfileViewerService.getByCvIdAndViewerId(0L, 0L)).thenReturn(viewer1);

        final ProfileViewer viewer2 = new ProfileViewer();
        viewer2.setId(0L);
        viewer2.setCvId(0L);
        viewer2.setViewerId(0L);
        viewer2.setCandidateId(0L);
        when(mockProfileViewerRepository.save(any(ProfileViewer.class))).thenReturn(viewer2);
        
        final ViewCVWithPayResponse result = recruiterManageServiceImplUnderTest.getCvWithPay(0L, 0L);

        verify(mockRecruiterService).updateTotalCvView(0L, 0L);
        verify(mockProfileViewerRepository).save(any(ProfileViewer.class));
    }

    @Test
    public void testGetCvWithPay_MajorLevelServiceReturnsNoItems() {

        final Optional<Recruiter> recruiter = Optional.of(recruiter());
        when(mockRecruiterService.findById(0L)).thenReturn(recruiter);

        final Optional<CV> cv = Optional.of(cv());
        when(mockCvService.findCvById(0L)).thenReturn(cv);

        final Optional<Candidate> candidate = Optional.of(candidate());
        when(mockCandidateService.findById(0L)).thenReturn(candidate);

        final List<Certificate> certificates = Arrays.asList(certificate);
        when(mockCertificateService.getListCertificateByCvId(0L)).thenReturn(certificates);

        final List<Education> educationList = Arrays.asList(education);
        when(mockEducationService.getListEducationByCvId(0L)).thenReturn(educationList);

        final List<Language> list = Arrays.asList(language);
        when(mockLanguageService.getListLanguageByCvId(0L)).thenReturn(list);

        when(mockMajorLevelService.getListMajorLevelByCvId(0L)).thenReturn(Collections.emptyList());

        final List<OtherSkill> otherSkills = Arrays.asList(otherSkill);
        when(mockOtherSkillService.getListOtherSkillByCvId(0L)).thenReturn(otherSkills);
        final List<WorkExperience> workExperiences = Arrays.asList(workExperience);
        when(mockWorkExperienceService.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);
        final Optional<Users> optional = Optional.of(users());
        when(mockUserService.findByIdOp(0L)).thenReturn(optional);
        final ProfileViewer viewer = profileViewer();
        final Optional<ProfileViewer> optional1 = Optional.of(viewer);
        when(mockProfileViewerService.getByCvIdAndViewerIdOptional(0L, 0L)).thenReturn(optional1);
        final Optional<CV> cv1 = Optional.of(cv());
        when(mockCvService.findByIdAndCandidateId(0L, 0L)).thenReturn(cv1);
        final Recruiter recruiter1 =   recruiter();
        when(mockRecruiterService.getRecruiterById(0L)).thenReturn(recruiter1);
        final ProfileViewer viewer1 = new ProfileViewer();
        viewer1.setId(0L);
        viewer1.setCvId(0L);
        viewer1.setViewerId(0L);
        viewer1.setCandidateId(0L);
        when(mockProfileViewerService.getByCvIdAndViewerId(0L, 0L)).thenReturn(viewer1);
        final ProfileViewer viewer2 = new ProfileViewer();
        viewer2.setId(0L);
        viewer2.setCvId(0L);
        viewer2.setViewerId(0L);
        viewer2.setCandidateId(0L);
        when(mockProfileViewerRepository.save(any(ProfileViewer.class))).thenReturn(viewer2);
        final ViewCVWithPayResponse result = recruiterManageServiceImplUnderTest.getCvWithPay(0L, 0L);
        verify(mockRecruiterService).updateTotalCvView(0L, 0L);
        verify(mockProfileViewerRepository).save(any(ProfileViewer.class));
    }

    @Test
    public void testGetCvWithPay_OtherSkillServiceReturnsNoItems() {
        final Optional<Recruiter> recruiter = Optional.of(recruiter());
        when(mockRecruiterService.findById(0L)).thenReturn(recruiter);
        final Optional<CV> cv = Optional.of(cv());
        when(mockCvService.findCvById(0L)).thenReturn(cv);
        final Optional<Candidate> candidate = Optional.of(candidate());
        when(mockCandidateService.findById(0L)).thenReturn(candidate);

        final List<Certificate> certificates = Arrays.asList(certificate);
        when(mockCertificateService.getListCertificateByCvId(0L)).thenReturn(certificates);

        final List<Education> educationList = Arrays.asList(education);
        when(mockEducationService.getListEducationByCvId(0L)).thenReturn(educationList);

        final List<Language> list = Arrays.asList(language);
        when(mockLanguageService.getListLanguageByCvId(0L)).thenReturn(list);

        final List<MajorLevel> majorLevels = Arrays.asList(majorLevel);
        when(mockMajorLevelService.getListMajorLevelByCvId(0L)).thenReturn(majorLevels);
        when(mockOtherSkillService.getListOtherSkillByCvId(0L)).thenReturn(Collections.emptyList());

        final List<WorkExperience> workExperiences = Arrays.asList(workExperience);
        when(mockWorkExperienceService.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);
        final Optional<Users> optional = Optional.of(users());
        when(mockUserService.findByIdOp(0L)).thenReturn(optional);

        final ProfileViewer viewer = profileViewer();
        final Optional<ProfileViewer> optional1 = Optional.of(viewer);
        when(mockProfileViewerService.getByCvIdAndViewerIdOptional(0L, 0L)).thenReturn(optional1);

        final Optional<CV> cv1 = Optional.of(cv());
        when(mockCvService.findByIdAndCandidateId(0L, 0L)).thenReturn(cv1);

        final Recruiter recruiter1 =   recruiter();
        when(mockRecruiterService.getRecruiterById(0L)).thenReturn(recruiter1);

        final ProfileViewer viewer1 = new ProfileViewer();
        viewer1.setId(0L);
        viewer1.setCvId(0L);
        viewer1.setViewerId(0L);
        viewer1.setCandidateId(0L);
        when(mockProfileViewerService.getByCvIdAndViewerId(0L, 0L)).thenReturn(viewer1);

        final ProfileViewer viewer2 = new ProfileViewer();
        viewer2.setId(0L);
        viewer2.setCvId(0L);
        viewer2.setViewerId(0L);
        viewer2.setCandidateId(0L);
        when(mockProfileViewerRepository.save(any(ProfileViewer.class))).thenReturn(viewer2);

        final ViewCVWithPayResponse result = recruiterManageServiceImplUnderTest.getCvWithPay(0L, 0L);

        verify(mockRecruiterService).updateTotalCvView(0L, 0L);
        verify(mockProfileViewerRepository).save(any(ProfileViewer.class));
    }
    private Education education = new Education(0L, 0L, "school", "major", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0), false, "description");
    @Test
    public void testGetCvWithPay_WorkExperienceServiceReturnsNoItems() {
        final Optional<Recruiter> recruiter = Optional.of(recruiter());
        when(mockRecruiterService.findById(0L)).thenReturn(recruiter);
        final Optional<CV> cv = Optional.of(cv());
        when(mockCvService.findCvById(0L)).thenReturn(cv);
        final Optional<Candidate> candidate = Optional.of(candidate());
        when(mockCandidateService.findById(0L)).thenReturn(candidate);
        final List<Certificate> certificates = Arrays.asList(certificate);
        when(mockCertificateService.getListCertificateByCvId(0L)).thenReturn(certificates);
        final List<Education> educationList = Arrays.asList(education);
        when(mockEducationService.getListEducationByCvId(0L)).thenReturn(educationList);
        final List<Language> list = Arrays.asList(language);
        when(mockLanguageService.getListLanguageByCvId(0L)).thenReturn(list);
        final List<MajorLevel> majorLevels = Arrays.asList(majorLevel);
        when(mockMajorLevelService.getListMajorLevelByCvId(0L)).thenReturn(majorLevels);
        
        final List<OtherSkill> otherSkills = Arrays.asList(otherSkill);
        when(mockOtherSkillService.getListOtherSkillByCvId(0L)).thenReturn(otherSkills);

        when(mockWorkExperienceService.getListWorkExperienceByCvId(0L)).thenReturn(Collections.emptyList());

        final Optional<Users> optional = Optional.of(users());
        when(mockUserService.findByIdOp(0L)).thenReturn(optional);
        
        final ProfileViewer viewer = profileViewer();
        final Optional<ProfileViewer> optional1 = Optional.of(viewer);
        when(mockProfileViewerService.getByCvIdAndViewerIdOptional(0L, 0L)).thenReturn(optional1);
        
        final Optional<CV> cv1 = Optional.of(cv());
        when(mockCvService.findByIdAndCandidateId(0L, 0L)).thenReturn(cv1);
        final Recruiter recruiter1 =   recruiter();
        when(mockRecruiterService.getRecruiterById(0L)).thenReturn(recruiter1);

        final ProfileViewer viewer1 = new ProfileViewer();
        viewer1.setId(0L);
        viewer1.setCvId(0L);
        viewer1.setViewerId(0L);
        viewer1.setCandidateId(0L);
        when(mockProfileViewerService.getByCvIdAndViewerId(0L, 0L)).thenReturn(viewer1);
        
        final ProfileViewer viewer2 = new ProfileViewer();
        viewer2.setId(0L);
        viewer2.setCvId(0L);
        viewer2.setViewerId(0L);
        viewer2.setCandidateId(0L);
        when(mockProfileViewerRepository.save(any(ProfileViewer.class))).thenReturn(viewer2);
        
        final ViewCVWithPayResponse result = recruiterManageServiceImplUnderTest.getCvWithPay(0L, 0L);
        verify(mockRecruiterService).updateTotalCvView(0L, 0L);
        verify(mockProfileViewerRepository).save(any(ProfileViewer.class));
    }
    private Certificate certificate = new Certificate(0L, "certificateName", "certificateUrl", 0L, 0L);
    private Language language = new Language(0L, "language", "level", 0L);
    private MajorLevel majorLevel = new MajorLevel(0L, 0L, 0L, 0L, "level", false);
    private OtherSkill otherSkill = new OtherSkill(0L, "skillName", 0L, "level");
    private WorkExperience workExperience = new WorkExperience(0L, 0L, "companyName", "position", LocalDateTime.of(2020, 1, 1, 0, 0, 0),
            LocalDateTime.of(2020, 1, 1, 0, 0, 0), "description", false);
    @Test 
    public void testGetCvWithPay_UserServiceReturnsAbsent() {
        final Optional<Recruiter> recruiter = Optional.of(recruiter());
        when(mockRecruiterService.findById(0L)).thenReturn(recruiter);
        final Optional<CV> cv = Optional.of(cv());
        when(mockCvService.findCvById(0L)).thenReturn(cv);
        final Optional<Candidate> candidate = Optional.of(candidate());
        when(mockCandidateService.findById(0L)).thenReturn(candidate);
        final List<Certificate> certificates = Arrays.asList(certificate);
        when(mockCertificateService.getListCertificateByCvId(0L)).thenReturn(certificates);
        final List<Education> educationList = Arrays.asList(education);
        when(mockEducationService.getListEducationByCvId(0L)).thenReturn(educationList);
        final List<Language> list = Arrays.asList(language);
        when(mockLanguageService.getListLanguageByCvId(0L)).thenReturn(list);
        
        final List<MajorLevel> majorLevels = Arrays.asList(majorLevel);
        when(mockMajorLevelService.getListMajorLevelByCvId(0L)).thenReturn(majorLevels);
        
        final List<OtherSkill> otherSkills = Arrays.asList(otherSkill);
        when(mockOtherSkillService.getListOtherSkillByCvId(0L)).thenReturn(otherSkills);
        final List<WorkExperience> workExperiences = Arrays.asList(workExperience);
        when(mockWorkExperienceService.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);

        when(mockUserService.findByIdOp(0L)).thenReturn(Optional.empty());
        final ProfileViewer viewer = profileViewer();
        final Optional<ProfileViewer> optional = Optional.of(viewer);
        when(mockProfileViewerService.getByCvIdAndViewerIdOptional(0L, 0L)).thenReturn(optional);

        final Optional<CV> cv1 = Optional.of(cv());
        when(mockCvService.findByIdAndCandidateId(0L, 0L)).thenReturn(cv1);

        final Recruiter recruiter1 =   recruiter();
        when(mockRecruiterService.getRecruiterById(0L)).thenReturn(recruiter1);
        final ProfileViewer viewer1 = new ProfileViewer();
        viewer1.setId(0L);
        viewer1.setCvId(0L);
        viewer1.setViewerId(0L);
        viewer1.setCandidateId(0L);
        when(mockProfileViewerService.getByCvIdAndViewerId(0L, 0L)).thenReturn(viewer1);
        final ProfileViewer viewer2 = new ProfileViewer();
        viewer2.setId(0L);
        viewer2.setCvId(0L);
        viewer2.setViewerId(0L);
        viewer2.setCandidateId(0L);
        when(mockProfileViewerRepository.save(any(ProfileViewer.class))).thenReturn(viewer2);
        final ViewCVWithPayResponse result = recruiterManageServiceImplUnderTest.getCvWithPay(0L, 0L);
        verify(mockRecruiterService).updateTotalCvView(0L, 0L);
        verify(mockProfileViewerRepository).save(any(ProfileViewer.class));
    }

    @Test
    public void testGetCvWithPay_ProfileViewerServiceGetByCvIdAndViewerIdOptionalReturnsAbsent() {
        final Optional<Recruiter> recruiter = Optional.of(recruiter());
        when(mockRecruiterService.findById(0L)).thenReturn(recruiter);
        final Optional<CV> cv = Optional.of(cv());
        when(mockCvService.findCvById(0L)).thenReturn(cv);
        final Optional<Candidate> candidate = Optional.of(candidate());
        when(mockCandidateService.findById(0L)).thenReturn(candidate);
        final List<Certificate> certificates = Arrays.asList(certificate);
        when(mockCertificateService.getListCertificateByCvId(0L)).thenReturn(certificates);

        final List<Education> educationList = Arrays.asList(education);
        when(mockEducationService.getListEducationByCvId(0L)).thenReturn(educationList);

        final List<Language> list = Arrays.asList(language);
        when(mockLanguageService.getListLanguageByCvId(0L)).thenReturn(list);

        final List<MajorLevel> majorLevels = Arrays.asList(majorLevel);
        when(mockMajorLevelService.getListMajorLevelByCvId(0L)).thenReturn(majorLevels);

        final List<OtherSkill> otherSkills = Arrays.asList(otherSkill);
        when(mockOtherSkillService.getListOtherSkillByCvId(0L)).thenReturn(otherSkills);

        final List<WorkExperience> workExperiences = Arrays.asList(workExperience);
        when(mockWorkExperienceService.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);

        final Optional<Users> optional = Optional.of(users());
        when(mockUserService.findByIdOp(0L)).thenReturn(optional);

        when(mockProfileViewerService.getByCvIdAndViewerIdOptional(0L, 0L)).thenReturn(Optional.empty());

        final Optional<CV> cv1 = Optional.of(cv());
        when(mockCvService.findByIdAndCandidateId(0L, 0L)).thenReturn(cv1);

        final Recruiter recruiter1 =   recruiter();
        when(mockRecruiterService.getRecruiterById(0L)).thenReturn(recruiter1);

        final ProfileViewer viewer = profileViewer();
        when(mockProfileViewerService.getByCvIdAndViewerId(0L, 0L)).thenReturn(viewer);

        final ProfileViewer viewer1 = new ProfileViewer();
        viewer1.setId(0L);
        viewer1.setCvId(0L);
        viewer1.setViewerId(0L);
        viewer1.setCandidateId(0L);
        when(mockProfileViewerRepository.save(any(ProfileViewer.class))).thenReturn(viewer1);
        final ViewCVWithPayResponse result = recruiterManageServiceImplUnderTest.getCvWithPay(0L, 0L);
        verify(mockRecruiterService).updateTotalCvView(0L, 0L);
        verify(mockProfileViewerRepository).save(any(ProfileViewer.class));
    }

    @Test
    public void testGetCvWithPay_CVServiceFindByIdAndCandidateIdReturnsAbsent() {
        final Optional<Recruiter> recruiter = Optional.of(recruiter());
        when(mockRecruiterService.findById(0L)).thenReturn(recruiter);

        final Optional<CV> cv = Optional.of(cv());
        when(mockCvService.findCvById(0L)).thenReturn(cv);
        final Optional<Candidate> candidate = Optional.of(candidate());
        when(mockCandidateService.findById(0L)).thenReturn(candidate);
        final List<Certificate> certificates = Arrays.asList(certificate);
        when(mockCertificateService.getListCertificateByCvId(0L)).thenReturn(certificates);
        final List<Education> educationList = Arrays.asList(education);
        when(mockEducationService.getListEducationByCvId(0L)).thenReturn(educationList);
        final List<Language> list = Arrays.asList(language);
        when(mockLanguageService.getListLanguageByCvId(0L)).thenReturn(list);
        final List<MajorLevel> majorLevels = Arrays.asList(majorLevel);
        when(mockMajorLevelService.getListMajorLevelByCvId(0L)).thenReturn(majorLevels);
        final List<OtherSkill> otherSkills = Arrays.asList(otherSkill);
        when(mockOtherSkillService.getListOtherSkillByCvId(0L)).thenReturn(otherSkills);
        final List<WorkExperience> workExperiences = Arrays.asList(workExperience);
        when(mockWorkExperienceService.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);

        final Optional<Users> optional = Optional.of(users());
        when(mockUserService.findByIdOp(0L)).thenReturn(optional);

        final ProfileViewer viewer = profileViewer();
        final Optional<ProfileViewer> optional1 = Optional.of(viewer);
        when(mockProfileViewerService.getByCvIdAndViewerIdOptional(0L, 0L)).thenReturn(optional1);
        when(mockCvService.findByIdAndCandidateId(0L, 0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recruiterManageServiceImplUnderTest.getCvWithPay(0L, 0L))
                .isInstanceOf(HiveConnectException.class);
        verify(mockRecruiterService).updateTotalCvView(0L, 0L);
    }

    @Test
    public void testGetCvWithPay_RecruiterServiceGetRecruiterByIdReturnsNull() {
        final Optional<Recruiter> recruiter = Optional.of(recruiter());
        when(mockRecruiterService.findById(0L)).thenReturn(recruiter);
        final Optional<CV> cv = Optional.of(cv());
        when(mockCvService.findCvById(0L)).thenReturn(cv);

        final Optional<Candidate> candidate = Optional.of(candidate());
        when(mockCandidateService.findById(0L)).thenReturn(candidate);

        final List<Certificate> certificates = Arrays.asList(certificate);
        when(mockCertificateService.getListCertificateByCvId(0L)).thenReturn(certificates);

        final List<Education> educationList = Arrays.asList(education);
        when(mockEducationService.getListEducationByCvId(0L)).thenReturn(educationList);

        final List<Language> list = Arrays.asList(language);
        when(mockLanguageService.getListLanguageByCvId(0L)).thenReturn(list);

        final List<MajorLevel> majorLevels = Arrays.asList(majorLevel);
        when(mockMajorLevelService.getListMajorLevelByCvId(0L)).thenReturn(majorLevels);

        final List<OtherSkill> otherSkills = Arrays.asList(otherSkill);
        when(mockOtherSkillService.getListOtherSkillByCvId(0L)).thenReturn(otherSkills);

        final List<WorkExperience> workExperiences = Arrays.asList(workExperience);
        when(mockWorkExperienceService.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);

        final Optional<Users> optional = Optional.of(users());
        when(mockUserService.findByIdOp(0L)).thenReturn(optional);

        final ProfileViewer viewer = profileViewer();
        final Optional<ProfileViewer> optional1 = Optional.of(viewer);
        when(mockProfileViewerService.getByCvIdAndViewerIdOptional(0L, 0L)).thenReturn(optional1);

        final Optional<CV> cv1 = Optional.of(cv());
        when(mockCvService.findByIdAndCandidateId(0L, 0L)).thenReturn(cv1);
        when(mockRecruiterService.getRecruiterById(0L)).thenReturn(null);
        assertThatThrownBy(() -> recruiterManageServiceImplUnderTest.getCvWithPay(0L, 0L))
                .isInstanceOf(HiveConnectException.class);
        verify(mockRecruiterService).updateTotalCvView(0L, 0L);
    }

    @Test
    public void testGetCvWithPay_ProfileViewerServiceGetByCvIdAndViewerIdReturnsNull() {
        final Optional<Recruiter> recruiter = Optional.of(recruiter());
        when(mockRecruiterService.findById(0L)).thenReturn(recruiter);
        final Optional<CV> cv = Optional.of(cv());
        when(mockCvService.findCvById(0L)).thenReturn(cv);

        final Optional<Candidate> candidate = Optional.of(candidate());
        when(mockCandidateService.findById(0L)).thenReturn(candidate);

        final List<Certificate> certificates = Arrays.asList(certificate);
        when(mockCertificateService.getListCertificateByCvId(0L)).thenReturn(certificates);

        final List<Education> educationList = Arrays.asList(education);
        when(mockEducationService.getListEducationByCvId(0L)).thenReturn(educationList);

        final List<Language> list = Arrays.asList(language);
        when(mockLanguageService.getListLanguageByCvId(0L)).thenReturn(list);

        final List<MajorLevel> majorLevels = Arrays.asList(majorLevel);
        when(mockMajorLevelService.getListMajorLevelByCvId(0L)).thenReturn(majorLevels);

        final List<OtherSkill> otherSkills = Arrays.asList(otherSkill);
        when(mockOtherSkillService.getListOtherSkillByCvId(0L)).thenReturn(otherSkills);

        final List<WorkExperience> workExperiences = Arrays.asList(workExperience);
        when(mockWorkExperienceService.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);

        final Optional<Users> optional = Optional.of(users());
        when(mockUserService.findByIdOp(0L)).thenReturn(optional);
        final ProfileViewer viewer = profileViewer();
        final Optional<ProfileViewer> optional1 = Optional.of(viewer);
        when(mockProfileViewerService.getByCvIdAndViewerIdOptional(0L, 0L)).thenReturn(optional1);

        final Optional<CV> cv1 = Optional.of(cv());
        when(mockCvService.findByIdAndCandidateId(0L, 0L)).thenReturn(cv1);

        final Recruiter recruiter1 =   recruiter();
        when(mockRecruiterService.getRecruiterById(0L)).thenReturn(recruiter1);

        when(mockProfileViewerService.getByCvIdAndViewerId(0L, 0L)).thenReturn(null);
        final ProfileViewer viewer1 = new ProfileViewer();
        viewer1.setId(0L);
        viewer1.setCvId(0L);
        viewer1.setViewerId(0L);
        viewer1.setCandidateId(0L);
        when(mockProfileViewerRepository.save(any(ProfileViewer.class))).thenReturn(viewer1);

        final ViewCVWithPayResponse result = recruiterManageServiceImplUnderTest.getCvWithPay(0L, 0L);
        verify(mockRecruiterService).updateTotalCvView(0L, 0L);
        verify(mockProfileViewerRepository).save(any(ProfileViewer.class));
    }

    @Test
    public void testInsertWhoViewCv() {
        final ViewCvResponse response = new ViewCvResponse();
        response.setCvId(0L);
        response.setViewerId(0L);
        response.setCandidateId(0L);
        final Optional<CV> cv = Optional.of(cv());
        when(mockCvService.findByIdAndCandidateId(0L, 0L)).thenReturn(cv);
        
        final Recruiter recruiter =   recruiter();
        when(mockRecruiterService.getRecruiterById(0L)).thenReturn(recruiter);
        final ProfileViewer viewer = profileViewer();
        when(mockProfileViewerService.getByCvIdAndViewerId(0L, 0L)).thenReturn(viewer);
        final ProfileViewer viewer1 = new ProfileViewer();
        viewer1.setId(0L);
        viewer1.setCvId(0L);
        viewer1.setViewerId(0L);
        viewer1.setCandidateId(0L);
        when(mockProfileViewerRepository.save(any(ProfileViewer.class))).thenReturn(viewer1);
        recruiterManageServiceImplUnderTest.insertWhoViewCv(response);
        verify(mockProfileViewerRepository).save(any(ProfileViewer.class));
    }

    @Test
    public void testInsertWhoViewCv_CVServiceReturnsAbsent() {
        final ViewCvResponse response = new ViewCvResponse();
        response.setCvId(0L);
        response.setViewerId(0L);
        response.setCandidateId(0L);

        when(mockCvService.findByIdAndCandidateId(0L, 0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> recruiterManageServiceImplUnderTest.insertWhoViewCv(response))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testInsertWhoViewCv_RecruiterServiceReturnsNull() {
        final ViewCvResponse response = new ViewCvResponse();
        response.setCvId(0L);
        response.setViewerId(0L);
        response.setCandidateId(0L);
        final Optional<CV> cv = Optional.of(cv());
        when(mockCvService.findByIdAndCandidateId(0L, 0L)).thenReturn(cv);
        when(mockRecruiterService.getRecruiterById(0L)).thenReturn(null);

        assertThatThrownBy(() -> recruiterManageServiceImplUnderTest.insertWhoViewCv(response))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testInsertWhoViewCv_ProfileViewerServiceReturnsNull() {
        final ViewCvResponse response = new ViewCvResponse();
        response.setCvId(0L);
        response.setViewerId(0L);
        response.setCandidateId(0L);
        
        final Optional<CV> cv = Optional.of(cv());
        when(mockCvService.findByIdAndCandidateId(0L, 0L)).thenReturn(cv);
        
        final Recruiter recruiter =   recruiter();
        when(mockRecruiterService.getRecruiterById(0L)).thenReturn(recruiter);

        when(mockProfileViewerService.getByCvIdAndViewerId(0L, 0L)).thenReturn(null);
        final ProfileViewer viewer = profileViewer();
        when(mockProfileViewerRepository.save(any(ProfileViewer.class))).thenReturn(viewer);

        recruiterManageServiceImplUnderTest.insertWhoViewCv(response);
        verify(mockProfileViewerRepository).save(any(ProfileViewer.class));
    }

    @Test
    public void testGetCvListAppliedJob() {
        final Optional<Job> job = Optional.of(job());
        when(mockJobService.findById(0L)).thenReturn(job);

        final Page<AppliedJob> appliedJobs = new PageImpl<>(
                Arrays.asList(new AppliedJob(0L, 0L, 0L, false, "approvalStatus", false, "cvUploadUrl")));
        when(mockAppliedJobService.getCvAppliedJob(any(Pageable.class), eq(0L), eq(true))).thenReturn(appliedJobs);

        final Candidate candidate = candidate();
        when(mockCandidateService.getCandidateById(0L)).thenReturn(candidate);

        final CV cv = cv();
        when(mockCvRepository.getByCandidateId(0L)).thenReturn(cv);
        final List<WorkExperience> workExperiences = Arrays.asList(workExperience);
        when(mockWorkExperienceRepository.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);
        final List<Education> educationList = Arrays.asList(education);
        when(mockEducationRepository.getListEducationByCvId(0L)).thenReturn(educationList);

        final ResponseDataPagination result = recruiterManageServiceImplUnderTest.getCvListAppliedJob(0, 0, 0L);
    }

    @Test
    public void testGetCvListAppliedJob_JobServiceReturnsAbsent() {
        when(mockJobService.findById(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recruiterManageServiceImplUnderTest.getCvListAppliedJob(0, 0, 0L))
                .isInstanceOf(HiveConnectException.class);
    }

    @Test
    public void testGetCvListAppliedJob_AppliedJobServiceReturnsNoItems() {
        final Optional<Job> job = Optional.of(job());
        when(mockJobService.findById(0L)).thenReturn(job);

        when(mockAppliedJobService.getCvAppliedJob(any(Pageable.class), eq(0L), eq(true)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        final Candidate candidate = candidate();
        when(mockCandidateService.getCandidateById(0L)).thenReturn(candidate);

        final CV cv = cv();
        when(mockCvRepository.getByCandidateId(0L)).thenReturn(cv);

        final List<WorkExperience> workExperiences = Arrays.asList(workExperience);
        when(mockWorkExperienceRepository.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);
        final List<Education> educationList = Arrays.asList(education);
        when(mockEducationRepository.getListEducationByCvId(0L)).thenReturn(educationList);

        final ResponseDataPagination result = recruiterManageServiceImplUnderTest.getCvListAppliedJob(0, 0, 0L);
    }

    @Test
    public void testGetCvListAppliedJob_CVRepositoryReturnsNull() {
        final Optional<Job> job = Optional.of(job());
        when(mockJobService.findById(0L)).thenReturn(job);
        final Page<AppliedJob> appliedJobs = new PageImpl<>(
                Arrays.asList(new AppliedJob(0L, 0L, 0L, false, "approvalStatus", false, "cvUploadUrl")));
        when(mockAppliedJobService.getCvAppliedJob(any(Pageable.class), eq(0L), eq(true))).thenReturn(appliedJobs);
        final Candidate candidate = candidate();
        when(mockCandidateService.getCandidateById(0L)).thenReturn(candidate);
        when(mockCvRepository.getByCandidateId(0L)).thenReturn(null);
        final List<WorkExperience> workExperiences = Arrays.asList(workExperience);
        when(mockWorkExperienceRepository.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);
        final List<Education> educationList = Arrays.asList(education);
        when(mockEducationRepository.getListEducationByCvId(0L)).thenReturn(educationList);

        final ResponseDataPagination result = recruiterManageServiceImplUnderTest.getCvListAppliedJob(0, 0, 0L);
    }

    @Test
    public void testGetCvListAppliedJob_WorkExperienceRepositoryReturnsNoItems() {
        final Optional<Job> job = Optional.of(job());
        when(mockJobService.findById(0L)).thenReturn(job);
        final Page<AppliedJob> appliedJobs = new PageImpl<>(
                Arrays.asList(new AppliedJob(0L, 0L, 0L, false, "approvalStatus", false, "cvUploadUrl")));
        when(mockAppliedJobService.getCvAppliedJob(any(Pageable.class), eq(0L), eq(true))).thenReturn(appliedJobs);
        final Candidate candidate = candidate();
        when(mockCandidateService.getCandidateById(0L)).thenReturn(candidate);
        final CV cv = cv();
        when(mockCvRepository.getByCandidateId(0L)).thenReturn(cv);

        when(mockWorkExperienceRepository.getListWorkExperienceByCvId(0L)).thenReturn(Collections.emptyList());
        final List<Education> educationList = Arrays.asList(education);
        when(mockEducationRepository.getListEducationByCvId(0L)).thenReturn(educationList);

        final ResponseDataPagination result = recruiterManageServiceImplUnderTest.getCvListAppliedJob(0, 0, 0L);
    }

    @Test
    public void testGetCvListAppliedJob_EducationRepositoryReturnsNoItems() {
        final Optional<Job> job = Optional.of(job());
        when(mockJobService.findById(0L)).thenReturn(job);

        final Page<AppliedJob> appliedJobs = new PageImpl<>(
                Arrays.asList(new AppliedJob(0L, 0L, 0L, false, "approvalStatus", false, "cvUploadUrl")));
        when(mockAppliedJobService.getCvAppliedJob(any(Pageable.class), eq(0L), eq(true))).thenReturn(appliedJobs);
        final Candidate candidate =candidate();
        when(mockCandidateService.getCandidateById(0L)).thenReturn(candidate);

        final CV cv = cv();
        when(mockCvRepository.getByCandidateId(0L)).thenReturn(cv);

        final List<WorkExperience> workExperiences = Arrays.asList(workExperience);
        when(mockWorkExperienceRepository.getListWorkExperienceByCvId(0L)).thenReturn(workExperiences);
        when(mockEducationRepository.getListEducationByCvId(0L)).thenReturn(Collections.emptyList());
        final ResponseDataPagination result = recruiterManageServiceImplUnderTest.getCvListAppliedJob(0, 0, 0L);
    }
}
