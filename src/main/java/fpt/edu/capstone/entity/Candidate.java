package fpt.edu.capstone.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
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

    @Column(name = "list_tech_stack_id")
    private String listTechStackId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "gender")
    private boolean gender;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "social_id")
    private long socialId;

    @Column(name = "tap_history_id")
    private long tapHistoryId;

    @Column(name = "wish_list_id")
    private long wishListId;

    @Column(name = "search_history_id")
    private  long searchHistoryID;

    @Column(name = "cv_url")
    private  String cvUrl;

    @Column(name = "applied_job_id")
    private long appliedJobId;

    public Candidate(long userId, String listTechStackId, String fullName, String phoneNumber, boolean gender, Date birthDate, long socialId, long tapHistoryId, long wishListId, long searchHistoryID, String cvUrl, long appliedJobId) {
        this.userId = userId;
        this.listTechStackId = listTechStackId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDate = birthDate;
        this.socialId = socialId;
        this.tapHistoryId = tapHistoryId;
        this.wishListId = wishListId;
        this.searchHistoryID = searchHistoryID;
        this.cvUrl = cvUrl;
        this.appliedJobId = appliedJobId;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", userId=" + userId +
                ", listTechStackId='" + listTechStackId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender=" + gender +
                ", birthDate=" + birthDate +
                ", socialId=" + socialId +
                ", tapHistoryId=" + tapHistoryId +
                ", wishListId=" + wishListId +
                ", searchHistoryID=" + searchHistoryID +
                ", cvUrl='" + cvUrl + '\'' +
                ", appliedJobId=" + appliedJobId +
                '}';
    }
}
