/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.article;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Ruby
 */
public class ArticleDTO implements Serializable {
    
    private int id;
    private String title;
    private String description;
    private String image;
    private LocalDate date;
    private boolean status;
    private String userId;

    public ArticleDTO() {
    }

    public ArticleDTO(int id, String title, String description, String image, LocalDate date, boolean status, String userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.date = date;
        this.status = status;
        this.userId = userId;
    }
    
    public ArticleDTO(int id, String title, String description, String image, LocalDate date, String userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.date = date;
        this.userId = userId;
    }
    
    public ArticleDTO(String title, String description, String image, String userId) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.date = LocalDate.now();
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDate getRealDate() {
        return date;
    }
    
    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
}
