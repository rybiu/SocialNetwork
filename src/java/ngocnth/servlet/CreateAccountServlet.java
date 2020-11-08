/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngocnth.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ngocnth.user.UserCreateError;
import ngocnth.user.UserDAO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Ruby
 */
@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {
    
    private final String LOGIN_PAGE = "DispatchServlet?btAction=Login";
    private final String CREATE_ACCOUNT_PAGE = "signUp.html";
    private final String CREATE_ACCOUNT_ERROR_PAGE = "signUp.jsp";
    private final String ERROR_PAGE = "error.html";
    
    private static final Logger LOGGER = LogManager.getLogger(CreateAccountServlet.class);

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
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String name = request.getParameter("txtName");
        String url = ERROR_PAGE;
        UserCreateError errors = new UserCreateError();
        boolean foundErr = false;
        try {
            if (request.getMethod().equals("GET")) {
                url = CREATE_ACCOUNT_PAGE;
            } else {
                if (email == null || !email.trim().contains("@")) {
                    foundErr = true;
                    errors.setEmailFormatErr("Email address is invalid");
                }
                if (password == null || password.trim().length() < 6 || password.trim().length() > 30) {
                    foundErr = true;
                    errors.setPasswordLengthErr("Password is required from 6 to 30 chars");
                } else if (!confirm.trim().equals(password.trim())) {
                    foundErr = true;
                    errors.setConfirmFormatErr("Confirm must match password");
                }
                if (name == null || name.trim().length() < 2 || name.trim().length() > 50) {
                    foundErr = true;
                    errors.setNameLengthErr("Full name is required from 2 to 50 chars");
                }   
                if (foundErr) {
                    request.setAttribute("CREATE_ERROR", errors);
                    url = CREATE_ACCOUNT_ERROR_PAGE;
                } else {
                    UserDAO dao = new UserDAO();
                    dao.createAccount(email, name, password);
                    url = LOGIN_PAGE;   
                }
            }
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            if (msg.contains("duplicate")) {
                errors.setEmailIsExisted("This email address is already taken");
                request.setAttribute("CREATE_ERROR", errors);
                url = CREATE_ACCOUNT_ERROR_PAGE;
            } else {
                LOGGER.error("SQLException", ex);
            }
        } catch (NamingException ex) {
            LOGGER.error("NamingException", ex);
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("NoSuchAlgorithmException", ex);
        } finally {
            if (url.equals(LOGIN_PAGE)) {
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
