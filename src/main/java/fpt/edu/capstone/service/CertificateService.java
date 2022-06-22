package fpt.edu.capstone.service;

import fpt.edu.capstone.atmpCandidate.CV;
import fpt.edu.capstone.atmpCandidate.Certificate;

import java.util.List;

public interface CertificateService {

    List<Certificate> getListCertificateByCvId(Long cvId);
}
