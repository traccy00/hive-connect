package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.Notification;
import fpt.edu.capstone.repository.NotificationRepository;
import fpt.edu.capstone.service.NotificationService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification insertNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public ResponseDataPagination getAllNotificationByUserId(Integer pageNo, Integer pageSize, long userId) {

        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);
        Page<Notification> notifications = notificationRepository.getAllNotificationByUserId(pageable, userId);

        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(notifications.getContent());
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(notifications.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(notifications.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;

    }
}
