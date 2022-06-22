package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.atmpCandidate.CV;
import fpt.edu.capstone.atmpCandidate.Certificate;
import fpt.edu.capstone.repository.CVReposiroty;
import fpt.edu.capstone.repository.CertificateRepository;
import fpt.edu.capstone.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CertificateServiceImpl implements CertificateService {
    @Autowired
    CertificateRepository certificateRepository;

    @Override
    public List<Certificate> getListCertificateByCvId(Long cvId) {
        return certificateRepository.getListCertificateByCvId(cvId);
    }
}
