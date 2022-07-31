package fpt.edu.capstone.repository;

import fpt.edu.capstone.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = "Select * from notification WHERE receiver_id = ?1", nativeQuery = true)
    Page<Notification> getAllNotificationByUserId(Pageable pageable, long userId);
}
