/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.article;

import java.io.Serializable;

/**
 *
 * @author Ruby
 */
public class ArticlePostError implements Serializable {
    
    private String titleLengthErr;
    private String descriptionLengthErr;

    public String getTitleLengthErr() {
        return titleLengthErr;
    }

    public void setTitleLengthErr(String titleLengthErr) {
        this.titleLengthErr = titleLengthErr;
    }

    public String getDescriptionLengthErr() {
        return descriptionLengthErr;
    }

    public void setDescriptionLengthErr(String descriptionLengthErr) {
        this.descriptionLengthErr = descriptionLengthErr;
    }

}
