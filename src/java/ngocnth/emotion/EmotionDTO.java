/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.emotion;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Ruby
 */
public class EmotionDTO implements Serializable {
    
    private int id;
    private String userId;
    private int articleId;
    private boolean type;
    private LocalDate date;
    private boolean status;

    public EmotionDTO() {
    }

    public EmotionDTO(int id, String userId, int articleId, boolean type, LocalDate date, boolean status) {
        this.id = id;
        this.userId = userId;
        this.articleId = articleId;
        this.type = type;
        this.date = date;
        this.status = status;
    }

    public EmotionDTO(String userId, int articleId, boolean type) {
        this.userId = userId;
        this.articleId = articleId;
        this.type = type;
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

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
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
