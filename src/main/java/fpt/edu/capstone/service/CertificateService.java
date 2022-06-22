package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.Certificate;

import java.util.List;

public interface CertificateService {

    List<Certificate> getListCertificateByCvId(Long cvId);
}
