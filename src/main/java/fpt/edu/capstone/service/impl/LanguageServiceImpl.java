package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.atmpCandidate.Language;
import fpt.edu.capstone.repository.LanguageRepository;
import fpt.edu.capstone.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    LanguageRepository languageRepository;

    @Override
    public List<Language> getListLanguageByCvId(Long cvId) {
        return languageRepository.getListLanguageByCvId(cvId);
    }
}
