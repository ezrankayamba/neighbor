package tz.co.nezatech.neighbor.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
@Entity
@Table(name = "tbl_token")
public class Token extends BaseModel{
    private String token, ownerId;
    private Date expiryDate;

    public Token() {

    }

    public Token(String token, String ownerId, Date expiryDate) {
        this.token = token;
        this.ownerId = ownerId;
        this.expiryDate = expiryDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
