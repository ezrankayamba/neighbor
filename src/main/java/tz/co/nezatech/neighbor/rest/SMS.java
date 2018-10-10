package tz.co.nezatech.neighbor.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import tz.co.nezatech.neighbor.model.SMSMessage;

@RestController
@RequestMapping("/api/sms")
@PropertySource("classpath:sms.properties")
public class SMS {
    @Value("${sms.africastalking.api.key}")
    private String apiKey;

    @Value("${sms.africastalking.api.username}")
    private String apiUsername;

    @PostMapping("/send")
    public ApiResponse sendSMS(@RequestBody SMSMessage smsMessage) {
        ApiResponse resp = new ApiResponse(0, "Successfully sent SMS");


        return resp;
    }
}
