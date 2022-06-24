package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.CV;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CVService {
    List<CV> findCvByCandidateId(Long candidateId); //giu

    CV getCVByCandidateId(long candidateId); //nam

    //giu
    void insertCv(long candidateId, long isDeleted, String summary, LocalDateTime createAt, LocalDateTime updateAt);

    //giu
    Optional<CV> findCvById(long id);

    void updateSummary(long cvId, String newSummary);

    void updateUpdatedDateOfCV(long id, LocalDateTime updatedDate);
}
