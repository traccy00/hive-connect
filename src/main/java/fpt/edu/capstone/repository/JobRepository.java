package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.sprint1.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
