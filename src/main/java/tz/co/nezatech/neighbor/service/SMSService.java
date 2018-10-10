package tz.co.nezatech.neighbor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import tz.co.nezatech.neighbor.model.SMSMessage;
import tz.co.nezatech.neighbor.repository.SMSMessageRepository;
import tz.co.nezatech.neighbor.sms.RequestResponseLoggingInterceptor;
import tz.co.nezatech.neighbor.sms.model.GatewayResponse;
import tz.co.nezatech.neighbor.sms.model.Recipient;
import tz.co.nezatech.neighbor.sms.model.SMSMessageData;

import java.util.List;

@Service
@PropertySource("classpath:sms.properties")
public class SMSService {
    @Value("${sms.africastalking.api.key}")
    private String apiKey;

    @Value("${sms.africastalking.api.username}")
    private String apiUsername;
    @Autowired
    SMSMessageRepository smsRepos;
    @Value("${sms.africastalking.api.url}")
    private String url;

    public int recordAndSendSMS(SMSMessage message) {
        SMSMessage savedMessage = smsRepos.save(message);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.set("apikey", apiKey);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("username", apiUsername);
            map.add("to", message.getMsisdn());
            map.add("message", message.getMessage());

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
            RestTemplate template = new RestTemplate();
            //template.setMessageConverters(List.of(new MappingJackson2HttpMessageConverter(), new FormHttpMessageConverter()));
            template.setInterceptors(List.of(new RequestResponseLoggingInterceptor()));
            String response = template.postForObject(url, request, String.class);
            System.out.println(response);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<GatewayResponse> typeReference = new TypeReference<>() {
            };
            GatewayResponse gr = mapper.readValue(response, typeReference);
            if (gr != null) {
                System.out.println("Successfully executed send sms API, read the response");
                SMSMessageData smsMessageData = gr.getSmsMessageData();
                List<Recipient> recipients = smsMessageData.getRecipients();
                if (recipients.size() > 0) {
                    Recipient r = recipients.get(0);
                    savedMessage.setMessageId(r.getMessageId());
                    savedMessage.setStatus(r.getStatusCode());
                    smsRepos.save(savedMessage);

                    if (List.of(100, 101, 102).contains(r.getStatusCode())) {
                        System.out.println("Sent or queued");
                        return 0;//success
                    }

                    System.err.println("Failed: " + r.getStatusCode());
                }
            } else {
                System.err.println("Failed to send SMS");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
