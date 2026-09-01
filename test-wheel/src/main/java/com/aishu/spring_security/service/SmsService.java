package com.aishu.spring_security.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private static final Logger log = LoggerFactory.getLogger(SmsService.class);

    @Value("${twilio.phone-number}")
    private String fromPhone;

    public void sendOtp(String toPhone, String otp) {
        Message message = Message.creator(
                new PhoneNumber(toPhone),
                new PhoneNumber(fromPhone),
                "Your OTP code is: " + otp
        ).create();

        log.info("Sent OTP SID: {}", message.getSid());
    }

    public void sendOt(String phone, String otp) {
        log.info("Sending OTP {} to phone {}", otp, phone);
    }
}


