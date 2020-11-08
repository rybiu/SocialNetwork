/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.comment;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Ruby
 */
public class CommentDTO implements Serializable {
    
    private int id;
    private String userId;
    private int articleId;
    private String description;
    private LocalDate date; 
    private boolean status;

    public CommentDTO() {
    }

    public CommentDTO(int id, String userId, int articleId, String description, LocalDate date, boolean status) {
        this.id = id;
        this.userId = userId;
        this.articleId = articleId;
        this.description = description;
        this.date = date;
        this.status = status;
    }

    public CommentDTO(String userId, int articleId, String description) {
        this.userId = userId;
        this.articleId = articleId;
        this.description = description;
        this.date = LocalDate.now();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
