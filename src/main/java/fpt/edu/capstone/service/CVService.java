package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.CV.IFindCVResponse;
import fpt.edu.capstone.entity.CV;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CVService {
    List<CV> findCvByCandidateId(Long candidateId); //giu

    CV getCVByCandidateId(long candidateId); //nam

    //giu
    Optional<CV> findCvById(long id);

    void updateSummary(long cvId, String newSummary);

    void updateUpdatedDateOfCV(long id, LocalDateTime updatedDate);

    Optional<CV> findByIdAndCandidateId(long id, long candidateId);

    Page<IFindCVResponse> findCVForRecruiter(Pageable pageable, //int experienceOption,
                                             String candidateAddress,
                                             String techStack);

    Page<IFindCVResponse> findCVTest(Pageable pageable, int experienceOption,
                                             String candidateAddress,
                                             String techStack);

    void save(CV cv);
}
