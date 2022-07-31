package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.CV.FindCVResponse;
import fpt.edu.capstone.dto.CV.IFindCVResponse;
import fpt.edu.capstone.dto.admin.CommonRecruiterInformationResponse;
import fpt.edu.capstone.dto.banner.UploadBannerRequest;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.company.CompanyInformationResponse;
import fpt.edu.capstone.dto.job.JobForRecruiterResponse;
import fpt.edu.capstone.dto.recruiter.*;
import fpt.edu.capstone.entity.*;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.BannerActiveRepository;
import fpt.edu.capstone.repository.RecruiterRepository;
import fpt.edu.capstone.repository.UserRepository;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecruiterManageServiceImpl implements RecruiterManageService {

    private final ModelMapper modelMapper;

    private final RecruiterRepository recruiterRepository;

    private final UserRepository userRepository;

    private final JobService jobService;

    private final AppliedJobService appliedJobService;

    private final RecruiterService recruiterService;

    private final UserService userService;

    private final ImageServiceImpl imageService;

    private final CompanyService companyService;

    private final CVService cvService;

    private final CandidateService candidateService;

    private final WorkExperienceService workExperienceService;

    private final EducationService educationService;

    private final PaymentService paymentService;

    private final BannerService bannerService;

    private final BannerActiveRepository bannerActiveRepository;

    private final DetailPackageService detailPackageService;

    private final RentalPackageService rentalPackageService;

    private final BannerActiveService bannerActiveService;

    private final FieldsService fieldsService;

    private final CountryService countryService;

    @Override
    public CommonRecruiterInformationResponse getCommonInforOfRecruiter(long recruiterId) {
        CommonRecruiterInformationResponse response = new CommonRecruiterInformationResponse();
        Optional<Recruiter> optionalRecruiter = recruiterRepository.findById(recruiterId);
        if (!optionalRecruiter.isPresent()) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        int step = 0;
        int totalStep = 3;
        Users user = userRepository.getById(optionalRecruiter.get().getUserId());
        if (user == null) {
            throw new HiveConnectException("Liên hệ admin");
        }
        //verify step 1
        if (user.isVerifiedEmail()) {
            step++;
        }
        //verify step 2
        if (user.isVerifiedPhone()) {
            step++;
        }
        //verify step 3
        if (optionalRecruiter.get().getBusinessLicenseApprovalStatus() != null
                && optionalRecruiter.get().getBusinessLicenseApprovalStatus().equals(Enums.ApprovalStatus.APPROVED.getStatus())) {
            step++;
        }
        response.setRecruiterFullName(optionalRecruiter.get().getFullName());
        response.setVerifyStep(step + " / " + totalStep);

        CountTotalCreatedJobResponse countTotalCreatedJobResponse = jobService.countTotalCreatedJobOfRecruiter(recruiterId);
        long totalCreatedJob = 0;
        if (countTotalCreatedJobResponse != null) {
            totalCreatedJob = countTotalCreatedJobResponse.getTotalCreatedJob();
        }
        response.setTotalCreatedJob(totalCreatedJob);

        CountCandidateApplyPercentageResponse countApplyCandidate = appliedJobService.countApplyPercentage(recruiterId);
        String result = "0";
        if (countApplyCandidate != null) {
            long totalApply = countApplyCandidate.getTotalApplied();
            long numberRecruits = countApplyCandidate.getNumberRecruits();
            double applyPercentage = (double) totalApply / numberRecruits;
            DecimalFormat formatter = new DecimalFormat("##.##"); //
            formatter.setRoundingMode(RoundingMode.DOWN); // Towards zero
            result = formatter.format(applyPercentage);
        }
        response.setCandidateApplyPercentage(result);

        return response;
    }

    public ResponseDataPagination getRecruitersOfCompany(Integer pageNo, Integer pageSize, long companyId) {
        List<RecruiterBaseOnCompanyResponse> responseList = new ArrayList<>();
        Page<Recruiter> recruiters = recruiterService.getRecruiterByCompanyId(pageNo, pageSize, companyId);
        if (recruiters.hasContent()) {
            for (Recruiter recruiter : recruiters) {
                RecruiterBaseOnCompanyResponse response = new RecruiterBaseOnCompanyResponse();
                response.setRecruiterId(recruiter.getId());

                Users user = userService.getUserById(recruiter.getUserId());
                if (user == null) {
                    throw new HiveConnectException("Liên hệ admin");
                }
                response.setUserName(user.getUsername());

                Image image = imageService.getAvatarRecruiter(recruiter.getId());
                if (image != null) {
                    response.setAvatar(image.getUrl());
                }
                response.setFullName(recruiter.getFullName());
                response.setGender(recruiter.isGender());
                response.setPosition(recruiter.getPosition());
                response.setLinkedinAccount(recruiter.getLinkedinAccount());

                response.setEmail(user.getEmail());
                response.setPhone(user.getPhone());

                responseList.add(response);
            }
        }
        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(responseList);
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(recruiters.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(recruiters.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public RecruiterProfileResponse updateRecruiterInformation(long recruiterId, RecruiterUpdateProfileRequest request) throws Exception {
        Recruiter recruiter = recruiterService.getById(recruiterId);
        if (recruiter == null) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        if (request == null) {
            throw new HiveConnectException("Không có dữ liệu thay đổi, không thể cập nhật");
        }
        if ((request.getFullName() == null || request.getFullName().trim().isEmpty())
                || (request.getPhone() == null || request.getPhone().trim().isEmpty())
                || (request.getPosition() == null || request.getPosition().trim().isEmpty())
                || (request.getLinkedinAccount() == null || request.getLinkedinAccount().trim().isEmpty())) {
            throw new HiveConnectException("Vui lòng nhập thông tin bắt buộc.");
        }
        if (request.getAvatarUrl() != null && request.getAvatarUrl().trim().isEmpty()) {
            recruiter.setAvatarUrl(request.getAvatarUrl());
        }
        recruiter.setFullName(request.getFullName());
        recruiter.setPosition(request.getPhone());
        recruiter.setGender(request.isGender());
        recruiter.setPosition(request.getPosition());
        recruiter.setLinkedinAccount(request.getLinkedinAccount());
        recruiterRepository.save(recruiter);

        Users user = userRepository.getUserById(recruiter.getUserId());
        //recruiter tồn tại nhưng user không tồn tại (database lỗi)
        if (user == null) {
            throw new HiveConnectException("Liên hệ admin");
        }
        user.setPhone(request.getPhone());
        userRepository.save(user);
        RecruiterProfileResponse profileResponse = getRecruiterProfile(recruiter.getUserId());
        return profileResponse;
    }

    @Override
    public RecruiterProfileResponse getRecruiterProfile(long userId) {
        Users user = userService.getUserById(userId);
        //recruiter tồn tại nhưng user không tồn tại (database lỗi)
        if (user == null) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        RecruiterProfileResponse profileResponse = new RecruiterProfileResponse();
        Recruiter recruiter = recruiterRepository.getRecruiterByUserId(userId);
        if (recruiter == null) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        profileResponse.setRecruiterId(recruiter.getId());
        profileResponse.setUserName(user.getUsername());
        profileResponse.setEmail(user.getEmail());
        profileResponse.setPhone(user.getPhone());

        Image image = imageService.getAvatarRecruiter(recruiter.getId());
        if (image != null) {
            profileResponse.setAvatarName(image.getName());
            profileResponse.setAvatarUrl(image.getUrl());
        }

        Company company = companyService.getCompanyById(recruiter.getCompanyId());
        if (company != null) {
            profileResponse.setCompanyId(company.getId());
            profileResponse.setCompanyName(company.getName());
            profileResponse.setCompanyAddress(company.getAddress());
        }

        profileResponse.setFullName(recruiter.getFullName());
        profileResponse.setGender(recruiter.isGender());
        profileResponse.setPosition(recruiter.getPosition());
        profileResponse.setLinkedinAccount(recruiter.getLinkedinAccount());
        if (recruiter.getCompanyId() == 0) {
            profileResponse.setJoinedCompany(false);
        } else if (recruiter.getCompanyId() > 0) {
            profileResponse.setJoinedCompany(true);
        }
        if (recruiter.getBusinessLicenseApprovalStatus() != null
                && recruiter.getBusinessLicenseApprovalStatus().equals(Enums.ApprovalStatus.APPROVED.getStatus())) {
            profileResponse.setApprovedBusinessLicense(true);
        } else {
            profileResponse.setApprovedBusinessLicense(false);
        }
        return profileResponse;
    }

    @Override
    public CompanyInformationResponse getCompanyInformation(long companyId, long recruiterId) {
        CompanyInformationResponse response = new CompanyInformationResponse();
        Optional<Company> companyOp = companyService.findById(companyId);
        if (!companyOp.isPresent()) {
            throw new HiveConnectException("Thông tin công ty không tồn tại");
        }
        Company tmp = companyOp.get();
        response.setAddress(tmp.getAddress());
        Image image = imageService.getImageCompany(companyId, true);
        if (image != null) {
            response.setAvatar(image.getUrl());
        }
        response.setDescription(tmp.getDescription());
        response.setEmail(tmp.getEmail());
        response.setFieldWork(tmp.getFieldWork());
        response.setId(tmp.getId());
        response.setName(tmp.getName());
        response.setMapUrl(tmp.getMapUrl());
        response.setPhone(tmp.getPhone());
        response.setNumberEmployees(tmp.getNumberEmployees());
        response.setWebsite(tmp.getWebsite());
        response.setTaxCode(tmp.getTaxCode());
        long creatorId = tmp.getCreatorId();
        boolean isCreator = false;
        if (recruiterId != 0 && creatorId == recruiterId) {
            isCreator = true;
        }
        response.setCreator(isCreator);
        List<Image> companyImageList = imageService.getCompanyImageList(companyId, false, false);
        List<String> companyImageUrls = companyImageList.stream().map(Image::getUrl).collect(Collectors.toList());
        response.setCompanyImageUrlList(companyImageUrls);
        List<Image> coverImage = imageService.getCompanyImageList(companyId, false, true);
        if (coverImage != null && !coverImage.isEmpty()) {
            response.setCoverImageUrl(coverImage.get(0).getUrl());
        }
        return response;
    }

    @Override
    public ResponseDataPagination findCV(Integer pageNo, Integer pageSize, //int experienceOption,
                                         String candidateAddress,
                                         String techStack) {
        List<FindCVResponse> responseList = new ArrayList<>();
        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);

        Page<IFindCVResponse> cvList = cvService.findCVForRecruiter(pageable, //experienceOption,
                candidateAddress, techStack);
        for (IFindCVResponse iFindCVResponse : cvList) {
            FindCVResponse response = new FindCVResponse();
            Optional<CV> cv = cvService.findCvById(iFindCVResponse.getCvId());
            Candidate candidate = candidateService.getCandidateById(cv.get().getCandidateId());
            response.setCandidateName(candidate.getFullName());

            List<WorkExperience> workExperiences = workExperienceService.
                    getListWorkExperienceByCvId(iFindCVResponse.getCvId());
            List<String> workPositionExperiences = new ArrayList<>();
            for (WorkExperience workExperience : workExperiences) {
                workPositionExperiences.add(workExperience.getPosition() + " - " + workExperience.getCompanyName());
            }
            response.setWorkPositionExperiences(workPositionExperiences);

            List<Education> educations = educationService.getListEducationByCvId(iFindCVResponse.getCvId());
            List<String> schools = educations.stream().map(Education::getSchool).collect(Collectors.toList());
            response.setSchools(schools);

            response.setCareerGoal(candidate.getIntroduction());
            response.setCandidateAddress(candidate.getAddress());
//            response.setSumExperienceYear(iFindCVResponse.getSumExperienceYear());
            responseList.add(response);
        }

        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(responseList);
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(cvList.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(cvList.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;
    }

    @Override
    public ResponseDataPagination findCVTest(Integer pageNo, Integer pageSize, int experienceOption,
                                             String candidateAddress,
                                             String techStack) {
        List<FindCVResponse> responseList = new ArrayList<>();
        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);

        Page<IFindCVResponse> cvList = cvService.findCVTest(pageable, experienceOption,
                candidateAddress, techStack);
        for (IFindCVResponse iFindCVResponse : cvList) {
            FindCVResponse response = new FindCVResponse();
            Optional<CV> cv = cvService.findCvById(iFindCVResponse.getCvId());
            Candidate candidate = candidateService.getCandidateById(cv.get().getCandidateId());
            response.setCandidateName(candidate.getFullName());

            List<WorkExperience> workExperiences = workExperienceService.
                    getListWorkExperienceByCvId(iFindCVResponse.getCvId());
            List<String> workPositionExperiences = new ArrayList<>();
            for (WorkExperience workExperience : workExperiences) {
                workPositionExperiences.add(workExperience.getPosition() + " - " + workExperience.getCompanyName());
            }
            response.setWorkPositionExperiences(workPositionExperiences);

            List<Education> educations = educationService.getListEducationByCvId(iFindCVResponse.getCvId());
            List<String> schools = educations.stream().map(Education::getSchool).collect(Collectors.toList());
            response.setSchools(schools);

            response.setCareerGoal(candidate.getIntroduction());
            response.setCandidateAddress(candidate.getAddress());
//            response.setSumExperienceYear(iFindCVResponse.getSumExperienceYear());
            responseList.add(response);
        }

        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(responseList);
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(cvList.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(cvList.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;
    }

    @Override
    public void uploadBanner(long recruiterId, UploadBannerRequest request) {
        if (recruiterService.getRecruiterById(recruiterId) == null) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        Payment payment = paymentService.findById(request.getPaymentId());
        //xem gói mà nhà tuyển dụng mua có những tính năng nào
        Banner banner = bannerService.findById(payment.getBannerId());
        if (banner.isSpotlight()) {
            BannerActive bannerActive = new BannerActive();
            bannerActive.setImageUrl(request.getSpotLightImage());
            bannerActive.setPaymentId(payment.getId());
            bannerActive.setDisplayPosition(Enums.BannerPosition.SPOTLIGHT.getStatus());
            bannerActive.create();
            bannerActiveRepository.save(bannerActive);
        }
        if (banner.isHomepageBannerA()) {
            BannerActive bannerActive = new BannerActive();
            bannerActive.setImageUrl(request.getHomepageBannerAImage());
            bannerActive.setPaymentId(payment.getId());
            bannerActive.setDisplayPosition(Enums.BannerPosition.HOME_BANNER_A.getStatus());
            bannerActive.create();
            bannerActiveRepository.save(bannerActive);
        }
        if (banner.isHomePageBannerB()) {
            BannerActive bannerActive = new BannerActive();
            bannerActive.setImageUrl(request.getHomepageBannerBImage());
            bannerActive.setPaymentId(payment.getId());
            bannerActive.setDisplayPosition(Enums.BannerPosition.HOME_BANNER_B.getStatus());
            bannerActive.create();
            bannerActiveRepository.save(bannerActive);
        }
        if (banner.isHomePageBannerC()) {
            BannerActive bannerActive = new BannerActive();
            bannerActive.setImageUrl(request.getHomepageBannerCImage());
            bannerActive.setPaymentId(payment.getId());
            bannerActive.setDisplayPosition(Enums.BannerPosition.HOME_BANNER_C.getStatus());
            bannerActive.create();
            bannerActiveRepository.save(bannerActive);
        }
        if (banner.isJobBannerA()) {
            BannerActive bannerActive = new BannerActive();
            bannerActive.setImageUrl(request.getJobBannerAImage());
            bannerActive.setPaymentId(payment.getId());
            bannerActive.setDisplayPosition(Enums.BannerPosition.JOB_BANNER_A.getStatus());
            bannerActive.create();
            bannerActiveRepository.save(bannerActive);
        }
        if (banner.isJobBannerB()) {
            BannerActive bannerActive = new BannerActive();
            bannerActive.setImageUrl(request.getJobBannerBImage());
            bannerActive.setPaymentId(payment.getId());
            bannerActive.setDisplayPosition(Enums.BannerPosition.JOB_BANNER_B.getStatus());
            bannerActive.create();
            bannerActiveRepository.save(bannerActive);
        }
        if (banner.isJobBannerC()) {
            BannerActive bannerActive = new BannerActive();
            bannerActive.setImageUrl(request.getJobBannerCImage());
            bannerActive.setPaymentId(payment.getId());
            bannerActive.setDisplayPosition(Enums.BannerPosition.JOB_BANNER_C.getStatus());
            bannerActive.create();
            bannerActiveRepository.save(bannerActive);
        }
    }

    @Override
    public ResponseDataPagination getJobOfRecruiter(Integer pageNo, Integer pageSize, long recruiterId) {
        List<JobForRecruiterResponse> responseList = new ArrayList<>();

        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);

        if (!recruiterService.existById(recruiterId)) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        Page<Job> jobsListOfRecruiter = jobService.getJobOfRecruiter(pageable, recruiterId);
        if (jobsListOfRecruiter.hasContent()) {
            for (Job job : jobsListOfRecruiter) {
                JobForRecruiterResponse response = new JobForRecruiterResponse();
                response.setJobId(job.getId());
                response.setJobName(job.getJobName());
                Company company = companyService.getCompanyById(job.getCompanyId());
                if (company == null) {
                    //company không tồn tại mà đã tạo được job
                    throw new HiveConnectException(ResponseMessageConstants.PLEASE_TRY_TO_CONTACT_ADMIN);
                }
                response.setCompanyName(company.getName());
                response.setWorkPlace(job.getWorkPlace());
                response.setFromSalary(job.getFromSalary());
                response.setToSalary(job.getToSalary());

                int applyCount = appliedJobService.countAppliedCVOfJob(job.getId());
                response.setApplyCount(applyCount);
//                response.getViewCount();
                response.setStartDate(job.getStartDate());
                response.setEndDate(job.getEndDate());
                responseList.add(response);
            }
        }
        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(responseList);
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(jobsListOfRecruiter.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(jobsListOfRecruiter.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;
    }

    @Override
    public DetailPurchasedPackageResponse getDetailPurchasedPackage(long recruiterId, long paymentId) {
        DetailPurchasedPackageResponse response = new DetailPurchasedPackageResponse();
        Payment payment = paymentService.findById(paymentId);
        //lấy thông tin của gói mua
        DetailPaymentPackageInforResponse detailPPInforRes = new DetailPaymentPackageInforResponse();
        if(payment.getBannerId() > 0) {
            Banner banner = bannerService.findById(payment.getBannerId());
            detailPPInforRes.setBannerPaymentPackage(banner);
        } else {
            if (payment.getDetailPackageId() > 0) {
                NormalPaymentPackage normalPP = new NormalPaymentPackage();
                DetailPackage detailPackage = detailPackageService.findById(payment.getDetailPackageId());
                Optional<RentalPackage> rentalPackage = rentalPackageService.findById(detailPackage.getRentalPackageId());
                if (!rentalPackage.isPresent()) {
                    throw new HiveConnectException(ResponseMessageConstants.RENTAL_PACKAGE_DOES_NOT_EXIST + ". " + ResponseMessageConstants.PLEASE_TRY_TO_CONTACT_ADMIN);
                }
                normalPP.setGroupPackageId(detailPackage.getRentalPackageId());
                normalPP.setGroupPackageName(rentalPackage.get().getPackageGroup());
                normalPP.setDetailPackageName(detailPackage.getDetailName());
                normalPP.setPrice(detailPackage.getPrice());
                normalPP.setDiscountPrice(detailPackage.getDiscount());
                normalPP.setTimeExpired(detailPackage.getTimeExpired());
                normalPP.setRelatedJob(detailPackage.isRelatedJob());
                normalPP.setSuggestJob(detailPackage.isSuggestJob());
                normalPP.setMaxCvView(detailPackage.getMaxCvView());
                detailPPInforRes.setNormalPaymentPackage(normalPP);
            }
        }
        response.setInforPPRes(detailPPInforRes);
        //lấy chi tiết recruiter đã upload cái gì cho gói này
        if (payment.getBannerId() > 0) {
            BannerDetailPurchasedResponse bannerDetailPurchasedRes = new BannerDetailPurchasedResponse();
            List<BannerPositionDetailResponse> bannerActiveOfPayment = bannerActiveService
                    .getAllBannerByPaymentId(paymentId);
            bannerDetailPurchasedRes.setBannerPosRes(bannerActiveOfPayment);
            response.setBannerPurchasedPPRes(bannerDetailPurchasedRes);
        } else {
            if (payment.getDetailPackageId() > 0) {
                Job selectedJob = jobService.getJobById(payment.getJobId());
                JobDetailPurchasedResponse jobDetailPurchasedRes = modelMapper
                        .map(selectedJob, JobDetailPurchasedResponse.class);
                Optional<Company> company = companyService.findById(selectedJob.getCompanyId());
                jobDetailPurchasedRes.setCompanyName(company.get().getName());
                Fields field = fieldsService.getById(selectedJob.getFieldId());
                jobDetailPurchasedRes.setFieldName(field.getFieldName());
                Optional<VietnamCountry> country = countryService.findById(selectedJob.getCountryId());
                jobDetailPurchasedRes.setCountry(country.get().getCountryName());

                response.setJobPurchasedPPRes(jobDetailPurchasedRes);
            }
        }
        return response;
    }
}
