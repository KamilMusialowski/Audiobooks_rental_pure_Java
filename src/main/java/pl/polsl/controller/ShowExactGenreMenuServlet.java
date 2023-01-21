/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.model.Model;

/**
 *
 * Servlet used to chose genre of audiobooks to display
 *
 * @author Kamil Musiałowski
 * @version 1.5
 */
@WebServlet("/ShowExactGenreMenuServlet")
public class ShowExactGenreMenuServlet extends HttpServlet {

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
        response.setContentType("text/html; charset=ISO-8859-2");
        //try {
        PrintWriter out = response.getWriter();
        out.println("<html><header>\n</header>\n<body>\n"
                + "<form action=\"MainPageServlet\">"
                + "<input type=\"submit\" value=\"Main menu\">\n"
                + "</form>\n"
                + "<form method=\"post\" action =\"ShowingExactGenreServlet\">"
                + "<input type=\"radio\" id=\"genreNovel\" name=\"genre\" value=\"Novel\">Novel\n"
                + "<input type=\"radio\" id=\"genreCrime\" name=\"genre\" value=\"Crime\">Crime\n"
                + "<input type=\"radio\" id=\"genreFantasy\" name=\"genre\" value=\"Fantasy\">Fantasy\n"
                + "<input type=\"radio\" id=\"genreUnspecified\" name=\"genre\" value=\"Unspecified\" checked=\"true\">Unspecified\n"
                + "<input type=\"submit\" value=\"Show\">\n"
                + "</form>\n"
                + "</body>\n</html>");
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

}
