package fpt.edu.capstone.controller;

import fpt.edu.capstone.atmpCandidate.*;
import fpt.edu.capstone.dto.CV.CVResponse;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/CV")
public class CVController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

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


    @GetMapping("/get-list-CV")
    public ResponseData getListCompany(){
        return null;
    }

    @GetMapping("/get-cv")
    public ResponseData findCvByCandidateID(@RequestParam Long candidateId){
        try {
            CV cv = cvService.findCvByCandidateId(candidateId);
            if(cv != null){
                List<Certificate> certificates = certificateService.getListCertificateByCvId(cv.getId());
                List<Education> educations = educationService.getListEducationByCvId(cv.getId());
                List<Language> languages = languageService.getListLanguageByCvId(cv.getId());
                List<MajorLevel> majorLevels = majorLevelService.getListMajorLevelByCvId(cv.getId());
                List<OtherSkill> otherSkills = otherSkillService.getListOtherSkillByCvId(cv.getId());
                List<WorkExperience> workExperiences = workExperienceService.getListWorkExperienceByCvId(cv.getId());
                CVResponse cvResponse = new CVResponse();
                cvResponse.setCandidateId(candidateId);
                cvResponse.setCreatedAt(cv.getCreatedAt());
                cvResponse.setUpdatedAt(cv.getUpdatedAt());
                cvResponse.setCertificates(certificates);
                cvResponse.setDeleted(cv.isDeleted());
                cvResponse.setEducations(educations);
                cvResponse.setId(cv.getId());
                cvResponse.setLanguages(languages);
                cvResponse.setSummary(cv.getSummary());
                cvResponse.setMajorLevels(majorLevels);
                cvResponse.setOtherSkills(otherSkills);
                cvResponse.setWorkExperiences(workExperiences);
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(),"Success", cvResponse);
            }else {
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(),"No CV be found", null);
            }
        }catch (Exception ex){
            return null;
        }
    }

    @GetMapping("/create-cv")
    public ResponseData createCV(@RequestParam Long candidateId){
        CV cv = cvService.findCvByCandidateId(candidateId);
        if(cv != null) {
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "You have CV already", null);
        }

        return null;
    }
    @PostMapping("/insert-education")
    public ResponseData insertEducation(@RequestBody Education newEdudation){

        return null;
    }

    @PostMapping("/insert-work-exp")
    public ResponseData insertWorkExp(){
        //tạo ra 1 bảng kinh nghiệm làm việc để lưu thông tin knlv của candidate
        return null;
    }

    @PostMapping("/insert-language")
    public ResponseData insertLanguage(){
        //tạo ra 1 bảng language  để lưu thông tin language của candidate
        return null;
    }

    @PostMapping("/insert-skill")
    public ResponseData insertSkill(){
        //tạo ra 1 bảng Skill  để lưu thông tin skill của candidate
        return null;
    }

    @PostMapping("/insert-certificate")
    public ResponseData insertCert(){
        //tạo ra 1 bảng cert  để lưu thông tin cert của candidate
        return null;
    }
}
