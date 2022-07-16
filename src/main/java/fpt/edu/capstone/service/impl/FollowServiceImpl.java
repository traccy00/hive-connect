package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Follow;
import fpt.edu.capstone.repository.FollowRepository;
import fpt.edu.capstone.service.FollowService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

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
}
