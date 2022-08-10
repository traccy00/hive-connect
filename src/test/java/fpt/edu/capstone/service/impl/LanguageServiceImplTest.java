package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Language;
import fpt.edu.capstone.repository.LanguageRepository;
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
public class LanguageServiceImplTest {
    @InjectMocks
    LanguageServiceImpl languageService;

    @Mock
    LanguageRepository languageRepository;

    @Test
    void getListLanguageByCvIdTest(){
        List<Language> list = new ArrayList<>();
        for (Language l :list) {
            l.setId(1L);
            l.setCvId(1L);
            l.setLanguage("English");
            l.setLevel("Intermediate");
            list.add(l);
        }
        Mockito.when(languageRepository.getListLanguageByCvId(ArgumentMatchers.anyLong())).thenReturn(list);
        list = languageService.getListLanguageByCvId(1L);
        assertEquals(0, list.size());
    }

    @Test
    void insertLanguageTest(){
        Language l = new Language();
        l.setId(1L);
        l.setCvId(1L);
        l.setLanguage("English");
        l.setLevel("Intermediate");
        Mockito.when(languageRepository.save(ArgumentMatchers.any(Language.class))).thenReturn(l);
        l = languageService.insertLanguage(l);
        assertEquals(1L, l.getId());
    }

    @Test
    void deleteLanguageTest(){
        Language l = new Language();
        l.setId(1L);
        l.setCvId(1L);
        l.setLanguage("English");
        l.setLevel("Intermediate");
        languageService.deleteLanguage(l);
    }

    @Test
    void updateLanguageTest(){
        Language l = new Language();
        l.setId(1L);
        l.setCvId(1L);
        l.setLanguage("English");
        l.setLevel("Intermediate");
        languageService.updateLanguage(l);
    }

    @Test
    void getLanguageByIdTest(){
        Language l = new Language();
        l.setId(1L);
        l.setCvId(1L);
        l.setLanguage("English");
        l.setLevel("Intermediate");
        Mockito.when(languageRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(l));
        Optional <Language> ll = languageService.getLanguageById(1L);
        assertEquals(1L, ll.get().getId());
    }
}
