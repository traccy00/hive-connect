package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.sprint1.Candidate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO public.candidate (user_id,list_tech_stack_id,full_name,phone_number,gender,birth_date,social_id,tap_history_id,wish_list_id,search_history_id,cv_url,applied_job_id) VALUES (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12)", nativeQuery = true)
    void insertCandidate(long userId, String listTechStackId, String fullName, String phoneNumber, boolean gender, Date birthDate, Long socialId, long tapHistoryId, long wishLishId, long searchHistoryId, String cvUrl, long appliedJobId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE public.candidate SET user_id=?1, list_tech_stack_id=?2, full_name=?3, phone_number=?4, gender=?5, birth_date=?6, social_id=?7, tap_history_id=?8, wish_list_id=?9, search_history_id=?10, cv_url=?11, applied_job_id=?12 WHERE id=?13", nativeQuery = true)
    void updateCandidate(long userId, String listTechStackId, String fullName, String phoneNumber, boolean gender, Date birthDate, Long socialId, long tapHistoryId, long wishLishId, long searchHistoryId, String cvUrl, long appliedJobId, long id);

    boolean existsById(long id);
}
