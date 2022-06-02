package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.sprint1.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Query(value = "select j from Job j where j.categoryId =:categoryId or 0 =:categoryId " +
            "and (lower(j.companyName) like lower(concat('%', :companyName ,'%')) or :companyName is null or :companyName ='')" +
            "and (lower(j.jobName) like lower(concat('%', :jobName ,'%')) or :jobName is null or :jobName ='')" +
            "and j.fromSalary =:fromSalary or 0 =:fromSalary " +
            "and j.toSalary =:toSalary or 0 =:toSalary " +
            "and (lower(j.rank) like lower(concat('%', :rank ,'%')) or :rank is null or :rank ='')" +
            "and (lower(j.workForm) like lower(concat('%', :workForm ,'%')) or :workForm is null or :workForm ='')" +
            "and (lower(j.workPlace) like lower(concat('%', :workPlace ,'%')) or :workPlace is null or :workPlace ='')" +
            "and (lower(j.techStackRequire) like lower(concat('%', :techStack ,'%')) or :techStack is null or :techStack ='')")
    Page<Job> searchListJobFilter(Pageable pageable, @Param("categoryId") long categoryId,@Param("companyName") String companyName,
                                  @Param("jobName") String jobName,@Param("fromSalary") long fromSalary,
                                  @Param("toSalary") long toSalary,@Param("rank") String rank, @Param("workForm") String workForm,
                                  @Param("workPlace") String workPlace,@Param("techStack") String techStack);

    @Query("update Job c set c.isDeleted = 1 where c.id =:jobId")
    void deleteJob(@Param("jobId") long jobId);
}
