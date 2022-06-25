package fpt.edu.capstone.controller;

import com.sendgrid.helpers.mail.objects.Email;
import fpt.edu.capstone.dto.CV.CVResponse;
import fpt.edu.capstone.dto.CV.UpdateCVSummaryRequest;
import fpt.edu.capstone.entity.*;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/CV")
public class CVController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Autowired
    private CVService cvService;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private EducationService educationService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private MajorLevelService majorLevelService;

    @Autowired
    private OtherSkillService otherSkillService;

    @Autowired
    private WorkExperienceService workExperienceService;

    @Autowired
    private FieldsService fieldsService;

    @Autowired
    private MajorService majorService;

    //Them phan add summary

    @GetMapping("/get-list-CV")
    public ResponseData getListCompany() {
        return null;
    }

    @GetMapping("/get-cv")
    public ResponseData findCvByCandidateID(@RequestParam long candidateId) {
        try {
            List<CV> cv = cvService.findCvByCandidateId(candidateId);
            if (cv != null) {
                List<Certificate> certificates = certificateService.getListCertificateByCvId(cv.get(0).getId());
                List<Education> educations = educationService.getListEducationByCvId(cv.get(0).getId());
                List<Language> languages = languageService.getListLanguageByCvId(cv.get(0).getId());
                List<MajorLevel> majorLevels = majorLevelService.getListMajorLevelByCvId(cv.get(0).getId());
                List<OtherSkill> otherSkills = otherSkillService.getListOtherSkillByCvId(cv.get(0).getId());
                List<WorkExperience> workExperiences = workExperienceService.getListWorkExperienceByCvId(cv.get(0).getId());
                CVResponse cvResponse = new CVResponse();
                cvResponse.setCandidateId(candidateId);
                cvResponse.setCreatedAt(cv.get(0).getCreatedAt());
                cvResponse.setUpdatedAt(cv.get(0).getUpdatedAt());
                cvResponse.setCertificates(certificates);
                cvResponse.setIsDeleted(cv.get(0).getIsDeleted());
                cvResponse.setEducations(educations);
                cvResponse.setId(cv.get(0).getId());
                cvResponse.setLanguages(languages);
                cvResponse.setSummary(cv.get(0).getSummary());
                cvResponse.setMajorLevels(majorLevels);
                cvResponse.setOtherSkills(otherSkills);
                cvResponse.setWorkExperiences(workExperiences);
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Success", cvResponse);
            } else {
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "No CV be found", null);
            }
        } catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "No cv be founded", null);
        }
    }

    @GetMapping("/create-cv")
    public ResponseData createCV(@RequestParam long candidateId) {
        try {
            List<CV> cv = cvService.findCvByCandidateId(candidateId);
            if (!cv.isEmpty()) {
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "You have CV already", null);
            }
        } catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
        LocalDateTime nowDate = LocalDateTime.now();
        cvService.insertCv(candidateId, 0, "", nowDate, nowDate);
        List<CV> cvs = cvService.findCvByCandidateId(candidateId);
        return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Create CV successful", cvs.get(0));
    }

    @PostMapping("/insert-education")
    public ResponseData insertEducation(@RequestBody Education newEdudation) {
        Optional<CV> cv = cvService.findCvById(newEdudation.getCvId());
        if(!cv.isPresent()) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), "CV is not exist", null);
        }
        educationService.insertEducation(newEdudation.getCvId(), newEdudation.getSchool(), newEdudation.getMajor(), newEdudation.getStartDate(), newEdudation.getEndDate(), newEdudation.getDescription(), newEdudation.isStudying());
        return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Create Education Success", newEdudation);
    }

    @PostMapping("/insert-work-exp")
    public ResponseData insertWorkExp(@RequestBody WorkExperience newWorkExperience) {
        Optional<CV> cv = cvService.findCvById(newWorkExperience.getCvId());
        if(!cv.isPresent()) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), "CV is not exist", null);
        }
        workExperienceService.insertWorkExperience(newWorkExperience.getCvId(), newWorkExperience.getCompanyName(), newWorkExperience.getPosition(), newWorkExperience.getStartDate(), newWorkExperience.getEndDate(), newWorkExperience.getDescription());
        return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Create Work Experience Success", newWorkExperience);
    }

    @PostMapping("/insert-language")
    public ResponseData insertLanguage(@RequestBody Language newLanguage) {
        Optional<CV> cv = cvService.findCvById(newLanguage.getCvId());
        if(!cv.isPresent()) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), "CV is not exist", null);
        }
        languageService.insertLanguage(newLanguage.getLanguage(), newLanguage.getLevel(), newLanguage.getCvId());
        return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Create New Language Success", newLanguage);
    }

    @PostMapping("/insert-skill")
    public ResponseData insertSkill(@RequestBody OtherSkill newOtherSkill) {
        Optional<CV> cv = cvService.findCvById(newOtherSkill.getCvId());
        if(!cv.isPresent()) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), "CV is not exist", null);
        }
        otherSkillService.insertOtherSkill(newOtherSkill.getSkillName(), newOtherSkill.getCvId(), newOtherSkill.getLevel());
        return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Create Other Skill Success", newOtherSkill);
    }

    @PostMapping("/insert-certificate")
    public ResponseData insertCert(@RequestBody Certificate newCertificate) {
        Optional<CV> cv = cvService.findCvById(newCertificate.getCvId());
        if(!cv.isPresent()) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), "CV is not exist", null);
        }
        certificateService.insertCertificate(newCertificate.getCertificateName(), newCertificate.getCertificateUrl(), 0, newCertificate.getCvId());
        return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Create Certificate Success", newCertificate);
    }

    @GetMapping("/get-all-field")
    public ResponseData getAllField() {
        List<Fields> fields = fieldsService.getAllField();
        if(fields.isEmpty()) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(),"Have no any field", null);
        }
        return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Success", fields);
    }

    @GetMapping("/get-major-by-field")
    public ResponseData getMajorByFieldId(@RequestParam long fieldId) {
        try {
            List<Major> majors = majorService.getAllMajorByFieldId(fieldId);
            if (majors.isEmpty()) {
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Major is empty", null);
            }
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Success", majors);
        } catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
    }

    @PostMapping("/update-cv-summary")
    public ResponseData updateCvSummary(@RequestBody UpdateCVSummaryRequest updateCVSummaryRequest) {
        try{
            Optional<CV> cv = cvService.findCvById(updateCVSummaryRequest.getCvId());
            if(cv.isPresent()) {
                cvService.updateSummary(updateCVSummaryRequest.getCvId(), updateCVSummaryRequest.getNewSummary());
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Update Summary Success", updateCVSummaryRequest.getNewSummary());
            }
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Can not fnid this cv", updateCVSummaryRequest.getCvId());
        }catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
    }

    @PutMapping("/update-updated-date-of-cv")
    public ResponseData updateUpdatedDateOfCv(@RequestBody long id) {
        try {
            Optional<CV> cv = cvService.findCvById(id);
            if(cv.isPresent()) {
                LocalDateTime nowDate = LocalDateTime.now();
                cvService.updateUpdatedDateOfCV(id, nowDate);
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Update updated date success", nowDate);
            }
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(),"CV is not exist", null);
        }catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
    }

    @PutMapping("/update-education")
    public ResponseData updateEducationById(@RequestBody Education updateEducation) {
        try {
            Optional<Education> education = educationService.getEducationById(updateEducation.getId());
            if(education.isPresent()) {
                LocalDateTime nowDate = LocalDateTime.now();
                educationService.updateEducation(updateEducation);
                cvService.updateUpdatedDateOfCV(updateEducation.getCvId(), nowDate);
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Update education success", updateEducation);
            }
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(),"Education is not exist", null);
        }catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
    }

    @PostMapping("/insert-major-level")
    public ResponseData insertMajorLevel(@RequestBody  MajorLevel newMajorLevel) {
        try{
            Optional<CV> cv = cvService.findCvById(newMajorLevel.getCvId());
            if(!cv.isPresent()) {
                return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), "CV is not exist", null);
            }
            majorLevelService.insertNewMajorLevel(newMajorLevel);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Create Major Level Success", newMajorLevel);

        }catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ex.getMessage(), null);
        }
    }
    @PutMapping("/delete-education")
    public ResponseData deleteEducationById(@RequestBody long id) {
        try {
            Optional<Education> education = educationService.getEducationById(id);
            if(education.isPresent()) {
                LocalDateTime nowDate = LocalDateTime.now();
                educationService.deleteEducation(education.get().getId());
                cvService.updateUpdatedDateOfCV(education.get().getCvId(), nowDate);
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Update education success", education);
            }
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(),"Education is not exist", null);
        }catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
    }


}
