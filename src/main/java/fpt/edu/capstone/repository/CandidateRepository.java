package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO public.candidate (user_id, gender, birth_date, search_history, country, full_name, address, social_link, avatar_url, wishlist_job_id_list, tap_history_id_list, is_need_job, experience_level, introduction) VALUES(?1, true, null, null, null, null, null, null, null, null, null, true, 0, null)", nativeQuery = true)
    void insertCandidate(long userId);


    @Query(value = "select * from Candidate c where c.user_id = ?1", nativeQuery = true)
    Optional<Candidate> findCandidateByUserId(long userId);

    boolean existsById(long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE public.candidate SET gender=?1, birth_date=?2, country=?3, full_name=?4, address=?5, social_link=?6, introduction=?7 WHERE id=?8", nativeQuery = true)
    void updateCandidateInformation(boolean gender, LocalDateTime birthDate, String country, String fullName, String address, String socialLink,  String introduction, long id);
}
