package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.Image;
import fpt.edu.capstone.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface ImageService {
    boolean isValidFile(MultipartFile file, String type);

    Image saveCompanyAvatar(MultipartFile file, String type, long companyId) throws IOException;

    Optional<Image> getFile(long id);

    List<Image> getAllFiles();

    Optional<Image> findAvatarByCompanyId(long companyId);

    void updateAvatar(String id, MultipartFile file) throws IOException;

    Optional<Image> finById(long id);

    Image getAvatarRecruiter(long recruiterId);

    Image getAvatarCandidate(long candidateId);

}
