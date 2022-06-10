package fpt.edu.capstone.entity.sprint1;

import fpt.edu.capstone.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "applied_job")
public class AppliedJob extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "job_id")
    private long jobId;

    @Column(name = "candidate_id")
    private long candidateId;

    @Column(name = "is_applied")
    private boolean isApplied;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //approved, rejected
    @Length(max = 15)
    @Column(name = "approval_status")
    private String approvalStatus;
}
