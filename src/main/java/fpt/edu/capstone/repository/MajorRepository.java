package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MajorRepository extends JpaRepository<Major,Long> {
    @Query("select m.major_name from Major m where m.id =:majorId")
    String getNameByMajorId(@Param("majorId") long majorId);
}
