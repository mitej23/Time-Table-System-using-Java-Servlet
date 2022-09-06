/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
@WebServlet(urlPatterns = {"/"})
public class HomeServlet extends HttpServlet {

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
            out.println("<title>Servlet HomeServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeServlet at " + request.getContextPath() + "</h1>");
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
        
        
        // Check if user cookie is present else reirect to login page
        boolean userCookie = false;
        Cookie [] ck = request.getCookies();
        for(int i = 0;ck!=null&&i < ck.length;i++){
            if(ck[i].getName().equals("user")){
                userCookie = true;
            }
        }
        
        if(!userCookie){
            response.sendRedirect("LoginServlet");
            out.close();
            return;
        }
        
        out.println("<html>");
        out.println("<head><title>Time Table Management System</title><style>body {margin: 4rem;}table {border: 1px solid black;border-spacing: 0px;}tr,th, td {font-size: 1.25rem;padding: 10px;}thead th,tbody tr th {border-bottom: 1px solid black;border-right: 1px solid black;color: white;background: #1865ad;}td {text-align: center;vertical-align: middle;border-right: 0.5px solid grey;border-bottom: 0.5px solid grey;}.container {display: flex;}form {margin-left: 3rem;display: flex;flex-direction: column;}input,select {font-size: 1rem;padding: 5px;margin-top: 5px;margin-bottom: 10px;}input[type=\"submit\"],input[type=\"reset\"] {background: #1865ad;color: white;border: none;padding: 8px;}</style></head>");
        out.println("<body>");
        out.println("<h1>Time Table Management System</h1>");
        out.println("<div class=\"container\"><table><thead>");
        out.println("<tr><th></th><th>Monday</th><th>Tuesday</th><th>Wednesday</th><th>Thursday</th><th>Friday</th><th>Saturday</th></tr>");
        out.println("</thead><tbody>");
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/timeTable?autoReconnect=true&useSSL=false","root","root");
            Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE);
            String[] timings = {"6:45 - 7:45","7:45 - 8:45","8:55 - 9:55","9:55 - 10:55","11:05 - 12:05","12:05 - 1:05"};
            
            for(int i = 1;i <= timings.length;i++){
                ResultSet result = statement.executeQuery("select * from timetable where tablerow = " + i +" order by tablecol;");
            
                out.print("<tr><th>" + timings[i - 1] + "</th>");                
                
                if(result.next() == false){
                    for(int j = 0;j < 6;j++){out.println("<td></td>");}
                }else{
                    result.beforeFirst();
                    for(int colCount = 1;colCount <= 6;colCount++){
                            if(result.next() == false){
                                out.println("<td></td>");
                            }else{
                                if(result.getInt(3) == colCount){
                                    out.println("<td>"+result.getString(1) + " </td>");
                                }else{
                                    out.println("<td></td>");
                                    result.previous();
                                }
                                
                            }     
                    }
                    
                    out.print("</tr>");
                }   
            }  
            
        }catch(Exception e){out.println(e);}
        
        out.println("</tbody></table>");
        
        out.println("<form action=\"/WebApplication2\" method=\"POST\"><h1>Add / Update Lecture</h1><label>Add Lecture Name:</label><input type=\"text\" name=\"lecture\" /><br /><label for=\"time\">Select Time:</label><select name=\"time\"><option value=\"1\">6:45 - 7:45</option><option value=\"2\">7:45 - 8:45</option><option value=\"3\">8:55 - 9:55</option><option value=\"4\">9:55 - 10:55</option><option value=\"5\">11:05 - 12:05 </option><option value=\"6\">12:05 - 1:05</option></select><label for=\"days\">Select Day:</label><select name=\"day\"><option value=\"1\">Monday</option><option value=\"2\">Tuesday</option><option value=\"3\">Wednesday</option><option value=\"4\">Thursday</option><option value=\"5\">Friday</option><option value=\"6\">Saturday</option></select><input type=\"reset\" /><input type=\"submit\" value=\"Submit\" /></form>");
        out.println("</div></body>");
        
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
        
        String subject = request.getParameter("lecture");
        String day = request.getParameter("day");
        String time = request.getParameter("time");
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/timeTable?autoReconnect=true&useSSL=false","root","root");
            
            Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE);
            ResultSet result = statement.executeQuery("select * from timetable where tablerow = " + time +" and tablecol ="+ day);
            
            // check if empyt or not
           
            if(result.next() == false){
                // empty 
                Statement stmt = con.createStatement();
                String sql = "insert into timetable values(\""+ subject + "\","+ time + ","+ day+")";
                stmt.execute(sql);
            }else{
                Statement stmt = con.createStatement();
                // if subject is "" empty check if user is admin 
                int subLen = result.getString(1).length();
                boolean empty = false;
                if(subLen == 0){
                    empty = true;
                }
                if(empty){
                    out.print("Empty");
                }else{

                    Cookie [] ck = request.getCookies();
                    for(int i = 0;ck!=null&&i < ck.length;i++){
                        if(ck[i].getName().equals("user")){
                            String userType = ck[i].getValue();
                            if(userType.equals("teacher")){
                                out.println("<h1>Teachers are not allowed to overwrite a lecture</h1>");
                            }else{
                                String sql = "update timetable set subject = \""+ subject + "\" where tablerow = " + time + " and tablecol = " + day;
                                stmt.executeUpdate(sql);
                                doGet(request,response);
                            }
                        }
                    }
                }
                     
                
//                
                
            }
            

            
            
            
        }catch(Exception e){

            out.println(e);
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
