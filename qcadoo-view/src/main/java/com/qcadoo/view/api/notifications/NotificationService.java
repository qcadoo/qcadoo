package com.qcadoo.view.api.notifications;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private List<NotificationDataComponent> notificationDataComponents;

    public NotificationContainer getNotification() {
        NotificationContainer notificationContainer = new NotificationContainer();
        List<Notification> notifications = Lists.newArrayList();
        for (NotificationDataComponent notificationDataComponent : notificationDataComponents) {
            Optional<Notification> maybeNotification = notificationDataComponent.registerNotification();
            if (maybeNotification.isPresent()) {
                notifications.add(maybeNotification.get());
            }
        }
        boolean playSound = notifications.stream().filter(notification -> notification.isSound()).count() > 0 ? true : false;
        notificationContainer.setSound(playSound);
        notificationContainer.setNotifications(notifications);
        return notificationContainer;
    }
}
