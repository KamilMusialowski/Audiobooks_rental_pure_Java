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
import pl.polsl.model.OwnException;

/**
 *
 * Servlet used to display audiobooks of exact genre
 *
 * @author Kamil Musiałowski
 * @version 1.5
 */
@WebServlet(name = "ShowingExactGenreServlet", urlPatterns = {"/ShowingExactGenreServlet"})
public class ShowingExactGenreServlet extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        out.println("<html>\n<body>\n"
                + "<form action=\"MainPageServlet\">"
                + "<input type=\"submit\" value=\"Main menu\">\n"
                + "</form>\n"
                + "<form method=\"post\" action =\"ShowingExactGenreServlet\">"
                + "<input type=\"radio\" id=\"genreNovel\" name=\"genre\" value=\"Novel\">Novel\n"
                + "<input type=\"radio\" id=\"genreCrime\" name=\"genre\" value=\"Crime\">Crime\n"
                + "<input type=\"radio\" id=\"genreFantasy\" name=\"genre\" value=\"Fantasy\">Fantasy\n"
                + "<input type=\"radio\" id=\"genreUnspecified\" name=\"genre\" value=\"Unspecified\">Unspecified\n"
                + "<input type=\"submit\" value=\"Show\">\n"
                + "</form>\n"
                + htmlTableGenerator(modelToTable((String) request.getParameter("genre")))
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
     * Initializes apps Model if it is not initialized yet.
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
     * Creates HTML table from String array
     *
     * @param data String array
     * @return String containing HTML table code
     */
    private String htmlTableGenerator(String[][] data) {
        String generatedTable = "<table style=\"border:1px solid black;margin-left:auto;margin-right:auto;\"border=\"1\";>\n"
                + "<tr>\n"
                + "<th>ID</th>\n"
                + "<th>Author</th>\n"
                + "<th>Title</th>\n"
                + "<th>Genre</th>\n"
                + "<th>Price</th>\n"
                + "</tr>\n";
        for (String[] data1 : data) {
            generatedTable += "\n<tr>\n";
            generatedTable
                    += ("<th>" + data1[0] + "</th>\n" + "<th>" + data1[1] + "</th>\n"
                    + "<th>" + data1[2] + "</th>\n" + "<th>" + data1[3] + "</th>\n"
                    + "<th>" + data1[4] + "</th>\n");
            generatedTable += ("<th> <form action=\"LendAudiobookServlet\"> <input type=\"submit\" name=\"" + data1[0] + "\" value=\"Lend\"/>"
                    + "<input type=\"hidden\" name=\"audiobook\" value=\"" + data1[0] + "\"/>"
                    + "</form></th>");
            generatedTable += "</tr>\n";
        }
        generatedTable += "</table>\n";
        return generatedTable;
    }

    /**
     * Prepares String array containing exact genre audiobooks data
     *
     * @param genre Audiobooks genre param
     * @return Strng array containig data from Model
     */
    private String[][] modelToTable(String genre) {
        int size = 0;
        String[][] data = new String[1][1];
        try {
            ResultSet rs = ((Model) this.getServletContext().getAttribute("model")).selectAudiobooksByGenre(genre);
            if (rs != null) {
                rs.last();
                size = rs.getRow();
                rs.beforeFirst();
            }
            data = new String[size][5];
            int arrayIndex = 0;
            while (rs.next()) {
                data[arrayIndex][0] = Integer.toString(rs.getInt(1));
                data[arrayIndex][1] = rs.getString("AUTHOR");
                data[arrayIndex][2] = rs.getString("TITLE");
                data[arrayIndex][3] = rs.getString("GENRE");
                data[arrayIndex][4] = Double.toString(rs.getDouble("PRICE"));
                arrayIndex++;
            }
            rs.close();
        } catch (SQLException | OwnException e) {
            
        }
        return data;
    }
    
}
