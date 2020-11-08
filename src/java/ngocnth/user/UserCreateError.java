/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.user;

import java.io.Serializable;

/**
 *
 * @author Ruby
 */
public class UserCreateError implements Serializable {
    
    private String emailFormatErr;
    private String passwordLengthErr;
    private String confirmFormatErr;
    private String nameLengthErr;
    private String emailIsExisted;

    public String getEmailFormatErr() {
        return emailFormatErr;
    }

    public void setEmailFormatErr(String emailFormatErr) {
        this.emailFormatErr = emailFormatErr;
    }

    public String getPasswordLengthErr() {
        return passwordLengthErr;
    }

    public void setPasswordLengthErr(String passwordLengthErr) {
        this.passwordLengthErr = passwordLengthErr;
    }

    public String getConfirmFormatErr() {
        return confirmFormatErr;
    }

    public void setConfirmFormatErr(String confirmFormatErr) {
        this.confirmFormatErr = confirmFormatErr;
    }

    public String getNameLengthErr() {
        return nameLengthErr;
    }

    public void setNameLengthErr(String nameLengthErr) {
        this.nameLengthErr = nameLengthErr;
    }

    public String getEmailIsExisted() {
        return emailIsExisted;
    }

    public void setEmailIsExisted(String emailIsExisted) {
        this.emailIsExisted = emailIsExisted;
    }

}
