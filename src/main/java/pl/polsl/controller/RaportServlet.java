/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.model.Model;

/**
 *
 * Servlet which controls raporting process.
 * 
 * @author Kamil Musiałowski
 * @version 1.5
 */
@WebServlet(name = "RaportServlet", urlPatterns = {"/RaportServlet"})
public class RaportServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods. Controls raporting process.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RaportServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<form action=\"MainPageServlet\">"
                    + "<input type=\"submit\" value=\"Main menu\">\n"
                    + "</form>\n");

            ResultSet rs = ((Model) this.getServletContext().getAttribute("model")).selectUsersAll();//stmt.executeQuery("SELECT * FROM APP.USERS");
            while (rs.next()) {
                out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " "/* + rs.getInt(4)*/ + "<br>");
            }
            out.println("<br><br><br><br>");
            rs = ((Model) this.getServletContext().getAttribute("model")).selectAudiobooksAll();//stmt.executeQuery("SELECT * FROM APP.AUDIOBOOKS");
            while (rs.next()) {
                out.println(rs.getInt(1) + " " + rs.getString(2)
                        + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getDouble(5) + " " + "<br>");
            }
            out.println("<br><br><br><br>");
            rs = ((Model) this.getServletContext().getAttribute("model")).selectLendAll();//stmt.executeQuery("SELECT * FROM APP.LEND");
            while (rs.next()) {
                out.println(rs.getInt("LEND_ID") + "; " + rs.getInt("USER_ID") + "; " + rs.getInt("AUDIOBOOK_ID") + "; "
                        + rs.getDate("START_DATE") + "; " + rs.getDate("END_DATE") + "<br>");
            }
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException e) {

        }
    }

    /**
     * Initializes apps Model and DB connection if it is not initialized yet.
     */
    @Override
    public void init() {
        Connection connection = (Connection) this.getServletContext().getAttribute("connection");
        if (connection == null) {
            Model model = new Model();
            connection = model.getConnection();
            this.getServletContext().setAttribute("model", model);
            this.getServletContext().setAttribute("connection", connection);
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
