/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import ngocnth.article.ArticleDAO;
import ngocnth.article.ArticleDTO;
import ngocnth.article.ArticlePostError;
import ngocnth.notification.NotificationDAO;
import ngocnth.user.UserDTO;
import ngocnth.util.Constant;
import ngocnth.util.FileHeper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Ruby
 */
@WebServlet(name = "PostArticleServlet", urlPatterns = {"/PostArticleServlet"})
@MultipartConfig()
public class PostArticleServlet extends HttpServlet {
    
    private final String VIEW_ARTICLE_PAGE = "DispatchServlet?btAction=View Detail&articleId=";
    private final String POST_ARTICLE_PAGE = "postArticle.jsp";
    private final String ERROR_PAGE = "error.html";
    
    private static final Logger LOGGER = LogManager.getLogger(PostArticleServlet.class);
    
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
        String url = ERROR_PAGE; 
        try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER");
            if (request.getMethod().equals("GET")) {
                NotificationDAO notificationDAO = new NotificationDAO();
                int numberNewNotification = notificationDAO.countNewNotification(user.getEmail());
                request.setAttribute("NEW_NOTIFICATION", numberNewNotification);
                url = POST_ARTICLE_PAGE;
            } else {
                boolean foundError = false;
                ArticlePostError error = new ArticlePostError();
                String title = request.getParameter("txtTitle");
                String description = request.getParameter("txtDescription");
                Part filePart = request.getPart("imgFile");
                String realPath = request.getServletContext().getRealPath("/") + Constant.UPLOAD_DIR;
                String fileName = null;
                if (title == null || title.trim().isEmpty() || title.trim().length() > 100) {
                    error.setTitleLengthErr("Title is required from 1 to 100 chars");
                    foundError = true;
                } 
                if (description == null || description.trim().isEmpty() || description.trim().length() > 500) {
                    error.setDescriptionLengthErr("Description is required from 1 to 500 chars");
                    foundError = true;
                }
                if (foundError) {
                    request.setAttribute("POST_ARTICLE_ERROR", error);
                    NotificationDAO notificationDAO = new NotificationDAO();
                    int numberNewNotification = notificationDAO.countNewNotification(user.getEmail());
                    request.setAttribute("NEW_NOTIFICATION", numberNewNotification);
                    url = POST_ARTICLE_PAGE;
                } else {
                    if (filePart.getSize() > 0) {
                        File image = FileHeper.uploadFile(filePart, realPath);
                        fileName = image.getName();
                    }
                    ArticleDAO dao = new ArticleDAO();
                    boolean result = dao.addArticle(new ArticleDTO(title.trim(), description.trim(), fileName, user.getEmail()));
                    if (result) {
                        int id = dao.getLastedArticleId();
                        url = VIEW_ARTICLE_PAGE + id;
                    }
                }
            }
        } catch (NamingException ex) {
            LOGGER.error("NamingException", ex);
        } catch (SQLException ex) {
            LOGGER.error("SQLException", ex);
        } catch (IOException ex) {
            LOGGER.error("IOException", ex);
        } finally {
            if (url.equals(POST_ARTICLE_PAGE) || url.equals(ERROR_PAGE)) {
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
