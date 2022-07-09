package fpt.edu.capstone.repository;

import fpt.edu.capstone.dto.job.JobResponse;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.entity.RecruiterPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Query(value = "select j from Job j where j.fieldId =:fieldId or 0 =:fieldId " +
            "and j.countryId =:countryId or 0 =:countryId " +
            "and (lower(j.jobName) like lower(concat('%', :jobName ,'%')) or :jobName is null or :jobName ='')" +
            "and j.fromSalary =:fromSalary or 0 =:fromSalary " +
            "and j.toSalary =:toSalary or 0 =:toSalary " +
            "and (lower(j.rank) like lower(concat('%', :rank ,'%')) or :rank is null or :rank ='')" +
            "and (lower(j.workForm) like lower(concat('%', :workForm ,'%')) or :workForm is null or :workForm ='')" +
            "and (lower(j.workPlace) like lower(concat('%', :workPlace ,'%')) or :workPlace is null or :workPlace ='')")
    Page<Job> searchListJobFilter(Pageable pageable, @Param("fieldId") long fieldId,@Param("countryId") long countryId, @Param("jobName") String jobName,
                                            @Param("fromSalary") long fromSalary, @Param("toSalary") long toSalary,
                                            @Param("rank") String rank, @Param("workForm") String workForm,
                                            @Param("workPlace") String workPlace);

//    @Query("update RecruiterPost c set c.isDeleted = 1 where c.id =:jobId")
//    void deleteJob(@Param("jobId") long jobId);

    boolean existsById(long id);

    @Query(value = "select * from Job where work_form like  upper(concat('%', :workForm ,'%'))", nativeQuery = true)
    Page<Job> getListJobByWorkForm(Pageable pageable, @Param("workForm") String workForm);

    @Query(value = "select * from job where is_new_job = ?1 and (is_deleted = ?2 or is_deleted is null)", nativeQuery = true)
    Page<Job> getNewestJob(Pageable pageable, boolean isNewJob, int isDeleted);

    @Query(value = "select * from job where is_urgent_job = ?1 and (is_deleted = ?2 or is_deleted is null)", nativeQuery = true)
    Page<Job> getUrgentJob(Pageable pageable, boolean isUrgentJob, int isDeleted);

    @Query(value = "select * from job where is_popular_job = ?1 and (is_deleted = ?2 or is_deleted is null)", nativeQuery = true)
    Page<Job> getPopularJob(Pageable pageable, boolean b, int i);

    @Query("Select c from Job c where c.fieldId = :fieldId and c.isDeleted = 0")
    Page<Job> getListJobByFieldId(Pageable pageable, @Param("fieldId") long fieldId);

    @Query("select c from Job c where c.jobDescription like lower(concat('%', :majorName ,'%')) or c.jobRequirement like  lower(concat('%', :majorName ,'%'))")
    List <Job> getListSuggestJobByCv(String majorName);

    Page<Job> getAllByRecruiterId(Pageable pageable, long recruiterId);

    Optional<Job> findById(long id);

    @Query(value = "select * from job where id = ?", nativeQuery = true)
    Job getById(long id);

    @Query(value = "select * from job where company_id = ?1", nativeQuery = true)
    Page<Job> getJobByCompanyId(long companyId, Pageable pageable);
}