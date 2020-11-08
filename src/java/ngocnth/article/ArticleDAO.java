/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.article;

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
import ngocnth.comment.CommentDAO;
import ngocnth.comment.CommentDetail;
import ngocnth.util.DBHelper;
import ngocnth.emotion.EmotionDAO;
import ngocnth.status.StatusDAO;

/**
 *
 * @author Ruby
 */
public class ArticleDAO implements Serializable {
    
    public static final String STATUS_ACTIVE = "Active";
    public static final String STATUS_DELETE = "Delete";
    
    public boolean addArticle(ArticleDTO dto)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "INSERT INTO tblArticle (title, description, image, userId, date, statusId) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";
                stm = con.prepareCall(sql);
                stm.setString(1, dto.getTitle());
                stm.setString(2, dto.getDescription());
                stm.setString(3, dto.getImage());
                stm.setString(4, dto.getUserId());
                stm.setString(5, Date.valueOf(dto.getRealDate()).toString());
                StatusDAO dao = new StatusDAO();
                int statusId = dao.getStatus(STATUS_ACTIVE).getId();
                stm.setInt(6, statusId);
                return stm.executeUpdate() > 0;
            } 
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }
    
    public boolean deleteArticle(int articleId, String userId)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "UPDATE tblArticle "
                        + "SET statusId = ? "
                        + "WHERE id = ? AND userId = ?";
                stm = con.prepareCall(sql);
                StatusDAO dao = new StatusDAO();
                int statusId = dao.getStatus(STATUS_DELETE).getId();
                stm.setInt(1, statusId);
                stm.setInt(2, articleId);
                stm.setString(3, userId);
                return stm.executeUpdate() > 0;
            } 
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }
    
    public ArticleDTO getArticle(int articleId)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT title, description, image, date, userId "
                        + "FROM tblArticle "
                        + "WHERE id = ? AND statusId = ?";
                stm = con.prepareCall(sql);
                stm.setInt(1, articleId);
                StatusDAO dao = new StatusDAO();
                int statusId = dao.getStatus(STATUS_ACTIVE).getId();
                stm.setInt(2, statusId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    String image = rs.getString("image");
                    LocalDate date = Date.valueOf(rs.getString("date")).toLocalDate().plusDays(2);
                    String userId = rs.getString("userId");
                    ArticleDTO dto = new ArticleDTO(articleId, title, description, image, date, userId);
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
    
    public List<ArticleDTO> getArticle(String searchValue, int start, int row)
            throws NamingException, SQLException {
        List<ArticleDTO> list = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT id, title, description, image, date, userId "
                        + "FROM tblArticle "
                        + "WHERE title LIKE ? AND statusId = ? "
                        + "ORDER BY date " 
                        + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                stm = con.prepareCall(sql);
                stm.setString(1, "%" + searchValue + "%");
                StatusDAO dao = new StatusDAO();
                int statusId = dao.getStatus(STATUS_ACTIVE).getId();
                stm.setInt(2, statusId);
                stm.setInt(3, start);
                stm.setInt(4, row);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    String image = rs.getString("image");
                    LocalDate date = Date.valueOf(rs.getString("date")).toLocalDate().plusDays(2);
                    String userId = rs.getString("userId");
                    ArticleDTO detail = new ArticleDTO(id, title, description, image, date, userId);
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
    
    public int getArticleCount(String searchValue)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT COUNT(*) AS count "
                        + "FROM tblArticle "
                        + "WHERE title LIKE ? AND statusId = ?";
                stm = con.prepareCall(sql);
                stm.setString(1, "%" + searchValue + "%");
                StatusDAO dao = new StatusDAO();
                int statusId = dao.getStatus(STATUS_ACTIVE).getId();
                stm.setInt(2, statusId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return rs.getInt("count");
                }
            } 
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return 0;
    }
    
    public ArticleDetail getArticleDetail(int articleId, String userId)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT title, description, image, date, name, email "
                        + "FROM ("
                        +   "SELECT title, description, image, date, userId "
                        +   "FROM tblArticle "
                        +   "WHERE id = ? AND statusId = ?"
                        + ") a JOIN tblUser ON a.userId = email";
                stm = con.prepareCall(sql);
                stm.setInt(1, articleId);
                StatusDAO dao = new StatusDAO();
                int statusId = dao.getStatus(STATUS_ACTIVE).getId();
                stm.setInt(2, statusId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    String image = rs.getString("image");
                    LocalDate date = Date.valueOf(rs.getString("date")).toLocalDate().plusDays(2);
                    String authorName = rs.getString("name");
                    String authorId = rs.getString("email");
                    EmotionDAO emotionDAO = new EmotionDAO();
                    int numberLike = emotionDAO.countTimeEmotion(articleId, true);
                    int numberUnlike = emotionDAO.countTimeEmotion(articleId, false);
                    boolean like = emotionDAO.hasEmotion(userId, articleId, true);
                    boolean unlike = emotionDAO.hasEmotion(userId, articleId, false);
                    CommentDAO cmtDAO = new CommentDAO();
                    List<CommentDetail> comments = cmtDAO.getComment(articleId);
                    return new ArticleDetail(authorId, authorName, title, description, image, date, numberLike, numberUnlike, like, unlike, comments);
                }
            } 
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return null;
    }
    
    public int getLastedArticleId()
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT MAX(id) AS id "
                        + "FROM tblArticle";
                stm = con.prepareCall(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            } 
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return -1;
    }
    
}
