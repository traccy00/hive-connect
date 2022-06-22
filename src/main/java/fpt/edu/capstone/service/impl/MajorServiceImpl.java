package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.repository.MajorRepository;
import fpt.edu.capstone.service.MajorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MajorServiceImpl implements MajorService {
    private final MajorRepository majorRepository;

    @Override
    public String getNameByMajorId(long majorId) {
        return majorRepository.getNameByMajorId(majorId);
    }
}
