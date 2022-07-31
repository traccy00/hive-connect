package fpt.edu.capstone.dto.recruiter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BannerPositionDetailResponse {
    private String position;
    private String imageUrl;
    private String approvalStatus;
}
