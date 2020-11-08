/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.article;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import ngocnth.comment.CommentDetail;

/**
 *
 * @author Ruby
 */
public class ArticleDetail implements Serializable {
    
    private String authorId;
    private String authorName;
    private String title;
    private String description;
    private String image;
    private LocalDate date;
    private int numberLike;
    private int numberDislike;
    private boolean like;
    private boolean dislike;
    private List<CommentDetail> comments;

    public ArticleDetail() {
    }

    public ArticleDetail(String authorId, String authorName, String title, String description, String image, LocalDate date, int numberLike, int numberDislike, boolean like, boolean dislike, List<CommentDetail> comments) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.title = title;
        this.description = description;
        this.image = image;
        this.date = date;
        this.numberLike = numberLike;
        this.numberDislike = numberDislike;
        this.like = like;
        this.dislike = dislike;
        this.comments = comments;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNumberLike() {
        return numberLike;
    }

    public void setNumberLike(int numberLike) {
        this.numberLike = numberLike;
    }

    public int getNumberDislike() {
        return numberDislike;
    }

    public void setNumberDislike(int numberDislike) {
        this.numberDislike = numberDislike;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isDislike() {
        return dislike;
    }

    public void setDislike(boolean dislike) {
        this.dislike = dislike;
    }

    public List<CommentDetail> getComments() {
        return comments;
    }

    public void setComments(List<CommentDetail> comments) {
        this.comments = comments;
    }

}
