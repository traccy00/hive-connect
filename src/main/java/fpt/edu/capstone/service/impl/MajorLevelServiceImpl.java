package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.MajorLevel;
import fpt.edu.capstone.repository.MajorLevelRepository;
import fpt.edu.capstone.service.MajorLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MajorLevelServiceImpl implements MajorLevelService {

    @Autowired
    MajorLevelRepository majorLevelRepository;

    @Override
    public List<MajorLevel> getListMajorLevelByCvId(Long cvId) {
        return majorLevelRepository.getListMajorLevelByCvId(cvId);
    }

    @Override
    public void insertNewMajorLevel(MajorLevel majorLevel) {
        majorLevelRepository.insertNewMajorLevel(majorLevel.getFiledId(), majorLevel.getMajorId(), majorLevel.getCvId(), majorLevel.getLevel(), majorLevel.isStatus());
    }

    @Override
    public MajorLevel getByCvId(long cvId) {
        return majorLevelRepository.getByCvId(cvId);
    }
}
