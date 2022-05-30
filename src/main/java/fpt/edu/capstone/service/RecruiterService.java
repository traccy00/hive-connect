package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.recruiter.RecruiterProfileResponse;

public interface RecruiterService {
    RecruiterProfileResponse getRecruiterProfile(long userId);

    boolean existById(long recId);
}
