/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ruby
 */
@MultipartConfig
@WebServlet(name = "DispatchServlet", urlPatterns = {"/DispatchServlet"})
public class DispatchServlet extends HttpServlet {
    
    private final String PAGE_404 = "404.html";
    private final String LOGIN_SERVLET = "LoginServlet";
    private final String LOGOUT_SERVLET = "LogoutServlet";
    private final String CREATE_ACCOUNT_SERVLET = "CreateAccountServlet";
    private final String SEARCH_SERVLET = "SearchServlet";
    private final String VIEW_DETAIL_SERVLET = "ViewDetailServlet";
    private final String POST_ARTICLE_SERVLET = "PostArticleServlet";
    private final String DELETE_ARTICLE_SERVLET = "DeleteArticleServlet";
    private final String CHANGE_EMOTION_SERVLET = "ChangeEmotionServlet";
    private final String POST_COMMENT_SERVLET = "PostCommentServlet";
    private final String DELETE_COMMENT_SERVLET = "DeleteCommentServlet";
    private final String SHOW_NOTI_SERVLET = "ShowNotificationServlet";
    private final String ACTIVE_ACCOUNT_SERVLET = "ActivateAccountServlet";
    
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
        String action = request.getParameter("btAction");
        String url = PAGE_404;
        try {
            if (action == null) {
                url = SEARCH_SERVLET;
            } else switch (action) {
                case "Login":
                    url = LOGIN_SERVLET;
                    break;
                case "Logout":
                    url = LOGOUT_SERVLET;
                    break;
                case "Sign Up":
                    url = CREATE_ACCOUNT_SERVLET;
                    break;
                case "Search":
                    url = SEARCH_SERVLET;
                    break;
                case "View Detail":
                    url = VIEW_DETAIL_SERVLET;
                    break;
                case "Change Emotion":
                    url = CHANGE_EMOTION_SERVLET;
                    break;
                case "Post Comment":
                    url = POST_COMMENT_SERVLET;
                    break;
                case "Delete Comment":
                    url = DELETE_COMMENT_SERVLET;
                    break;
                case "Post Article":
                    url = POST_ARTICLE_SERVLET;
                    break;
                case "Delete Article":
                    url = DELETE_ARTICLE_SERVLET;
                    break;
                case "Show Notification":
                    url = SHOW_NOTI_SERVLET;
                    break;
                case "Activate":
                    url = ACTIVE_ACCOUNT_SERVLET;
                    break;
            }
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
