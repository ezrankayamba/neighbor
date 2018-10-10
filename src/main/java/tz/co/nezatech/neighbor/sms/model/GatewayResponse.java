package tz.co.nezatech.neighbor.sms.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GatewayResponse {
    @JsonProperty("SMSMessageData")
    private SMSMessageData smsMessageData;

    public GatewayResponse() {
    }

    public SMSMessageData getSmsMessageData() {
        return smsMessageData;
    }

    public void setSmsMessageData(SMSMessageData smsMessageData) {
        this.smsMessageData = smsMessageData;
    }
}
