package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.sprint1.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {

    @Query(value = "select * from recruiter r where r.user_id = ? and is_deleted = false", nativeQuery = true)
    Recruiter getRecruiterProfile(long userId);
}
