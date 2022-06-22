package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Fields;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldRepository extends JpaRepository<Fields,Long> {
}
