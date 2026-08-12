package com.aishu.spring_security.service;


import com.aishu.spring_security.Repository.NotificationRepository;
import com.aishu.spring_security.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void addSignupNotification(String username) {
        Notification note = new Notification();
        note.setTitle("New User Signup");
        note.setMessage("User " + username + " signed up successfully");
        note.setTimeAgo(LocalDateTime.now()); // later you can calculate dynamically
        notificationRepository.save(note);
    }

    public List<Notification> getLatest() {
        // Example: fetch last 10 notifications, ordered by timestamp
        return notificationRepository.findTop5ByOrderByTimeAgoDesc();
    }
}
