package com.aishu.spring_security.controller;

import com.aishu.spring_security.model.Notification;
import com.aishu.spring_security.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class notificationController {

    private static final Logger log = LoggerFactory.getLogger(notificationController.class);

    @Autowired
    NotificationService notificationService;

    @GetMapping("/notifications")
    @ResponseBody
    public List<Notification> getNotifications() {

        return notificationService.getLatest();
    }



    @MessageMapping("/sendMessage")
    @SendTo("/topic/notifications")
    public String sendMessage(String message){
        log.info("message: {}", message);
        return message;
    }


}
