/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.notification;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Ruby
 */
public class NotificationDetail implements Serializable {
    
    private int id;
    private String username;
    private int articleId;
    private String articleTitle;
    private int type;
    private boolean status;
    private LocalDate date; 

    public NotificationDetail() {
    }

    public NotificationDetail(int id, String username, int articleId, String articleTitle, int type, boolean status, LocalDate date) {
        this.id = id;
        this.username = username;
        this.articleId = articleId;
        this.articleTitle = articleTitle;
        this.type = type;
        this.status = status;
        this.date = date;
    }
    
    public NotificationDetail(String username, int articleId, String articleTitle, int type, boolean status, LocalDate date) {
        this.username = username;
        this.articleId = articleId;
        this.articleTitle = articleTitle;
        this.type = type;
        this.status = status;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
