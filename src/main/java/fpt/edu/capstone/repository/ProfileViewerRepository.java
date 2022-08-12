package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.ProfileViewer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ProfileViewerRepository extends JpaRepository<ProfileViewer, Long> {

    @Query(value = "select * from profile_viewer pv " +
            "join cv c on pv.cv_id = c.id " +
            "join candidate c2 on c2.id = c.candidate_id " +
            "join users u on u.id = c2.user_id where cv_id = ? and u.is_locked = false order by pv.created_at desc", nativeQuery = true)
    Page<ProfileViewer> getAllByCvId(Pageable pageable, long cvId);

    @Query(value = "select * from profile_viewer pv where pv.cv_id = ?1 and pv.viewer_id = ?2", nativeQuery = true)
    ProfileViewer getByCvIdAndViewerId(long cvId, long viewerId);

    @Query(value = "select * from profile_viewer pv where pv.cv_id = ?1 and pv.viewer_id = ?2", nativeQuery = true)
    Optional<ProfileViewer> getByCvIdAndViewerIdOptional(long cvId, long viewerId);
}
