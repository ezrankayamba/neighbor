package tz.co.nezatech.neighbor.sms.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SMSMessageData {
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Recipients")
    private List<Recipient> recipients;

    public SMSMessageData() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Recipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Recipient> recipients) {
        this.recipients = recipients;
    }
}