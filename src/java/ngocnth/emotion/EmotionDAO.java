/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.emotion;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import ngocnth.util.DBHelper;

/**
 *
 * @author Ruby
 */
public class EmotionDAO implements Serializable {
    
    public boolean addEmotion(EmotionDTO dto)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "INSERT INTO tblEmotion (userId, articleId, type, date) "
                        + "VALUES (?, ?, ?, ?)";
                stm = con.prepareCall(sql);
                stm.setString(1, dto.getUserId());
                stm.setInt(2, dto.getArticleId());
                stm.setBoolean(3, dto.isType());
                stm.setString(4, Date.valueOf(dto.getDate()).toString());
                return stm.executeUpdate() > 0;
            } 
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }
    
    public boolean deleteEmotion(String userId, int articleId)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "DELETE FROM tblEmotion "
                        + "WHERE userId = ? AND articleId = ?";
                stm = con.prepareCall(sql);
                stm.setString(1, userId);
                stm.setInt(2, articleId);
                return stm.executeUpdate() > 0;
            } 
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }
    
    public int countTimeEmotion(int articleId, boolean type)
            throws NamingException, SQLException {
        int result = 0;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT COUNT(*) AS count "
                        + "FROM tblEmotion "
                        + "WHERE articleId = ? AND type = ?";
                stm = con.prepareCall(sql);
                stm.setInt(1, articleId);
                stm.setBoolean(2, type);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("count");
                }
            } 
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return result;
    }
    
    public boolean hasEmotion(String userId, int articleId, boolean type)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT COUNT(*) AS count "
                        + "FROM tblEmotion "
                        + "WHERE userId = ? AND articleId = ? AND type = ?";
                stm = con.prepareCall(sql);
                stm.setString(1, userId);
                stm.setInt(2, articleId);
                stm.setBoolean(3, type);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return rs.getInt("count") > 0;
                }
            } 
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }
    
       
}
