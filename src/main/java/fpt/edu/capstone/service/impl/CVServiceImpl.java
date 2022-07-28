package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.CV.IFindCVResponse;
import fpt.edu.capstone.entity.CV;
import fpt.edu.capstone.repository.CVRepository;
import fpt.edu.capstone.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CVServiceImpl implements CVService {

    @Autowired
    CVRepository cvRepository;

    public List<CV> findCvByCandidateId(Long candidateId) {
        return cvRepository.findCvByCandidateId(candidateId);
    }

    @Override
    public CV insertCv(long candidateId, long isDeleted, String summary, LocalDateTime createAt, LocalDateTime updateAt) {
        CV newCV = new CV(0, candidateId, isDeleted, summary, createAt,updateAt);
        return cvRepository.save(newCV);
    }

    @Override
    public Optional<CV> findCvById(long id) {
        return cvRepository.findById(id);
    }

    @Override
    public void updateSummary(long cvId, String newSummary) {
        cvRepository.updateSummary(cvId, newSummary);
    }

    @Override
    public void updateUpdatedDateOfCV(long id, LocalDateTime updatedDate) {
        cvRepository.updateUpdatedDateOfCV(id,updatedDate);
    }

    @Override
    public Optional<CV> findByIdAndCandidateId(long id, long candidateId) {
        return cvRepository.findByIdAndCAndCandidateId(id, candidateId);
    }

    @Override
    public Page<IFindCVResponse> findCVForRecruiter(Pageable pageable, //int experienceOption,
                                                    String candidateAddress,
                                                    String techStack) {
        //1 năm kinh nghiệm
        int experienceYearSearch1 = 1;
        //3 năm kinh nghiệm
        int experienceYearSearch2 = 3;
//        return cvRepository.findCvForRecruiter(pageable, experienceOption, experienceYearSearch1, experienceYearSearch2,
//                candidateAddress, techStack);
        return cvRepository.findCvForRecruiter(pageable, candidateAddress, techStack);
    }

    @Override
    public Page<IFindCVResponse> findCVTest(Pageable pageable, int experienceOption,
                                                    String candidateAddress,
                                                    String techStack) {
        //1 năm kinh nghiệm
        int experienceYearSearch1 = 1;
        //3 năm kinh nghiệm
        int experienceYearSearch2 = 3;
        return cvRepository.findCvForRecruiter(pageable, experienceOption, experienceYearSearch1, experienceYearSearch2,
                candidateAddress, techStack);
    }

    public CV getCVByCandidateId(long candidateId) { //Nam
        return cvRepository.getByCandidateId(candidateId);
    }
}
