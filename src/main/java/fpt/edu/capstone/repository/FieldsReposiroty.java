package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Fields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.lang.reflect.Field;
import java.util.List;

public interface FieldsReposiroty extends JpaRepository<Fields, Long> {
    @Query(value = "SELECT * FROM public.fields", nativeQuery = true)
    List<Fields> getAllField();

    Fields getById(long id);
}
