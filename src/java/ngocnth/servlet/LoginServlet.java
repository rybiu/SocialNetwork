/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ngocnth.user.UserDAO;
import ngocnth.user.UserDTO;
import ngocnth.util.MailHelper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Ruby
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String SEARCH_PAGE = "DispatchServlet?btAction=Search";
    private final String LOGIN_PAGE = "login.html";
    private final String INVALID_PAGE = "loginFail.html";
    private final String ACTIVATE_PAGE = "activate.html";
    private final String ERROR_PAGE = "error.html";
    
    private static final Logger LOGGER = LogManager.getLogger(LoginServlet.class);
    
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
            if (request.getMethod().equals("GET")) {
                url = LOGIN_PAGE;
            } else {
                String email = request.getParameter("txtEmail");
                String password = request.getParameter("txtPassword");
                UserDAO dao = new UserDAO();
                UserDTO user = dao.checkLogin(email, password);
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("USER", user);
                    if (user.getStatusName().equals("New")) {
                        url = ACTIVATE_PAGE;
                        if (session.getAttribute("CODE_ACTIVATION") == null) {
                            int codeActivation = (int) Math.floor(((Math.random() * 290100) + 100000));
                            String mailSubject = "Social Network account activation";
                            String mailMsg = "Hi " + user.getName() + ",\n\n" 
                                + "Thanks for joining Social Network!\n\n"
                                + "Please confirm your email address and activate your account "
                                + "by this code: " + codeActivation + ".\n\n" 
                                + "Regards,\n\n" 
                                + "The Social Network team\n\n" 
                                + "If you didn't register for SocialNetwork, please ignore this message.";
                            MailHelper.send(user.getEmail(), mailSubject, mailMsg);
                            session.setAttribute("CODE_ACTIVATION", codeActivation);
                        }
                    } else {
                        url = SEARCH_PAGE;
                        Cookie cookie = new Cookie("Authentication", email + "-" + password);
                        cookie.setMaxAge(60 * 10);
                        response.addCookie(cookie);
                    }
                } else {
                    url = INVALID_PAGE;
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("SQLException", ex);
        } catch (NamingException ex) {
            LOGGER.error("NamingException", ex);
        } catch (MessagingException ex) {
            LOGGER.error("MessagingException", ex);
        } catch (UnsupportedEncodingException ex) {
            LOGGER.error("UnsupportedEncodingException", ex);
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("NoSuchAlgorithmException", ex);
        } finally {
            if (url.equals(SEARCH_PAGE)) {
                response.sendRedirect(url);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
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
