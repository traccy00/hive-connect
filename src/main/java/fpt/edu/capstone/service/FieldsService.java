package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.Fields;

import java.lang.reflect.Field;
import java.util.List;

public interface FieldsService {
    List<Fields> getAllField();

    boolean existById(long id);

    Fields getById(long id);
}
