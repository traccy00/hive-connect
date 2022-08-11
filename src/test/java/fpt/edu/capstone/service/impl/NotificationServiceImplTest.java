package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Notification;
import fpt.edu.capstone.repository.NotificationRepository;
import fpt.edu.capstone.utils.ResponseDataPagination;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceImplTest {

    @Mock
    private NotificationRepository mockNotificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationServiceImplUnderTest;

    private Notification notificationA = new Notification(0L, 0L, 0L,
            LocalDateTime.now(), "content", false, false);
    @Test
    public void testInsertNotification() {
        final Notification notification = notificationA;
        final Notification notification1 = notificationA;
        when(mockNotificationRepository.save(any(Notification.class))).thenReturn(notification1);
        final Notification result = notificationServiceImplUnderTest.insertNotification(notification);
    }

    @Test
    public void testGetAllNotificationByUserId() {
        final Page<Notification> notifications = new PageImpl<>(Arrays.asList(notificationA));
        when(mockNotificationRepository.getAllNotificationByUserId(any(Pageable.class), eq(0L)))
                .thenReturn(notifications);
        final ResponseDataPagination result = notificationServiceImplUnderTest.getAllNotificationByUserId(1, 10, 0L);
    }

    @Test
    public void testGetAllNotificationByUserId_NotificationRepositoryReturnsNoItems() {
        when(mockNotificationRepository.getAllNotificationByUserId(any(Pageable.class), eq(0L)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        final ResponseDataPagination result = notificationServiceImplUnderTest.getAllNotificationByUserId(1, 10, 0L);
    }
}
