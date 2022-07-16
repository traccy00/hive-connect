package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.follow WHERE follower_id = ?1 and followed_id = ?2 and type = ?3", nativeQuery = true)
    void unFollow(long followerId, long followedId, long type);

    @Query(value = "select * from follow where follower_id = ?1 and type = 1", nativeQuery = true)
    Optional<List<Follow>> getListFollowedJobByFollowerId(long followerId);

    @Query(value = "select * from follow where follower_id = ?1 and followed_id = ?2 and type = ?3", nativeQuery = true)
    Optional<Follow> getFollowIfHave(long followerId, long followedId, long type);
}
