package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Query(value = "select c from Category c where (lower(c.name) like lower(concat('%', :name, '%')) or :name is null or :name='')")
    List <Category> searchCategoryList(@Param("name") String name);

    @Modifying
    @Query("update Category c set c.isDeleted = 1 where c.id =:categoryId")
    void deleteCategory(@Param("categoryId") long categoryId);
}
