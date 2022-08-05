package fpt.edu.capstone.dto.File;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImageRequest {

    private long userId;

    private long companyId;

    private long eventId;

    private boolean isAvatar;

    private boolean isBanner;

    private String type; //type Avatar || CompanyImage
}
