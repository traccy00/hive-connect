package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.Certificate;
import fpt.edu.capstone.swagger.OpenAPIConfig;

import java.util.List;
import java.util.Optional;

public interface CertificateService {

    List<Certificate> getListCertificateByCvId(Long cvId);

    Certificate insertCertificate(Certificate newCertificate);

    void updateService(Certificate certificate);

    void deleteCertificate(Certificate certificate);

    Optional<Certificate> getCertificateById(long id);
}
