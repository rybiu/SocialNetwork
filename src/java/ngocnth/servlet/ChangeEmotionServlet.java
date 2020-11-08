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
import ngocnth.notification.NotificationDAO;
import ngocnth.notification.NotificationDTO;
import ngocnth.user.UserDTO;
import ngocnth.util.Constant;
import ngocnth.emotion.EmotionDAO;
import ngocnth.emotion.EmotionDTO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Ruby
 */
@WebServlet(name = "ChangeEmotionServlet", urlPatterns = {"/ChangeEmotionServlet"})
public class ChangeEmotionServlet extends HttpServlet {

    private final String DETAIL_PAGE = "DispatchServlet?btAction=View Detail&articleId=";
    private final String ERROR_PAGE = "error.html";
    private final String NOT_FOUND_PAGE = "404.html";
    
    private static final Logger LOGGER = LogManager.getLogger(ChangeEmotionServlet.class);
    
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
        String pk = request.getParameter("articleId");
        String type = request.getParameter("type");
        String url = ERROR_PAGE;
        try { 
            int articleId = Integer.parseInt(pk);
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER");
            EmotionDAO emotionDAO = new EmotionDAO();
            switch (type) {
                case "Like":
                    emotionDAO.deleteEmotion(user.getEmail(), articleId);
                    EmotionDTO dto = new EmotionDTO(user.getEmail(), articleId, Constant.EMOTION_LIKE);
                    emotionDAO.addEmotion(dto);
                    ArticleDAO articleDAO = new ArticleDAO();
                    ArticleDTO article = articleDAO.getArticle(articleId);
                    if (!article.getUserId().equals(user.getEmail())) {
                        NotificationDAO notificationDAO = new NotificationDAO();
                        notificationDAO.addNotification(
                                new NotificationDTO(user.getEmail(), articleId, Constant.NOTIFICATION_LIKE, dto.getDate()));
                    }
                    break;
                case "Dislike":
                    emotionDAO.deleteEmotion(user.getEmail(), articleId);
                    emotionDAO.addEmotion(new EmotionDTO(user.getEmail(), articleId, Constant.EMOTION_DISLIKE));
                    break;
                case "UnLike":
                case "UnDislike":
                    emotionDAO.deleteEmotion(user.getEmail(), articleId);
                    break;
            }
            url = DETAIL_PAGE + articleId;
        } catch (NumberFormatException ex){
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
