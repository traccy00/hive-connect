package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("select p from Payment p where p.recruiterId =:recruiterId or 0 =:recruiterId " +
            "and p.detailPackageId =:rentalPackageId or 0 =:rentalPackageId " +
            "and p.bannerId =:bannerId or 0=:bannerId " +
            "and (lower(p.transactionCode) like lower(concat('%', :code ,'%')) or :code is null or :code ='')" +
            "and  (lower(p.orderType) like lower(concat('%', :type ,'%')) or :type is null or :type ='')")
    List<Payment> getListPaymentFilter(@Param("recruiterId") long recruiterId,@Param("rentalPackageId") long rentalPackageId,
                                       @Param("bannerId") long bannerId,@Param("code") String transactionCode,@Param("type") String orderType);

    @Query(value = "select * from Payment p order by created_at desc    ", nativeQuery = true)
    List<Payment> getListPaymentOrderByDate();

    List<Payment> findByRecruiterId(long recId);

    @Query(value = "select * from payment p where p.created_at >= :startDate and p.created_at < :endDate", nativeQuery = true)
    Page<Payment> getRevenueInMonth(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);
}
