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
/*
không nên insert từng entity, nên insert từng đối tượng
thiếu check condition
insert cần lưu thêm created at
 */
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

    @PostMapping("/insert-education")
    public ResponseData insertEducation(){
        //tạo ra 1 bảng học vấn để lưu thông tin học vấn của candidate
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

