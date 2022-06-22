package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.Language;

import java.util.List;

public interface LanguageService {
    List<Language> getListLanguageByCvId(Long cvId);
}
