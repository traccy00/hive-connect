package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.Notification;
import fpt.edu.capstone.utils.ResponseDataPagination;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


public interface NotificationService {
    Notification insertNotification(Notification notification);

    ResponseDataPagination getAllNotificationByUserId(Integer pageNo, Integer pageSize, long userId);

}
