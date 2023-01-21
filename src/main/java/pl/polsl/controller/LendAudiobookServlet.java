/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.model.Model;

/**
 *
 * Servlet which controls borrowing process.
 * 
 * @author Kamil Musiałowski
 * @version 1.5
 */
@WebServlet(name = "LendAudiobookServlet", urlPatterns = {"/LendAudiobookServlet"})
public class LendAudiobookServlet extends HttpServlet {

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

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods. Controls audiobook borrowing process.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        String loggedUser = Integer.toString((int)session.getAttribute("loggedUser"));
        String audiobookID = request.getParameter("audiobook");
        try {
            ((Model) this.getServletContext().getAttribute("model")).lendAudiobook(loggedUser, audiobookID);
            ResultSet rs = ((Model) this.getServletContext().getAttribute("model")).selectAudiobookByID(audiobookID);
            rs.next();
            String yourAudiobook = rs.getString("TITLE") + "; " + rs.getString("AUTHOR");
            double price = rs.getDouble("Price");
            int status = ((Model) this.getServletContext().getAttribute("model")).selectUserStatusByID(loggedUser);//rs.getInt("STATUS");
            if (status == 2) {
                price = Math.round((price * 0.7) * 100.00) / 100.00;
            }
            PrintWriter out = response.getWriter();
            out.println("<html>\n<body>\n"
                    + "<h1>Thank you!</h1>\n"
                    + "Enjoy your new audiobook:<br>"
                    + yourAudiobook + "<br>"
                    + "According to your account status, you paid: " + price + "PLN<br>"
                    + "<button onclick=\"history.back()\">Go Back</button>"
                    + "</body>\n</html>");
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.println("<html>\n<body>\n"
                    + "<h1>Not correct data</h1>\n"
                    + e.getMessage()
                    + "<button onclick=\"history.back()\">Go Back</button>"
                    + "</body>\n</html>");
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
