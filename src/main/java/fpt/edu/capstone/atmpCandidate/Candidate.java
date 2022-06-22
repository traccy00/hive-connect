package fpt.edu.capstone.atmpCandidate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "candidate")
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "gender")
    private boolean gender;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "search_history")
    private  String searchHistory;

    @Column(name = "country")
    private String country;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "social_link")
    private String socialLink;

    @Column(name = "wishlist_job_id_list")
    private String wishlistJobIdList;

    @Column(name = "tap_history_id_list")
    private String tapHistoryIdList;

    @Column(name = "is_need_job")
    private boolean isNeedJob;

    @Column(name = "experience_level")
    private String experienceLevel;

    @Column(name = "introduction")
    private String introduction;



}
