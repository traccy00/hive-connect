package fpt.edu.capstone.repository;

import fpt.edu.capstone.dto.recruiter.CountTotalCreatedJobResponse;
import fpt.edu.capstone.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Query(value = "select j from Job j " +
            "join Company c on c.id = j.companyId " +
            "where (j.fieldId  =:fieldId or 0 =:fieldId) " +
            "and (j.countryId  =:countryId or 0 =:countryId) " +
            "and ((lower(j.jobName) like lower(concat('%', :jobName ,'%')) or :jobName is null or :jobName ='') " +
            "or (lower(j.rank) like lower(concat('%', :jobName ,'%')) or :jobName is null or :jobName ='') " +
            "or (lower(c.name) like lower(concat('%', :jobName ,'%')) or :jobName is null or :jobName ='') " +
            "or (lower(j.workForm) like lower(concat('%', :jobName ,'%')) or :jobName is null or :jobName ='') " +
            "or (lower(j.workPlace) like lower(concat('%', :jobName ,'%')) or :jobName is null or :jobName ='')) " +
            "and (lower(j.workForm) like lower(concat('%', :workForm ,'%')) or :workForm is null or :workForm ='') " +
            "and (lower(j.workPlace) like lower(concat('%', :workPlace ,'%')) or :workPlace is null or :workPlace ='') " +
            "and j.isDeleted  = 0")
    Page<Job> searchListJobFilter(Pageable pageable, @Param("fieldId") long fieldId, @Param("countryId") long countryId,
                                  @Param("jobName") String jobName, @Param("workForm") String workForm, @Param("workPlace") String workPlace);

    @Modifying
    @Query("update Job j set j.isDeleted = 1 where j.id =:jobId")
    void deleteJob(@Param("jobId") long jobId);

    boolean existsById(long id);

    @Query(value = "select * from Job where work_form like upper(concat('%', :workForm ,'%')) and flag =:flag order by random()", nativeQuery = true)
    Page<Job> getListJobByWorkForm(Pageable pageable, @Param("workForm") String workForm, @Param("flag") String flag);

    @Query(value = "select * from job where is_new_job = ?1 and (is_deleted = ?2 or is_deleted is null) and flag = ?3 order by random()", nativeQuery = true)
    Page<Job> getNewestJob(Pageable pageable, boolean isNewJob, int isDeleted, String flag);

    @Query(value = "select * from job where is_urgent_job = ?1 and (is_deleted = ?2 or is_deleted is null) and flag = ?3 order by random()", nativeQuery = true)
    Page<Job> getUrgentJob(Pageable pageable, boolean isUrgentJob, int isDeleted, String flag);

    @Query(value = "select * from job where is_popular_job = ?1 and (is_deleted = ?2 or is_deleted is null) and flag = ?3 order by random()", nativeQuery = true)
    Page<Job> getPopularJob(Pageable pageable, boolean isPopularJob, int isDeleted, String flag);

    @Query(value = "Select * from Job where field_id =:fieldId and is_deleted = 0 and flag =:flag order by random()", nativeQuery = true)
    Page<Job> getListJobByFieldId(Pageable pageable, @Param("fieldId") long fieldId, @Param("flag") String flag);

    @Query(value = "select * from job j where lower(j.job_description) like lower(concat('%', :majorName ,'%')) " +
            "or (lower(j.job_requirement) like lower(concat('%', :majorName ,'%'))) " +
            "or (lower(j.job_name) like lower(concat('%', :majorName ,'%')))", nativeQuery = true)
    List <Job> getListSuggestJobByCv(@Param("majorName") String majorName);

    Page<Job> getAllByRecruiterId(Pageable pageable, long recruiterId);

    @Query(value = "select * from job where id = ?", nativeQuery = true)
    Job getById(long id);

    @Query(value = "select * from job where company_id = ?1", nativeQuery = true)
    Page<Job> getJobByCompanyId(long companyId, Pageable pageable);

    @Query(value = "Update job set is_deleted = ?1 where id = ?2", nativeQuery = true)
    void updateIsDeleted(long status, long id);

    @Query(value = "select count(j.id) as totalCreatedJob, j.recruiter_id as recruiterId from job j where recruiter_id = ? group by j.recruiter_id", nativeQuery = true)
    CountTotalCreatedJobResponse countTotalCreatedJobOfRecruiter(long recruiterId);

    List<Job> findAllByRecruiterId(long recId);

    @Query(value = "with r as (select unnest (string_to_array((select j.job_name as job_name from job j where j.id = :detailJobId) , ' ')) as parts) " +
            "select * from job j where exists(select 1 from r where j.job_name like concat('%', r.parts,'%')) limit 20", nativeQuery = true)
    List<Job> getSameJobsOtherCompanies(@Param("detailJobId") long detailJobId);
}