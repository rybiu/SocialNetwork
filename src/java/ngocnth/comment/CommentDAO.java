/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.comment;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import ngocnth.util.DBHelper;

/**
 *
 * @author Ruby
 */
public class CommentDAO implements Serializable {
    
    public boolean addCommnent(CommentDTO dto)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "INSERT INTO tblComment (userId, articleId, description, date) "
                        + "VALUES (?, ?, ?, ?)";
                stm = con.prepareCall(sql);
                stm.setString(1, dto.getUserId());
                stm.setInt(2, dto.getArticleId());
                stm.setString(3, dto.getDescription());
                stm.setString(4, Date.valueOf(dto.getDate()).toString());
                return stm.executeUpdate() > 0;
            } 
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }
    
    public boolean deleteCommnent(int commentId, String userId)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "UPDATE tblComment "
                        + "SET status = 0 "
                        + "WHERE id = ? AND userId = ?";
                stm = con.prepareCall(sql);
                stm.setInt(1, commentId);
                stm.setString(2, userId);
                return stm.executeUpdate() > 0;
            } 
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }
    
    public List<CommentDetail> getComment(int articleId)
            throws NamingException, SQLException {
        List<CommentDetail> list = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT id, userId, name, description, date "
                        + "FROM ("
                        +   "SELECT id, userId, description, date "
                        +   "FROM tblComment "
                        +   "WHERE articleId = ? AND status = 1"
                        + ") c JOIN tblUser ON userId = email";
                stm = con.prepareCall(sql);
                stm.setInt(1, articleId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String userId = rs.getString("userId");
                    String username = rs.getString("name");
                    String description = rs.getString("description");
                    LocalDate date = rs.getDate("date").toLocalDate().plusDays(2);
                    CommentDetail detail = new CommentDetail(id, userId, username, description, date);
                    if (list == null) list = new ArrayList<>();
                    list.add(detail);
                }
            } 
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return list;
    }
}
