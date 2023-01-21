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
import pl.polsl.model.*;

/**
 *
 * Servlet which user uses to insert new audiobooks data.
 *
 * @author Kamil Musiałowski
 * @version 1.5
 */
@WebServlet("/AddNewAudiobookServlet")
public class AddNewAudiobookServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods. Displays page which allowes to add new audiobook.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=ISO-8859-2");
        PrintWriter out = response.getWriter();
        out.println("<html><header>\n</header>\n<body>\n"
                + "\n<form method=\"post\" action =\"AddingNewAudiobookServlet\">"
                + "<label for=\"newAuthor\">Author:</label><br>\n"
                + "<input type=\"text\" id=\"newAuthor\" name=\"newAuthor\" value=\"\"><br>\n"
                + "<label for=\nnewTitle\">Title:</label><br>\n"
                + "<input type=\"text\" id=\"newTitle\" name=\"newTitle\" value=\"\"><br>\n"
                + "<label for=\nnewPrice\">Price:</label><br>\n"
                + "<input type=\"text\" id=\"newPrice\" name=\"newPrice\" value=\"\"><br>\n"
                + "<input type=\"radio\" id=\"genreNovel\" name=\"genre\" value=\"Novel\">Novel\n"
                + "<input type=\"radio\" id=\"genreCrime\" name=\"genre\" value=\"Crime\">Crime\n"
                + "<input type=\"radio\" id=\"genreFantasy\" name=\"genre\" value=\"Fantasy\">Fantasy\n"
                + "<input type=\"radio\" id=\"genreUnspecified\" name=\"genre\" value=\"Unspecified\" checked=\"true\">Unspecified\n"
                + "<input type=\"submit\" value=\"Add audiobook\">\n"
                + "</form>\n"
                + "</body>\n</html>");
    }

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
