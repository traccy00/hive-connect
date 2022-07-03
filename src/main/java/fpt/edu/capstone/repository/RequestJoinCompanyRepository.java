package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.RequestJoinCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface RequestJoinCompanyRepository extends JpaRepository<RequestJoinCompany, Long> {

    @Query(value = "Select * from request_join_company where sender_id = ?1", nativeQuery = true)
    Optional<RequestJoinCompany> findBySenderId(long senderId);

    @Query(value = "Select * from request_join_company where approver_id = ?1", nativeQuery = true)
    Optional<List<RequestJoinCompany>> findByApproverId(long senderId);

    @Modifying
    @Transactional
    @Query(value = "Update request_join_company set status = ?1 where id = ?2", nativeQuery = true)
    void approveRequest(String status, long id);
}
