package fpt.edu.capstone.entity;

import fpt.edu.capstone.utils.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "banner_active")
public class BannerActive extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "banner_id")
    private long bannerId;

    @Column(name = "display_position")
    private String displayPosition;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
