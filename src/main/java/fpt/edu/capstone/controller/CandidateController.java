package fpt.edu.capstone.controller;

import fpt.edu.capstone.entity.sprint1.Candidate;
import fpt.edu.capstone.repository.CandidateRepository;
import fpt.edu.capstone.service.CandidateService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/candidate")
public class CandidateController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private CandidateService candidateService;

    @GetMapping("/all")
    public ResponseData getAllCandidate() {
        try{
            List<Candidate> listAllCandidate = candidateService.getAllCandidate();
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), listAllCandidate);
        }catch (Exception ex){
            logger.error(ex.getMessage());
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage());
        }
    }

    @PostMapping("/insert")
    public ResponseData inserCandidate(@RequestBody Candidate newCandidate){
       try{
           System.out.println(newCandidate.toString());
           candidateService.insertCandidate(newCandidate.getUserId(), newCandidate.getListTechStackId(), newCandidate.getFullName(), newCandidate.getPhoneNumber(), newCandidate.isGender(), newCandidate.getBirthDate(), newCandidate.getSocialId(), newCandidate.getTapHistoryId(), newCandidate.getWishListId(), newCandidate.getSearchHistoryID(), newCandidate.getCvUrl(), newCandidate.getAppliedJobId());

           return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Add candicate successful");
        } catch (Exception ex) {
           return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage());
       }
    }

    @PutMapping("/{id}")
    public ResponseData updateCandidate(@RequestBody Candidate newCandidate, @PathVariable long id){
        Optional<Candidate> foundedCandidate = candidateService.findById(id);
        if(!foundedCandidate.isPresent()){
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus() , "Can not find candidate with this id "+id);
        }
        try{
            System.out.println(newCandidate.toString());
            candidateService.updateCandidate(newCandidate.getUserId(), newCandidate.getListTechStackId(), newCandidate.getFullName(), newCandidate.getPhoneNumber(), newCandidate.isGender(), newCandidate.getBirthDate(), newCandidate.getSocialId(), newCandidate.getTapHistoryId(), newCandidate.getWishListId(), newCandidate.getSearchHistoryID(), newCandidate.getCvUrl(), newCandidate.getAppliedJobId(), id);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Edit candicate successful", newCandidate);
        } catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage());
        }
    }

}

