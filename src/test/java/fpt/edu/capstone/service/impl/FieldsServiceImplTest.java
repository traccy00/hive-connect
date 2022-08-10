package fpt.edu.capstone.service.impl;

import com.amazonaws.services.apigateway.model.Op;
import fpt.edu.capstone.entity.Fields;
import fpt.edu.capstone.repository.FieldsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
public class FieldsServiceImplTest {
    @InjectMocks
    FieldsServiceImpl fieldsService;

    @Mock
    FieldsRepository fieldsRepository;

    @Test
    void getAllFieldTest(){
        List<Fields> list = new ArrayList<>();
        Mockito.when(fieldsRepository.getAllField()).thenReturn(list);
        list = fieldsService.getAllField();
        assertEquals(0, list.size());
    }

    @Test
    void existByIdTest(){
        Fields fields = new Fields();
        fields.setId(1L);
        Mockito.when(fieldsRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
        boolean res = fieldsService.existById(1L);
        assertEquals(true, res);
    }

    @Test
    void getByIdTest(){
        Fields fields = new Fields();
        fields.setId(1L);
        Mockito.when(fieldsRepository.getById(ArgumentMatchers.anyLong())).thenReturn(fields);
        Fields res = fieldsService.getById(1L);
        assertEquals(1L, res.getId());
    }

    @Test
    void findByIdTest(){
        Fields fields = new Fields();
        fields.setId(1L);
        Mockito.when(fieldsRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(fields));
        Optional<Fields> res = fieldsService.findById(1L);
        assertEquals(1L, res.get().getId());
    }
}
