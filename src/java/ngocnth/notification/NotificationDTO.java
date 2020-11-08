/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.notification;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Ruby
 */
public class NotificationDTO implements Serializable {
    
    private int id;
    private String userId;
    private int articleId;
    private int type;
    private LocalDate date;
    private boolean status;

    public NotificationDTO() {
    }

    public NotificationDTO(int id, String userId, int articleId, int type, LocalDate date, boolean status) {
        this.id = id;
        this.userId = userId;
        this.articleId = articleId;
        this.type = type;
        this.date = date;
        this.status = status;
    }
    
    public NotificationDTO(String userId, int articleId, int type, LocalDate date) {
        this.userId = userId;
        this.articleId = articleId;
        this.type = type;
        this.date = date;
    }    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
   
}
