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
public class UserDTO implements Serializable {
    
    private String email;
    private String name;
    private String password;
    private String statusName;

    public UserDTO() {
    }

    public UserDTO(String email, String name, String password, String statusName) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.statusName = statusName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

}
