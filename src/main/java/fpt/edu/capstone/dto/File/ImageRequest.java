package fpt.edu.capstone.dto.File;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImageRequest {

    private long userId;

//    private String id;

    private long companyId;

    private long eventId;

    private long candidatePostId;

    private long recruiterPostId;

    private boolean isAvatar;

    private boolean isBanner;

    private String type; //type Avatar || CompanyImage
}
