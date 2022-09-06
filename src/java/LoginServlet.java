/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
@WebServlet(urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html><html><head><meta charset=\"utf-8\"><meta name=\"viewport\" content=\"width=device-width\"><title>replit</title><style>h1{margin: 0px;margin-bottom: 1.25rem;}form {display: flex;flex-direction: column;position: absolute;top: 0;bottom: 0;left: 0;right: 0;margin: auto;margin-top: 7rem;max-width: 275px;max-height: fit-content;padding: 2.5rem;/* border: 1px solid black; */border-radius: 10px;box-shadow: 0 4px 6px -1px rgb(0 0 0 / 10%), 0 2px 4px -2px rgb(0 0 0 / 10%);}input,select {font-size: 1rem;padding: 5px;margin-top: 5px;margin-bottom: 10px;border-radius: 3px;}input[type=\"submit\"],input[type=\"reset\"] {background: #1865ad;color: white;border: none;padding: 8px;}</style></head><body><form action=\"LoginServlet\" method=\"POST\"><h1>Login</h1><label>User Type</label><div style=\"margin: 7px -5px;\"><input type=\"radio\" name=\"user_type\" value=\"Teacher\"><label for=\"user_type\">Teacher</label><input type=\"radio\" name=\"user_type\" value=\"Admin\"><label for=\"user_type\">Admin</label></div><label>Password</label><input type=\"password\" name=\"password\" /><input type=\"reset\"><input type=\"submit\" value=\"Login\"></form></body></html>");
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String user = request.getParameter("user_type");
        String password = request.getParameter("password");
        
        out.println("<h1> "+user + " " + password +" </h1>");
        
        if(user.equals("Admin")){
            out.println("<h1>Admin Selectd</h1>");
            if(password.equals("root@123")){
                out.println("<h1>Admin Logged In</h1>");
                // set cookie
                Cookie userCookie = new Cookie("user","admin");
                response.addCookie(userCookie);
                response.sendRedirect("HomeServlet");
            }
        }else if(user.equals("Teacher")){
            out.println("<h1>Teacher Selectd</h1>");
            if(password.equals("password")){
                // setcookie
                Cookie userCookie = new Cookie("user","teacher");
                response.addCookie(userCookie);
                response.sendRedirect("HomeServlet");
            }
        }else{
            out.println("user authentication failed");
            doGet(request,response);
        }
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
