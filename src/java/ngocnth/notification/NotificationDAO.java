/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.notification;

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
import ngocnth.article.ArticleDAO;
import ngocnth.status.StatusDAO;
import ngocnth.util.DBHelper;

/**
 *
 * @author Ruby
 */
public class NotificationDAO implements Serializable {
    
    public boolean addNotification(NotificationDTO dto)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "INSERT INTO tblNotification (userId, articleId, type, date) "
                        + "VALUES (?, ?, ?, ?)";
                stm = con.prepareCall(sql);
                stm.setString(1, dto.getUserId());
                stm.setInt(2, dto.getArticleId());
                stm.setInt(3, dto.getType());
                stm.setString(4, Date.valueOf(dto.getDate()).toString());
                return stm.executeUpdate() > 0;
            } 
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }
    
    public int countNewNotification(String userId)
            throws NamingException, SQLException {
        int result = 0;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT COUNT(n.id) AS num_new_notification "
                        + "FROM tblNotification AS n JOIN ("
                        +   "SELECT id, title "
                        +   "FROM tblArticle "
                        +   "WHERE userId = ? AND statusId = ?) AS a "
                        + "ON articleId = a.id "
                        + "JOIN tblUser ON userId = email "
                        + "WHERE status = 1";
                stm = con.prepareCall(sql);
                stm.setString(1, userId);
                StatusDAO dao = new StatusDAO();
                int statusId = dao.getStatus(ArticleDAO.STATUS_ACTIVE).getId();
                stm.setInt(2, statusId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return rs.getInt("num_new_notification");
                }
            } 
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return result;
    }
    
    public List<NotificationDetail> getNotification(String userId)
            throws NamingException, SQLException {
        List<NotificationDetail> list = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT n.id AS noId, name, articleId, title, type, status, date "
                        + "FROM tblNotification AS n JOIN ("
                        +   "SELECT id, title "
                        +   "FROM tblArticle "
                        +   "WHERE userId = ? AND statusId = ?) a "
                        + "ON articleId = a.id "
                        + "JOIN tblUser ON userId = email "
                        + "ORDER BY date DESC, status DESC";
                stm = con.prepareCall(sql);
                stm.setString(1, userId);
                StatusDAO dao = new StatusDAO();
                int statusId = dao.getStatus(ArticleDAO.STATUS_ACTIVE).getId();
                stm.setInt(2, statusId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("noId");
                    String name = rs.getString("name");
                    int articleId = rs.getInt("articleId");
                    String title = rs.getString("title");
                    int type = rs.getInt("type");
                    boolean status = rs.getBoolean("status");
                    LocalDate date = Date.valueOf(rs.getString("date")).toLocalDate().plusDays(2);
                    NotificationDetail detail = new NotificationDetail(name, articleId, title, type, status, date);
                    if (list == null) list = new ArrayList<>();
                    list.add(detail);
                    this.updateStatusNotification(id);
                }
            } 
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return list;
    }
    
    private boolean updateStatusNotification(int notificationId)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "UPDATE tblNotification "
                        + "SET status = 0 "
                        + "WHERE id = ?";
                stm = con.prepareCall(sql);
                stm.setInt(1, notificationId);
                return stm.executeUpdate() > 0;
            } 
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }
    
}
