package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.candidate.CandidateBaseInformationResponse;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.entity.*;
import fpt.edu.capstone.repository.CVImportedRepository;
import fpt.edu.capstone.service.CandidateService;
import fpt.edu.capstone.service.UserService;
import fpt.edu.capstone.service.impl.CVImportedService;
import fpt.edu.capstone.service.impl.ImageService;
import fpt.edu.capstone.service.impl.UserImageService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.LogUtils;
import fpt.edu.capstone.utils.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/candidate")
public class CandidateController {
    private static final Logger logger = LoggerFactory.getLogger(Candidate.class);
    @Autowired
    private CandidateService candidateService;

    @Autowired
    private UserImageService userImageService;

    @Autowired
    private UserService userService;

    @Autowired
    private CVImportedService cvImportedService;


    @GetMapping("/all")
    public ResponseData getAllCandidate() {
        try{
            List<Candidate> listAllCandidate = candidateService.getAllCandidate();
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "All of candidate", listAllCandidate);
        }catch (Exception ex){
            logger.error(ex.getMessage());
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage());
        }
    }

    @GetMapping("/find-by-userid")
    public ResponseData getCandidateById(@RequestParam long userId) {
        try{
            Optional<Candidate> candidate = candidateService.findCandidateByUserId(userId);
            if(candidate.isPresent()){
                CandidateBaseInformationResponse response = new CandidateBaseInformationResponse();
                Candidate candidateNN = candidate.get();
                response.setAddress(candidateNN.getAddress());
                response.setAvatarUrl(candidateNN.getAvatarUrl());
                response.setCountry(candidateNN.getCountry());
                response.setBirthDate(candidateNN.getBirthDate());
                response.setId(candidateNN.getId());
                response.setExperienceLevel(candidateNN.getExperienceLevel());
                response.setFullName(candidateNN.getFullName());
                response.setGender(candidateNN.isGender());
                response.setIntroduction(candidateNN.getIntroduction());
                response.setNeedJob(candidateNN.isNeedJob());
                response.setSocialLink(candidateNN.getSocialLink());
                response.setUserId(candidateNN.getUserId());
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Find candidate successful", response);
            }
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Can not find candidate by this user id", null);
        }catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
    }

//    @PostMapping("/insert")
//    public ResponseData insertCandidate(@RequestBody Candidate newCandidate){
//       try{
//           System.out.println(newCandidate.toString());
//           candidateService.insertCandidate(newCandidate);
//           return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Add candicate successful");
//        } catch (Exception ex) {
//           return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage());
//       }
//    }

    @PutMapping("/{id}")
    public ResponseData updateCandidate(@RequestBody Candidate newCandidate, @PathVariable long id){
        Optional<Candidate> foundedCandidate = candidateService.findById(id);
        if(!foundedCandidate.isPresent()){
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus() , "Can not find candidate with this id "+id);
        }
        try{
            System.out.println(newCandidate.toString());
            candidateService.updateCandidate(newCandidate, id);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Edit candicate successful", newCandidate);
        } catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage());
        }
    }

    @PutMapping("/update-candidate-base-information")
    public ResponseData updateCandidateBaseInformation(@RequestBody Candidate updateCandidate) {
        try {
            Optional<Candidate> candidate = candidateService.findById(updateCandidate.getId());
            if(candidate.isPresent()) {
              candidateService.updateCandidateInformation(updateCandidate);
              return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Update successful", updateCandidate);
            }
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), "Can not find this candidate", updateCandidate.getId());
        }catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }

    }

    @PutMapping("/update-is-need-job")
    public ResponseData updateIsNeedJob(@RequestParam long candidateId) {
            try{
                Optional<Candidate> candidate = candidateService.findById(candidateId);
                if(candidate.isPresent()) {
                    candidateService.updateIsNeedJob(!candidate.get().isNeedJob(), candidate.get().getId());
                    return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Update successful", candidate.get().isNeedJob());
                }
                return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), "Can not find this candidate", candidateId);
            }catch (Exception ex) {
                return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
            }
    }

    @PostMapping("upload-cv")
    public ResponseData uploadCV(@RequestParam("file") MultipartFile file, long candidateId) {
        try{
                Optional<Candidate> candidate = candidateService.findById(candidateId);
                if(candidate.isPresent()){ //Check if this user is candidate
                        CVImported cvImported =  cvImportedService.save(file,"CV",candidateId);
                        return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Upload CV successful", cvImported.getId());
                }else {
                    return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), "Can not find this candidate", candidateId);
                }
        }catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
    }

    @GetMapping("/cv/{id}")
    public ResponseEntity<byte[]> getCVImported(@PathVariable String id) {
        Optional<CVImported> CVImported = cvImportedService.findById(id);

        if (!CVImported.isPresent()) {
            return ResponseEntity.notFound()
                    .build();
        }

        CVImported cvImported = CVImported.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + cvImported.getName() + "\"")
                .contentType(MediaType.valueOf(cvImported.getContentType()))
                .body(cvImported.getData());
    }
    @GetMapping("/get-who-viewed-CV")
    public ResponseData getRecruitersViewedCV(@RequestParam long candidateId) {
        try {
//            Page<>
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

}

