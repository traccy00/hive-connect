package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Fields;
import fpt.edu.capstone.repository.FieldRepository;
import fpt.edu.capstone.repository.FieldsReposiroty;
import fpt.edu.capstone.service.FieldsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
@AllArgsConstructor
public class FieldsServiceImpl implements FieldsService {
    private final FieldsReposiroty fieldsReposiroty;
    @Override
    public List<Fields> getAllField(){
        return fieldsReposiroty.getAllField();
    }

    @Override
    public boolean existById(long id) {
        return fieldsReposiroty.existsById(id);
    }
}