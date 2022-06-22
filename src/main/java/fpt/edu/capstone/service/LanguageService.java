package fpt.edu.capstone.service;

import fpt.edu.capstone.atmpCandidate.Language;

import java.util.List;

public interface LanguageService {
    List<Language> getListLanguageByCvId(Long cvId);
}
