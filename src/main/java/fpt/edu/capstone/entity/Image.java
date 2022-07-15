package fpt.edu.capstone.entity;

import fpt.edu.capstone.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
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
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "company_id")
    private long companyId;

    @Column(name = "candidate_post_id")
    private long candidatePostId;

    @Column(name = "recruiter_post_id")
    private long recruiterPostId;

    @Column(name = "is_deleted")
    private int isDeleted = 0;

    @Column(name = "is_avatar")
    private boolean isAvatar;

    @Column(name = "is_banner")
    private boolean isBanner;

    @Column(name = "event_id")
    private long eventId;

    @Column(name = "content_type")
    private String contentType;

    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "data")
    private byte[] data;

    @Column(name = "recruiter_id")
    private long recruiterId;
}
