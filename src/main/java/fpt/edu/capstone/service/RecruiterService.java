package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.recruiter.RecruiterProfileResponse;
import fpt.edu.capstone.entity.sprint1.Recruiter;

public interface RecruiterService {
    RecruiterProfileResponse getRecruiterProfile(long userId);

    boolean existById(long recId);

    Recruiter getRecruiterById(long id);
}
