/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ngocnth.article.ArticleDAO;
import ngocnth.article.ArticleDTO;
import ngocnth.comment.CommentDAO;
import ngocnth.comment.CommentDTO;
import ngocnth.notification.NotificationDAO;
import ngocnth.notification.NotificationDTO;
import ngocnth.user.UserDTO;
import ngocnth.util.Constant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Ruby
 */
@WebServlet(name = "PostCommentServlet", urlPatterns = {"/PostCommentServlet"})
public class PostCommentServlet extends HttpServlet {
    
    private final String VIEW_ARTICLE_PAGE = "DispatchServlet?btAction=View Detail&articleId=";
    private final String ERROR_PAGE = "error.html";
    private final String NOT_FOUND_PAGE = "404.html";
    
    private static final Logger LOGGER = LogManager.getLogger(PostCommentServlet.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String pk = request.getParameter("txtArticleId");
        String comment = request.getParameter("txtComment");
        String url = ERROR_PAGE;
        try {
            int articleId = Integer.parseInt(pk);
            ArticleDAO articleDAO = new ArticleDAO();
            ArticleDTO article = articleDAO.getArticle(articleId);
            if (article != null) {
                if (comment == null || comment.trim().isEmpty()) {
                    request.setAttribute("POST_COMMENT_ERROR", "The comment content must be not empty");
                    url = VIEW_ARTICLE_PAGE + pk;
                } else {
                    HttpSession session = request.getSession();
                    UserDTO user = (UserDTO) session.getAttribute("USER");
                    CommentDAO cmtDAO = new CommentDAO();
                    CommentDTO cmtDTO = new CommentDTO(user.getEmail(), articleId, comment);
                    boolean result = cmtDAO.addCommnent(cmtDTO);
                    if (result) {
                        if (!article.getUserId().equals(user.getEmail())) {
                            NotificationDAO notiDAO = new NotificationDAO();
                            NotificationDTO notiDTO = 
                                    new NotificationDTO(user.getEmail(), articleId, Constant.NOTIFICATION_COMMENT, cmtDTO.getDate());
                            notiDAO.addNotification(notiDTO);
                        }
                        url = VIEW_ARTICLE_PAGE + pk;
                    }
                }
            }
        } catch (NumberFormatException ex) {
            url = NOT_FOUND_PAGE;
        } catch (NamingException ex) {
            LOGGER.error("NamingException", ex);
        } catch (SQLException ex) {
            LOGGER.error("SQLException", ex);
        } finally {
            if (url.equals(NOT_FOUND_PAGE) || url.equals(ERROR_PAGE)) {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                response.sendRedirect(url);
            }
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
