package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.sprint1.Admin;
import fpt.edu.capstone.entity.sprint1.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

}
