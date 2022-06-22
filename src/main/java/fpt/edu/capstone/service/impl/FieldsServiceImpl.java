package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Fields;
import fpt.edu.capstone.repository.FieldRepository;
import fpt.edu.capstone.service.FieldsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FieldsServiceImpl implements FieldsService {
    private final FieldRepository fieldRepository;
    @Override
    public List<Fields> getAll() {
        return fieldRepository.findAll();
    }
}