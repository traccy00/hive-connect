package fpt.edu.capstone.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reported")
@Getter
@Setter
public class Reported {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "reported_reason")
    private String reportedReason;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "person_report")
    private long personReport;

    @Column(name = "post_id")
    private long postId;

    @Column(name = "related_link")
    private String relatedLink;

    @Column(name = "approval_reported_status")
    private String approvalReportedStatus;
}
