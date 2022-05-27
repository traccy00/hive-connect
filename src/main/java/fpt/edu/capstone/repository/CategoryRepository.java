package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.sprint1.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
