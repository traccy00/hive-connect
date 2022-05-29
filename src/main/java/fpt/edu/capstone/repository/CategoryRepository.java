package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.sprint1.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Query("select c from Category where c.")
    List<Category> findByName(String name);
}
