package fpt.edu.capstone.entity;

import fpt.edu.capstone.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "detail_package")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DetailPackage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "rental_package_id")
    private long rentalPackageId; //1

    //QUẢNG CÁO TUYỂN DỤNG
    @Column(name = "detail_name")
    private String detailName; //tên chi tiết các gói dịch vụ

    @Column(name = "price")
    private long price;

    @Column(name = "discount")
    private long discount;

    @Column(name = "time_expired")
    private String timeExpired;

    @Column(name = "description")
    private String description; // mô tả về gói khuyến mãi

//    @Column(name = "label")
    //Việc làm tốt nhất , việc làm nổi bật, việc làm hấp dẫn , none,
    // sẽ được hiển thị 1 nhãn dán nhỏ bên cạnh công việc, hiểu đơn giản như đi mua tủ lạnh, luôn có những nhán dán sale 50%... được dán ở góc bên phải của tủ lạnh
//    private String label;

   @Column(name = "is_related_job")
    private boolean isRelatedJob;

    @Column(name = "is_suggest_job")
    private boolean isSuggestJob;

//    @Column(name = "is_golden_hour")
//    private boolean goldenHour;
//
//    @Column(name = "is_normal_hour")
//    private boolean normalHour;
//
//    //GÓI MUA THÊM
//    @Column(name = "max_upload_six_image")
//    private boolean maxUploadSixImage;

//    @Column(name = "is_urgent")
//    private boolean isUrgent; // tin tuyển dụng được gán gấp vào tiêu đề //nếu type ( line 7 = GÓI MUA THÊM và isUrgent = true thì được gán GẤP vào tiêu đề)
//
//    @Column(name = "is_hot")
//    private boolean isHot;
//
//    @Column(name = "is_good_job")
//    private boolean isGoodJob;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "max_cv_view")
    private int maxCvView; // được xem full thông tin tối đa bao nhiêu cv
}
