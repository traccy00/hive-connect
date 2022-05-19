package fpt.edu.capstone.entity;

import fpt.edu.capstone.utils.BaseEntity;

public class Job extends BaseEntity {
    //Thieu location
    private long id;
    private long categoryId;
    private long companyId;
    private String jobName;
    private String jobDescription;
    private String salary;
    private long numberRecruitments; // tuyển dụng số lượng bao nhiêu người.
    private String rank; // tuyển dụng cấp bậc nào
    private String workForm; // parttime, fulltime, remote, ctv...
    private String experience; // yêu cầu số năm kinh nghiệm
    private boolean gender; // male, female, ko yêu cầu...
    private String workAddress;
    private long startDate; // ngày tuyển dụng
    private long endDate; // ngày kết thúc tuyển dụng
    private String workPlace;
    /*
       id long [pk, increment]
   user_id long
   type_of_work varchar
   position varchar
   experience varchar
   workplace_id long
   salary_from varchar
   salary_to varchar //Mức lương (Loại, Kiểu lương, Từ, Đến)???
   post_view long //lượt tiếp cận
   wishlist varchar //lượt quan tâm, yêu thích
     */


}
