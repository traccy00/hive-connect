package fpt.edu.capstone.entity;

import fpt.edu.capstone.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="image")
@Where(clause = "is_deleted=0")
@AllArgsConstructor
@NoArgsConstructor
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "company_id")
    private long companyId;

    @Column(name = "candidate_id")
    private long candidateId;

    @Column(name = "recruiter_id")
    private long recruiterId;

    @Column(name = "event_id")
    private long eventId;

    @Column(name = "candidate_post_id")
    private long candidatePostId;

    @Column(name = "recruiter_post_id")
    private long recruiterPostId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_deleted")
    private int isDeleted;

    @Column(name = "is_avatar")
    private boolean isAvatar;

    @Column(name = "is_banner")
    private boolean isBanner;
}
