package fpt.edu.capstone.entity.sprint1;

import fpt.edu.capstone.utils.BaseEntity;

import java.util.List;

public class Job extends BaseEntity {
    private long id;
    private long categoryId; //IT, Kế toán, Saleman, ....
    private long companyId;
    private String jobName;
    private String jobDescription;
    private long fromSalary;
    private long toSalary;     //thuận tiện cho việc sort lương
    private long numberRecruitments; // tuyển dụng số lượng bao nhiêu người.
    private String rank; // tuyển dụng cấp bậc nào
    private String workForm; // parttime, fulltime, remote, ctv...
    private String experience; // yêu cầu số năm kinh nghiệm
    private boolean gender; // male, female, ko yêu cầu...
    private String workAddress;
    private long startDate; // ngày tuyển dụng
    private long endDate; // ngày kết thúc tuyển dụng
    private String workPlace;
    private long jobViewCount;
}
