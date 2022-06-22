//package fpt.edu.capstone.repository;
//
//import fpt.edu.capstone.entity.JobHashtag;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface JobHashTagRepository extends JpaRepository<Long, JobHashtag> {
//
//    @Query(value = "select * from job_hashtag where job_id = ?", nativeQuery = true)
//    List<JobHashtag> getJobHashTagByJobId(long jobId);
//}
