package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.recruiter.RecruiterProfileResponse;
import fpt.edu.capstone.entity.Recruiter;

import java.util.Optional;

public interface RecruiterService {
    RecruiterProfileResponse getRecruiterProfile(long userId);

    boolean existById(long recId);

    Recruiter getRecruiterById(long id);

    Optional <Recruiter> findRecruiterByUserId(long userId);

    void insertRecruiter(long userId);
}
