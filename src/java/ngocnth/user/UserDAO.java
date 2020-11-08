/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.user;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import ngocnth.status.StatusDAO;
import ngocnth.util.DBHelper;

/**
 *
 * @author Ruby
 */
public class UserDAO implements Serializable {
    
    private final String STATUS_NEW = "New";
    private final String STATUS_ACTIVE = "Active";
    
    private String encodePassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8)); 
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    public UserDTO checkLogin(String email, String password) 
            throws SQLException, NamingException, NoSuchAlgorithmException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String encodePassword = this.encodePassword(password);
                String sql = "SELECT u.name AS user_name, s.name AS status_name "
                        + "FROM ("
                        +   "SELECT name, statusId "
                        +   "FROM tblUser "
                        +   "WHERE email = ? AND password = ?"
                        + ") u JOIN tblStatus s ON statusId = s.id";
                stm = con.prepareCall(sql);
                stm.setString(1, email);
                stm.setString(2, encodePassword);
                rs = stm.executeQuery();
                if (rs.next()) {   
                    String username = rs.getString("user_name");
                    String statusName = rs.getString("status_name");
                    UserDTO dto = new UserDTO(email, username, password, statusName);
                    return dto;
                }
            } 
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return null;
    }
    
    public boolean createAccount(String email, String name, String password) 
            throws SQLException, NamingException, NoSuchAlgorithmException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "INSERT INTO tblUser (email, password, name, statusId) "
                        + "VALUES (?, ?, ?, ?)";
                stm = con.prepareCall(sql);
                stm.setString(1, email);
                stm.setString(2, this.encodePassword(password));
                stm.setString(3, name);
                StatusDAO dao = new StatusDAO();
                int statusId = dao.getStatus(STATUS_NEW).getId(); 
                stm.setInt(4, statusId);
                return stm.executeUpdate() > 0;
            } 
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }
    
    public boolean updateStatusAccount(String email) 
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "UPDATE tblUser "
                        + "SET statusId = ? "
                        + "WHERE email = ?";
                stm = con.prepareCall(sql);
                StatusDAO dao = new StatusDAO();
                int statusId = dao.getStatus(STATUS_ACTIVE).getId(); 
                stm.setInt(1, statusId);
                stm.setString(2, email);
                return stm.executeUpdate() > 0;
            } 
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }
    
}
