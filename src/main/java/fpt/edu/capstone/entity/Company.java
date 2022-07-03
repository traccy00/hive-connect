package fpt.edu.capstone.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "companies")
@Setter
@Getter
public class Company {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "field_work")
    private String fieldWork;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "description")
    private String description;

    @Column(name = "website")
    private String website;

    @Column(name = "number_employees")
    private String numberEmployees; //số lượng nhân viên đang có trong công ty

    @Column(name = "address")
    private String address;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "taxcode")
    private String taxCode;

    @Column(name = "is_deleted")
    private int isDeleted = 0;

    @Column(name = "map_url")
    private String mapUrl;

    @Column(name = "creator_id") //Id của thằng recruiter tạo công ty
    private long creatorId;
}
