package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.MajorLevel;

import java.util.List;

public interface MajorLevelService {
    List<MajorLevel> getListMajorLevelByCvId(Long cvId);

    void insertNewMajorLevel(MajorLevel majorLevel);

    MajorLevel getByCvId(long cvId);
}
