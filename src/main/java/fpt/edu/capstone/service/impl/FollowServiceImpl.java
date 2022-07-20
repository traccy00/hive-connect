package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.candidate.FollowingResponse;
import fpt.edu.capstone.dto.job.JobResponse;
import fpt.edu.capstone.entity.AppliedJob;
import fpt.edu.capstone.entity.Follow;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.repository.FollowRepository;
import fpt.edu.capstone.service.FollowService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    @Override
    public Optional<List<Follow>> getListFollowedJobByFollowerId(long followerId) {
        return followRepository.getListFollowedJobByFollowerId(followerId);
    }

    @Override
    public void unFollow(long followerId, long followedId, long type) {
        followRepository.unFollow(followerId, followedId, type);
    }

    @Override
    public Follow insertFollow(Follow follow) {
        return followRepository.save(follow);
    }

    @Override
    public Boolean isFollowing(long followerId, long followedId, long type) {
        Optional<Follow> follow = followRepository.getFollowIfHave(followerId, followedId, type);
        if(follow.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public ResponseDataPagination getFollowedJobByCandidateID(Integer pageNo, Integer pageSize, long candidateId) {
        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);
        Page<FollowingResponse> follows = followRepository.getFollowedJobByCandidateID(pageable, candidateId);

        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(follows.getContent());
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(follows.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(follows.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;
    }
}
