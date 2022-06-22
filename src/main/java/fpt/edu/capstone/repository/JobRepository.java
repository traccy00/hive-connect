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

public interface JobRepository extends JpaRepository<Job, Long> {

//    @Query(value = "select j from RecruiterPost j where j.categoryId =:categoryId or 0 =:categoryId " +
//            "and (lower(j.companyName) like lower(concat('%', :companyName ,'%')) or :companyName is null or :companyName ='')" +
//            "and (lower(j.jobName) like lower(concat('%', :jobName ,'%')) or :jobName is null or :jobName ='')" +
//            "and j.fromSalary =:fromSalary or 0 =:fromSalary " +
//            "and j.toSalary =:toSalary or 0 =:toSalary " +
//            "and (lower(j.rank) like lower(concat('%', :rank ,'%')) or :rank is null or :rank ='')" +
//            "and (lower(j.workForm) like lower(concat('%', :workForm ,'%')) or :workForm is null or :workForm ='')" +
//            "and (lower(j.workPlace) like lower(concat('%', :workPlace ,'%')) or :workPlace is null or :workPlace ='')" +
//            "and (lower(j.techStackRequire) like lower(concat('%', :techStack ,'%')) or :techStack is null or :techStack ='')")
//    Page<RecruiterPost> searchListJobFilter(Pageable pageable, @Param("categoryId") long categoryId, @Param("companyName") String companyName,
//                                            @Param("jobName") String jobName, @Param("fromSalary") long fromSalary,
//                                            @Param("toSalary") long toSalary, @Param("rank") String rank, @Param("workForm") String workForm,
//                                            @Param("workPlace") String workPlace, @Param("techStack") String techStack);

//    @Query("update RecruiterPost c set c.isDeleted = 1 where c.id =:jobId")
//    void deleteJob(@Param("jobId") long jobId);

    boolean existsById(long id);

    @Query(value = "select * from Job where work_form like  lower(concat('%', :workForm ,'%'))", nativeQuery = true)
    List<JobResponse> getListJobByWorkForm(@Param("workForm") String workForm);

    @Query(value = "select * from job", nativeQuery = true)
    List<Job> getNewestJob(boolean isNewJob, int isDeleted);

    @Query("Select c from Job c where c.fieldId = :fieldId and c.isDeleted = 0")
    List<JobResponse> getListJobByFieldId(@Param("fieldId") long fieldId);

    @Query("select c from Job c where (c.jobDescription like lower(concat('%', :majorName ,'%'))) or (c.jobRequirement like  lower(concat('%', :majorName ,'%')))")
    List <Job> getListSuggestJobByCv(String majorName);

}