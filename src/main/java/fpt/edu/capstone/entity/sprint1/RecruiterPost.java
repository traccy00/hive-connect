package fpt.edu.capstone.entity.sprint1;

import fpt.edu.capstone.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "recruiter_post")
@Where(clause = "is_deleted=0")
@AllArgsConstructor
@NoArgsConstructor
public class RecruiterPost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "category_id")
    private long categoryId; //IT, Kế toán, Saleman, ....

    @Column(name = "recruiter_id")
    private long recruiterId;

    @Column(name = "company_id")
    private long companyId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "job_description")
    private String jobDescription;

    @Column(name = "from_salary")
    private long fromSalary;

    @Column(name = "to_salary")
    private long toSalary;     //thuận tiện cho việc sort lương

    @Column(name = "number_recruits")
    private long numberRecruits; // tuyển dụng số lượng bao nhiêu người.

    @Column(name = "rank")
    private String rank; // tuyển dụng cấp bậc nào

    @Column(name = "work_form")
    private String workForm; // parttime, fulltime, remote, ctv...

    @Column(name = "experience")
    private String experience; // yêu cầu số năm kinh nghiệm

    @Column(name = "start_date")
    private LocalDateTime startDate; // ngày tuyển dụng

    @Column(name = "end_date")
    private LocalDateTime endDate; // ngày kết thúc tuyển dụng

    @Column(name = "work_place")
    private String workPlace;

    @Column(name = "job_view_count")
    private long jobViewCount;

    @Column(name = "tech_stack_require")
    private String techStackRequire;

    @Column(name = "is_deleted")
    private int isDeleted;

    @Column(name = "hashtag")
    private String hashtag;

    @Column(name = "title")
    private String title;

    @Column(name = "map_url")
    private String mapUrl;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

}
