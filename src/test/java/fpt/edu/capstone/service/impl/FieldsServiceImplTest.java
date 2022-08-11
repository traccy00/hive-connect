package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Fields;
import fpt.edu.capstone.repository.FieldsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FieldsServiceImplTest {

    @Mock
    private FieldsRepository mockFieldsRepository;

    private FieldsServiceImpl fieldsServiceImplUnderTest;

    @Before
    public void setUp() throws Exception {
        fieldsServiceImplUnderTest = new FieldsServiceImpl(mockFieldsRepository);
    }

    @Test
    public void testGetAllField() {
        final List<Fields> list = Arrays.asList(new Fields(0L, "fieldName", "status"));
        when(mockFieldsRepository.getAllField()).thenReturn(list);
        final List<Fields> result = fieldsServiceImplUnderTest.getAllField();
    }

    @Test
    public void testGetAllField_FieldsRepositoryReturnsNoItems() {
        when(mockFieldsRepository.getAllField()).thenReturn(Collections.emptyList());
        final List<Fields> result = fieldsServiceImplUnderTest.getAllField();
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testExistById() {
        when(mockFieldsRepository.existsById(0L)).thenReturn(false);
        final boolean result = fieldsServiceImplUnderTest.existById(0L);
        assertThat(result).isFalse();
    }

    @Test
    public void testGetById() {
        when(mockFieldsRepository.getById(0L)).thenReturn(new Fields(0L, "fieldName", "status"));
        final Fields result = fieldsServiceImplUnderTest.getById(0L);
    }

    @Test
    public void testFindById() {
        final Optional<Fields> fields = Optional.of(new Fields(0L, "fieldName", "status"));
        when(mockFieldsRepository.findById(0L)).thenReturn(fields);
        final Optional<Fields> result = fieldsServiceImplUnderTest.findById(0L);
    }

    @Test
    public void testFindById_FieldsRepositoryReturnsAbsent() {
        when(mockFieldsRepository.findById(0L)).thenReturn(Optional.empty());
        final Optional<Fields> result = fieldsServiceImplUnderTest.findById(0L);
        assertThat(result).isEmpty();
    }
}
