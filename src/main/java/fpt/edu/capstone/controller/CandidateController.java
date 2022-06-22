package fpt.edu.capstone.controller;

import fpt.edu.capstone.entity.Candidate;
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
    private static final Logger logger = LoggerFactory.getLogger(Candidate.class);
    @Autowired
    private CandidateService candidateService;

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

    @PostMapping("/insert")
    public ResponseData inserCandidate(@RequestBody Candidate newCandidate){
       try{
           System.out.println(newCandidate.toString());
           candidateService.insertCandidate(newCandidate);
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
            candidateService.updateCandidate(newCandidate, id);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Edit candicate successful", newCandidate);
        } catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage());
        }
    }


}

