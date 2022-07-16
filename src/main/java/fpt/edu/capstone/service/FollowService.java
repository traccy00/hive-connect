package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.Follow;

import java.util.List;
import java.util.Optional;

public interface FollowService {
    Optional<List<Follow>> getListFollowedJobByFollowerId(long followerId);

    void unFollow(long followerId, long followedId, long type);

    Follow insertFollow(Follow follow);

    Boolean isFollowing(long followerId, long followedId, long type);
}
