package tz.co.nezatech.neighbor.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_sms")
public class SMSMessage extends BaseModel {
    private String message, msisdn, messageId;
    private int status;

    public SMSMessage(String message, String msisdn) {
        this.message = message;
        this.msisdn = msisdn;
        this.status = 10;//created
    }

    public SMSMessage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
